package com.zhengqing.demo.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.web.config.jackson.ToLongSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * jackson
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)  // 对单个类配置jackson属性命名
public class DemoJacksonVO extends BaseDTO {

    private Long id;

    @JsonSerialize(using = ToLongSerializer.class)
    private Long no;

    private Integer sex;

    private String name;

    @JsonIgnore
    private String password;

    private String hello_world;

    private String hiWorld;

    private Boolean status;

    private Date time;

    private List<DemoJacksonVO> list;

    private DemoJacksonVO obj;

}