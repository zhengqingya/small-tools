package com.zhengqing.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <p>
 * bean 工具类
 * </p>
 *
 * @author： zhengqing <br/>
 * @date： 2019/11/6$ 18:34$ <br/>
 * @version： <br/>
 */
@Slf4j
public class MyBeanUtil {

    /**
     * 对象属性拷贝
     *
     * @param source:
     *            源对象
     * @param target:
     *            目标对象
     * @return: void
     * @author : zhengqing
     * @date : 2020/9/5 23:22
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 对象属性拷贝 : 将源对象的属性拷贝到目标对象
     *
     * @param source:
     *            源对象
     * @param clzz:
     *            目标对象class
     */
    public static <T> T copyProperties(Object source, Class<T> clzz) {
        if (source == null) {
            return null;
        }
        T target = BeanUtils.instantiate(clzz);
        try {
            BeanUtils.copyProperties(source, target);
        } catch (BeansException e) {
            log.error("BeanUtil property copy  failed :BeansException", e);
        } catch (Exception e) {
            log.error("BeanUtil property copy failed:Exception", e);
        }
        return target;
    }

    /**
     * 拷贝list
     *
     * @param input:
     *            输入集合
     * @param clzz:
     *            输出集合类型
     * @param <E>:
     *            输入集合类型
     * @param <T>:
     *            输出集合类型
     * @return 返回集合
     */
    public static <E, T> List<T> copyList(List<E> input, Class<T> clzz) {
        List<T> output = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(input)) {
            for (E source : input) {
                T target = BeanUtils.instantiate(clzz);
                BeanUtils.copyProperties(source, target);
                output.add(target);
            }
        }
        return output;
    }

    /**
     * 获取任意泛型类的指定属性值（暂只支持同时访问父类和子类属性，如果有多继承的话，还需要再修改）
     *
     * @param tList:
     *            list对象数据
     * @param field:
     *            要获取指定字段属性值对应的字段名
     * @param isSuperfield:
     *            true：父类 false：子类
     * @return: java.util.List<?>
     */
    public static <T> List<?> getFieldList(List<T> tList, String field, Boolean isSuperfield) {
        if (StringUtils.isBlank(field)) {
            return null;
        }

        // 拼接方法
        field =
            new StringBuffer("get").append(field.substring(0, 1).toUpperCase()).append(field.substring(1)).toString();
        List<Object> idList = Lists.newArrayList();
        Method method = null;
        try {
            if (isSuperfield) {
                method = tList.get(0).getClass().getSuperclass().getMethod(field);
            } else {
                method = tList.get(0).getClass().getMethod(field);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            for (T t : tList) {
                Object s = method.invoke(t);
                idList.add(s);
            }
        } catch (NullPointerException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return idList;
    }

    /**
     * 获取任意泛型类的指定属性值（暂只支持同时访问父类和子类属性，如果有多继承的话，还需要再修改）
     *
     * @param tList:
     *            list对象数据
     * @param field:
     *            要获取指定字段属性值对应的字段名
     * @return: java.util.List<?>
     */
    public static <T> List<?> getFieldList(List<Map<String, Object>> tList, String field) {
        if (StringUtils.isBlank(field)) {
            return null;
        }
        List<Object> idList = Lists.newArrayList();
        for (Map<String, Object> map : tList) {
            idList.add(map.get(field));
        }
        return idList;
    }

    /**
     * 将Map转换为对象
     *
     * @param paramMap:
     * @param clz:
     * @return: T
     * @author : zhengqing
     * @date : 2020/11/27 18:39
     */
    public static <T> T parseMap2Object(Map<String, Object> paramMap, Class<T> clz) {
        return JSONObject.parseObject(JSONObject.toJSONString(paramMap), clz);
    }

}
