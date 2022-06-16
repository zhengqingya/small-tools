package com.zhengqing.system.api;

import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.util.QiniuFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * <p>
 * 基础模块 - 上传文件 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/14 20:13
 */
@Slf4j
@RestController
@RequestMapping("/web/api/file")
@Api(tags = "基础模块 - 上传文件接口")
public class SysFileController extends BaseController {

    @Resource
    private QiniuFileUtil qiniuFileUtil;

    @PostMapping("uploadMultipartFile")
    @ApiOperation("上传文件")
    public String uploadMultipartFile(@RequestParam(value = "file") MultipartFile file) {
        return this.qiniuFileUtil.uploadFile(file, file.getOriginalFilename());
    }

    @PostMapping("uploadFile")
    @ApiOperation("上传文件")
    public String uploadFile(@RequestParam("file") File file) {
        return this.qiniuFileUtil.uploadFile(file, file.getName());
    }

    @GetMapping("downloadFile")
    @ApiOperation("下载文件")
    public String downloadFile(@RequestParam("fileName") String fileName) {
        return this.qiniuFileUtil.downloadFile(fileName, 3600);
    }

    @DeleteMapping("deleteFile")
    @ApiOperation("删除文件")
    public String deleteFile(@RequestParam String fileName) {
        return this.qiniuFileUtil.delete(fileName);
    }

}
