package com.zhengqing.nacos.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * <p> 属性 配置类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/12/28 15:53
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "order.print", ignoreUnknownFields = true)
public class AppConfig {

    /**
     * 订单备注打印过滤关键字前缀
     */
    private List<String> remarkFilterStrList;

}
