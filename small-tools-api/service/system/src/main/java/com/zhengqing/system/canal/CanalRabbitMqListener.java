package com.zhengqing.system.canal;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhengqing.system.constant.CanalConstant;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysPermissionBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p> 监听数据库数据变化 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/20 15:07
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CanalRabbitMqListener {

    private final ISysDictService dictService;
    private final ISysPermissionBusinessService sysPermissionBusinessService;

    private final List<String> permTableNameList = Lists.newArrayList(
            "t_sys_role",
            "t_sys_menu",
            "t_sys_role_menu",
            "t_sys_user_role",
            "t_sys_permission",
            "t_sys_role_permission"
    );
    private final List<String> dictTableNameList = Lists.newArrayList(
            "t_sys_dict",
            "t_sys_dict_type"
    );

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = CanalConstant.CANAL_QUEUE, durable = "true"),
                    exchange = @Exchange(value = CanalConstant.CANAL_EXCHANGE),
                    key = CanalConstant.CANAL_ROUTING_KEY
            )
    })
    public void handleCanalDataChange(String message) {
        log.info("[canal] 接收消息: {}", message);
        JSONObject msgObj = JSONObject.parseObject(message);
        String tableName = msgObj.getString("table");

        if (this.permTableNameList.contains(tableName)) {
            log.info("[canal] {} 更新缓存-权限", tableName);
            this.sysPermissionBusinessService.refreshRedisPerm();
        }
        if (this.dictTableNameList.contains(tableName)) {
            log.info("[canal] {} 更新缓存-数据字典", tableName);
            this.dictService.initCache();
        }

    }

}
