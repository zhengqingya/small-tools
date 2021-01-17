package com.zhengqing.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.zhengqing.common.config.CommonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 配置信息
 * </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/8/19 9:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Configuration
@ConfigurationProperties(prefix = "small-tools", ignoreUnknownFields = false)
public class DemoProperty extends CommonProperty {

}
