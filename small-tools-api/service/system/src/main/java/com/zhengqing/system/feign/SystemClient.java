package com.zhengqing.system.feign;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhengqing.common.http.ApiResult;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysUserService;

import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * Feign接口类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2021/1/1 21:45
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class SystemClient implements ISystemClient {

    private ISysDictService dictService;

    private ISysUserService sysUserService;

    @Override
    @GetMapping(API_DICT + "/getUpDictListFromCacheByCode")
    public List<SysDictVO> getUpDictListFromCacheByCode(String code) {
        return dictService.getUpDictListFromCacheByCode(code);
    }

    @Override
    @PostMapping(API_USER)
    public ApiResult<Integer> addOrUpdateData(SysUserSaveDTO params) {
        return ApiResult.build(sysUserService.addOrUpdateData(params));
    }

}
