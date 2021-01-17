package com.zhengqing.system.api;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.util.QiniuFileUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 基础模块 - 上传文件 接口
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/14 20:13
 */
@Slf4j
@RestController
@RequestMapping("/web/api/file")
@Api(tags = "基础模块 - 上传文件接口")
public class SysFileController extends BaseController {

    @Autowired
    private QiniuFileUtil qiniuFileUtil;

    @PostMapping("uploadMultipartFile")
    @ApiOperation("上传文件")
    public String uploadMultipartFile(@RequestParam(value = "file") MultipartFile file) {
        return qiniuFileUtil.uploadFile(file, file.getOriginalFilename());
    }

    @PostMapping("uploadFile")
    @ApiOperation("上传文件")
    public String uploadFile(@RequestParam("file") File file) {
        return qiniuFileUtil.uploadFile(file, file.getName());
    }

    @GetMapping("downloadFile")
    @ApiOperation("下载文件")
    public String downloadFile(@RequestParam("fileName") String fileName) {
        return qiniuFileUtil.downloadFile(fileName, 3600);
    }

    @DeleteMapping("deleteFile")
    @ApiOperation("删除文件")
    public String deleteFile(@RequestParam String fileName) {
        return qiniuFileUtil.delete(fileName);
    }

}
