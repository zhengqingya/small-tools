package com.zhengqing.tool.other.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 匿名处理状态枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/2 15:52
 */
@Getter
@AllArgsConstructor
public enum StOtherAnonymityStatusEnum {

    未处理(0, "未处理"), 已处理(1, "已处理");

    private final Integer status;
    private final String desc;

    private static final List<StOtherAnonymityStatusEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(StOtherAnonymityStatusEnum.values()));
    }

    /**
     * 根据状态查询相应枚举类信息
     *
     * @param status: 匿名状态
     * @return 对应枚举信息
     * @author zhengqingya
     * @date 2020/10/25 13:53
     */
    public static StOtherAnonymityStatusEnum getEnum(Integer status) {
        for (StOtherAnonymityStatusEnum anonymityStatusEnum : LIST) {
            if (anonymityStatusEnum.getStatus().equals(status)) {
                return anonymityStatusEnum;
            }
        }
        throw new MyException("未找到正确的匿名状态值！");
    }

}
