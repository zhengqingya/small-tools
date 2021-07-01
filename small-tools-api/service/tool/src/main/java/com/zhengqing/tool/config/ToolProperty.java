package com.zhengqing.tool.config;

import com.zhengqing.common.config.CommonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 配置信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/19 9:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(prefix = "small-tools", ignoreUnknownFields = false)
public class ToolProperty extends CommonProperty {

}
