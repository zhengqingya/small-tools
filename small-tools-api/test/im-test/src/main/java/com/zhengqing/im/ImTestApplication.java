package com.zhengqing.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.zhengqing"})
@SpringBootApplication
public class ImTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImTestApplication.class, args);
    }
}
