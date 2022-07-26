package com.zhengqing.demo.model.vo;

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
public class DemoJacksonVO {

    private Long id;

    private String name;

    private Boolean status;

    private Date time;

    private List<DemoJacksonVO> list;

    private DemoJacksonVO obj;

}
