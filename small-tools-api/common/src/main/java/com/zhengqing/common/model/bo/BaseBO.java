package com.zhengqing.common.model.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p> 基类参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/17 15:38
 */
@Data
@SuperBuilder
@ApiModel("基类参数")
public class BaseBO implements Serializable {

    private static final long serialVersionUID = 1L;

}
