package com.zhengqing.tool.db.api;

import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.tool.db.service.IStDbJdbcService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 管理员api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/19 13:49
 */
@RestController
@RequestMapping("")
@Api(tags = "管理员api")
@RequiredArgsConstructor
public class StDbAdminController extends BaseController {

    private final IStDbJdbcService stDbJdbcService;

//    /**
//     * http://127.0.0.1:20030/initDb
//     */
//    @ApiOperation("初始化DB(谨慎操作)")
//    @GetMapping("initDb")
//    public String initDb() {
//        String initDbSql = "DROP DATABASE IF EXISTS `small-tools`; " +
//                "CREATE DATABASE `small-tools` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci; ";
//        String initSql = "USE `small-tools`; " + MyFileUtil.readFileContent(ProjectConstant.PROJECT_ROOT_DIRECTORY + "/doc/sql/small-tools.sql");
//        this.stDbJdbcService.execSql(initDbSql);
//        this.stDbJdbcService.execSql(initSql);
//        return "OK";
//    }

}
