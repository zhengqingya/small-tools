package com.zhengqing.mavenplugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

@Slf4j
@Mojo(
        // 标识
        name = "myPlugin"
)
public class MyPlugin extends AbstractMojo {

    /**
     * 接收使用插件时传递的参数
     */
    @Parameter
    private String msg;

    @Parameter
    private List<String> options;

    @Parameter(property = "args")
    private String args;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("****** msg：[{}]", this.msg);
        log.info("****** options：[{}]", this.options);
        log.info("****** args：[{}]", this.args);
    }

}