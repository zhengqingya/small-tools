package com.zhengqing.common.validator.fieldrepeat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.zhengqing.common.exception.MyException;
import com.zhengqing.common.mapper.MyBaseMapper;
import com.zhengqing.common.util.ApplicationContextUtil;
import com.zhengqing.common.util.MyBeanUtil;
import com.zhengqing.common.util.MyStringUtil;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * <p>
 * 数据库字段内容重复判断处理工具类
 * </p>
 *
 * @author： zhengqing <br/>
 * @date： 2019/9/10$ 9:28$ <br/>
 * @version： <br/>
 */
@Slf4j
public class FieldRepeatValidatorUtil {

    /**
     * 表名
     */
    private String TABLE_NAME;

    /**
     * 数据库对应实体类主键id字段属性名
     */
    private String idName;
    /**
     * 数据库主键id字段属性名
     */
    private String idDbName;

    /**
     * 数据库主键id字段属性值
     */
    private Integer idValue;

    /**
     * 校验字段名组
     */
    private String[] fieldNames;

    /**
     * 校验辅助字段组值 - 字符串、数字、对象...
     */
    private List<Object> fieldValues = new LinkedList<>();

    /**
     * 校验辅助字段组固定值 - 字符串、数字、对象...
     */
    private String[] fieldFixedValues;

    /**
     * 储存校验辅助字段对应数据库字段值
     */
    private List<String> DB_FIELDS;

    /**
     * 顶级父包名
     */
    private final String PACKAGE_NAME = "java.lang.Object";

    /**
     * 实体类对象值
     */
    private Object object;

    /**
     * 错误提示信息
     */
    private String message;

    /**
     * @param tableName：
     *            数据库表名
     * @param idDbName：
     *            数据库主键id字段属性名
     * @param fieldNames：校验字段组
     * @param dbFieldNames：数据库校验字段组
     * @param fieldFixedValues：校验字段组固定值
     * @param object：对象数据
     * @param message：回调到前端提示消息
     */
    public FieldRepeatValidatorUtil(String tableName, String idDbName, String[] fieldNames, String[] dbFieldNames,
        String[] fieldFixedValues, Object object, String message) {
        this.TABLE_NAME = tableName;

        this.idDbName = idDbName;
        // id 转 驼峰命名
        this.idName = MyStringUtil.dbStrToHumpLower(idDbName);

        this.fieldNames = fieldNames;
        this.DB_FIELDS = new ArrayList<>(Arrays.asList(dbFieldNames));
        this.fieldFixedValues = fieldFixedValues;
        this.object = object;
        this.message = message;
        getFieldValue();
    }

    /**
     * 校验数据
     *
     * @return: boolean
     */
    public boolean fieldRepeat() {
        // 4、 校验字段内容是否重复
        // 工厂模式 + ar动态语法 -> 【这里已修改为`MyBaseMapper`方式查询数据】
        // Model entity = (Model)object;
        // if (object.getClass().getAnnotation(TableName.class) == null) {
        // try {
        // entity = (Model)object.getClass().getSuperclass().newInstance();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // list = entity.selectList((Wrapper)new QueryWrapper().allEq(queryMap));

        // 5、拼接sql
        Map<Object, Object> queryMap = new HashMap<>(5);
        if (fieldFixedValues.length == 0) {
            for (int i = 0; i < fieldNames.length; i++) {
                queryMap.put(DB_FIELDS.get(i), fieldValues.get(i));
            }
        } else {
            for (int i = 0; i < fieldNames.length; i++) {
                if (StringUtils.isBlank(fieldFixedValues[i])) {
                    queryMap.put(DB_FIELDS.get(i), fieldValues.get(i));
                } else {
                    queryMap.put(DB_FIELDS.get(i), fieldFixedValues[i]);
                }

            }
        }

        List<Map<String, Object>> list =
            ApplicationContextUtil.getApplicationContext().getBean(MyBaseMapper.class).selectList(TABLE_NAME, queryMap);

        /// 6、如果数据重复返回false -> 再返回自定义错误消息到前端
        if (!CollectionUtils.isEmpty(list)) {
            if (idValue == null) {
                throw new MyException(message);
            } else {
                // 获取list中指定字段属性值 - 这里只获取主键id
                List<Integer> idList = (List<Integer>)MyBeanUtil.getFieldList(list, idDbName);
                boolean isContainsIdValue = idList.contains(idValue);
                if (list.size() > 1 || !isContainsIdValue) {
                    throw new MyException(message);
                }
            }
        }
        return true;
    }

    /**
     * 获取主键id、校验字段、以及对应数据库字段值
     */
    private void getFieldValue() {
        try {
            // 1、获取所有的字段
            Class clz = object.getClass();
            // 当父类为null的时候说明到达了最上层的父类(Object类) -> 作递归取父类属性值使用
            Map<String, Field> fieldMap = new HashMap<>();
            while (clz != null && !PACKAGE_NAME.equals(clz.getName().toLowerCase())) {
                fieldMap.putAll(
                    Arrays.stream(clz.getDeclaredFields()).collect(Collectors.toMap(Field::getName, field -> field)));
                // 得到父类,然后赋给自己
                clz = clz.getSuperclass();
            }

            // 2、取校验字段值
            for (int i = 0; i < fieldNames.length; i++) {
                Field field = fieldMap.get(fieldNames[i]);
                if (field == null) {
                    fieldValues.add(null);
                } else {
                    // 设置对象中成员 属性private为可读
                    field.setAccessible(true);
                    // 校验字段名的值 【 fieldNames中第一个字段为校验字段，其后为辅助校验字段 】
                    Object fieldValue = field.get(object);
                    fieldValues.add(fieldValue);
                }
            }

            // 3、取主键id字段值 -> 作用：判断是插入还是更新操作
            Field fieldId = fieldMap.get(idName);
            if (fieldId != null) {
                fieldId.setAccessible(true);
                idValue = (Integer)fieldId.get(object);
            }
        } catch (Exception e) {
            throw new MyException("数据库字段内容验重校验取值失败：" + e.toString());
        }
    }

    /**
     * 手动校验 (把注解@valid放在servevice层上是没有效果的，只有放在Controller上才有效果,因此通过调用ValidatorFactory工厂方法创建一个实例对象来进行手动校验 )
     * 百度建议： @Validated放到实现上,@NotNull,@Valid等声明放到接口上
     *
     * @param obj:
     *            校验对象
     * @param groups:
     *            组校验
     * @return: void
     * @author : zhengqing
     * @date : 2020/10/19 11:27
     */
    @SneakyThrows(Exception.class)
    public static void validate(Object obj, Class<?>... groups) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<Object>> set = validator.validate(obj, groups);
        for (ConstraintViolation<Object> constraintViolation : set) {
            String violationMessage = constraintViolation.getMessage();
            log.error(" 【{}】", constraintViolation.getPropertyPath() + ":" + violationMessage);
            throw new ValidationException(violationMessage);
        }
    }

}
