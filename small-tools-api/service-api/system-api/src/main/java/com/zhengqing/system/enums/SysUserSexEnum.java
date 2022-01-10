package com.zhengqing.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.google.common.collect.Lists;
import com.zhengqing.common.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 性别枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:56
 */
@Getter
@AllArgsConstructor
public enum SysUserSexEnum {

    /**
     * 未知
     */
    未知(0, "未知"),
    /**
     * 男
     */
    男(1, "男"),
    /**
     * 女
     */
    女(2, "女");

    /**
     * 类型值
     * {@link com.baomidou.mybatisplus.annotation.EnumValue} 标记数据库存的值是type
     */
    @EnumValue
    private final Integer type;
    /**
     * 类型描述
     */
    private final String desc;


    private static final List<SysUserSexEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(SysUserSexEnum.values()));
    }

    /**
     * 根据指定的规则类型查找相应枚举类
     *
     * @param type 类型
     * @return 类型枚举信息
     * @author zhengqingya
     * @date 2022/1/10 12:52
     */
    public static SysUserSexEnum getEnum(Integer type) {
        for (SysUserSexEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定类型数据！");
    }

}
