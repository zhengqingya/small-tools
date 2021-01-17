package com.zhengqing.common.config.mybatis;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zhengqing.common.http.ContextHandler;
import com.zhengqing.common.util.MyDateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * MyBatisPlus自定义字段自动填充处理类 - 实体类中使用 @TableField注解
 * </p>
 *
 * @description: 注意前端传值时要为null
 * @author: zhengqing
 * @date: 2019/8/18 0018 1:46
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 是否有效(1:有效 0:无效）
     */
    private final String IS_VALID = "isValid";
    /**
     * 创建人id
     */
    private final String CREATE_BY = "createBy";
    /**
     * 创建时间
     */
    private final String CREATE_TIME = "createTime";
    /**
     * 更新人id
     */
    private final String UPDATE_BY = "updateBy";
    /**
     * 更新时间
     */
    private final String UPDATE_TIME = "updateTime";

    /**
     * 创建
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug(" -------------------- start insert fill ...  --------------------");
        // 用户id
        int userId = ContextHandler.getUserId();
        // 当前时间
        Date nowDate = new Date();

        // 判断对象中是否存在该参数，如果存在则插入数据
        if (metaObject.hasGetter(IS_VALID)) {
            setFieldValByName(IS_VALID, 1, metaObject);
        }
        if (metaObject.hasGetter(CREATE_BY)) {
            setFieldValByName(CREATE_BY, userId, metaObject);
        }
        if (metaObject.hasGetter(CREATE_TIME)) {
            setFieldValByName(CREATE_TIME, nowDate, metaObject);
        }
        if (metaObject.hasGetter(UPDATE_BY)) {
            setFieldValByName(UPDATE_BY, userId, metaObject);
        }
        if (metaObject.hasGetter(UPDATE_TIME)) {
            setFieldValByName(UPDATE_TIME, nowDate, metaObject);
        }

        // 日志输出 ================================================================================================
        // Date createTime = (Date)this.getFieldValByName(CREATE_TIME, metaObject);
        log.debug("MyBatisPlus自动填充处理 - 时间:{} 操作人id:{}", MyDateUtil.dateToStr(nowDate, MyDateUtil.DATE_TIME_FORMAT),
            userId);
    }

    /**
     * 更新
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug(" -------------------- start update fill ...  --------------------");
        // 用户id
        int userId = ContextHandler.getUserId();
        // 当前时间
        Date nowDate = new Date();

        // 判断对象中是否存在该参数，如果存在则插入数据
        if (metaObject.hasGetter(UPDATE_BY)) {
            setFieldValByName(UPDATE_BY, userId, metaObject);
        }
        if (metaObject.hasGetter(UPDATE_TIME)) {
            setFieldValByName(UPDATE_TIME, nowDate, metaObject);
        }

        // 日志输出 ================================================================================================
        log.debug("MyBatisPlus自动填充处理 - 时间:{} 操作人id:{}", MyDateUtil.dateToStr(nowDate, MyDateUtil.DATE_TIME_FORMAT),
            userId);
    }

}
