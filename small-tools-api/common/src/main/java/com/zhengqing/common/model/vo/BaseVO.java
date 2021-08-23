package com.zhengqing.common.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p> 基类响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/18 16:14
 */
@Data
@SuperBuilder
@ApiModel("基类响应参数")
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

}
