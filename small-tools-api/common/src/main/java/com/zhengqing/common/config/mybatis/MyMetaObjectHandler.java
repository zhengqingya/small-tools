package com.zhengqing.common.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.http.ContextHandler;
import com.zhengqing.common.util.MyDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

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
     * 创建
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug(" -------------------- [MyBatisPlus自动填充处理] start insert fill ...  --------------------");
        // 用户id
        int userId = ContextHandler.getUserId();
        // 当前时间
        Date nowDate = new Date();

        // 判断对象中是否存在该参数，如果存在则插入数据
        if (metaObject.hasGetter(AppConstant.IS_VALID)) {
            this.setFieldValByName(AppConstant.IS_VALID, 1, metaObject);
        }
        if (metaObject.hasGetter(AppConstant.CREATE_BY)) {
            Object value = metaObject.getValue(AppConstant.CREATE_BY);
            if (value != null) {
                userId = (Integer) value;
            }
            this.setFieldValByName(AppConstant.CREATE_BY, userId, metaObject);
        }
        if (metaObject.hasGetter(AppConstant.CREATE_TIME)) {
            this.setFieldValByName(AppConstant.CREATE_TIME, nowDate, metaObject);
        }
        if (metaObject.hasGetter(AppConstant.UPDATE_BY)) {
            this.setFieldValByName(AppConstant.UPDATE_BY, userId, metaObject);
        }
        if (metaObject.hasGetter(AppConstant.UPDATE_TIME)) {
            this.setFieldValByName(AppConstant.UPDATE_TIME, nowDate, metaObject);
        }

        // 日志输出 ================================================================================================
        // Date createTime = (Date)this.getFieldValByName(CREATE_TIME, metaObject);
        log.debug("[MyBatisPlus自动填充处理] 时间:{} 操作人id:{}", MyDateUtil.dateToStr(nowDate, MyDateUtil.DATE_TIME_FORMAT),
                userId);
    }

    /**
     * 更新
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug(" -------------------- [MyBatisPlus自动填充处理] start update fill ...  --------------------");
        // 用户id
        int userId = ContextHandler.getUserId();
        // 当前时间
        Date nowDate = new Date();

        // 判断对象中是否存在该参数，如果存在则插入数据
        if (metaObject.hasGetter(AppConstant.UPDATE_BY)) {
            this.setFieldValByName(AppConstant.UPDATE_BY, userId, metaObject);
        }
        if (metaObject.hasGetter(AppConstant.UPDATE_TIME)) {
            this.setFieldValByName(AppConstant.UPDATE_TIME, nowDate, metaObject);
        }

        // 日志输出 ================================================================================================
        log.debug("[MyBatisPlus自动填充处理] 时间:{} 操作人id:{}", MyDateUtil.dateToStr(nowDate, MyDateUtil.DATE_TIME_FORMAT),
                userId);
    }

}
