package com.zhengqing.common.base.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <p> 分页响应参数 </p>
 *
 * @author zhengqingya
 * @description {@link com.baomidou.mybatisplus.core.metadata.IPage }
 * @date 2021/8/18 16:14
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("基类响应参数")
public class PageVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    Long current;
    /**
     * 每页显示条数
     */
    Long size;
    /**
     * 当前分页总页数
     */
    Long pages;
    /**
     * 当前满足条件总行数
     */
    Long total;
    /**
     * 分页记录列表
     */
    List<T> records;

}
