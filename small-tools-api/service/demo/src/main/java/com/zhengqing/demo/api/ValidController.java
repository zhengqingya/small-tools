package com.zhengqing.demo.api;

import com.alibaba.fastjson.JSON;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <p> 校验 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/10/20 17:47
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_DEMO + "/api/valid")
@Api(tags = "test-valid")
public class ValidController {

    @PostMapping("/test1")
    public User test1(@Validated @RequestBody User params) {
        System.err.println(JSON.toJSONString(params));
        return params;
    }

    @GetMapping("/test2")
    public User test2(@Validated @ModelAttribute User params) {
        System.err.println(JSON.toJSONString(params));
        return params;
    }

    @PostMapping("")
    @ApiOperation("新增")
    public User add(@Validated @RequestBody User params) {
        System.err.println(JSON.toJSONString(params));
        return params;
    }

    @PutMapping("")
    @ApiOperation("更新")
    public User update(@Validated(UpdateGroup.class) @RequestBody User params) {
        System.err.println(JSON.toJSONString(params));
        return params;
    }

    @Data
    static class User {
        @NotNull(groups = {UpdateGroup.class}, message = "ID不能为空!")
        private Integer id;

        @Range(min = 1, max = 3, message = "类型值范围: 1-3")
        @ApiModelProperty(example = "1")
        private Integer type;

        @NotBlank(message = "名称不能为空！")
        @ApiModelProperty(example = "zhengqingya")
        private String name;

        @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
        @ApiModelProperty(example = "15153306666")
        private String phone;

        @Email(message = "邮箱格式不对")
        @ApiModelProperty(example = "666@qq.com")
        private String email;

        @Valid // 校验嵌套对象
        private User user;
    }

}


