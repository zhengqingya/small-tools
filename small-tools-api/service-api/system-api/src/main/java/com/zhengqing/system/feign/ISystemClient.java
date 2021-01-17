package com.zhengqing.system.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.http.ApiResult;
import com.zhengqing.common.rpc.IBaseClient;
import com.zhengqing.system.feign.fallback.ISystemClientFallback;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;

/**
 * <p>
 * Feign接口类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2021/1/1 21:45
 */
@FeignClient(value = AppConstant.APPLICATION_NAME_SYSTEM, fallback = ISystemClientFallback.class)
public interface ISystemClient extends IBaseClient {

    String API_DICT = API_PREFIX + "/" + AppConstant.APPLICATION_NAME_SYSTEM + "/dict";
    String API_USER = API_PREFIX + "/" + AppConstant.APPLICATION_NAME_SYSTEM + "/user";

    /**
     * 通过类型code获取数据字典列表数据 - 从缓存中取数据（只有启用的数据）
     *
     * @param code:
     *            类型编码
     * @return: 数据字典列表数据
     * @author : zhengqing
     * @date : 2020/9/12 17:38
     */
    @GetMapping(API_DICT + "/getUpDictListFromCacheByCode")
    List<SysDictVO> getUpDictListFromCacheByCode(@RequestParam String code);

    @PostMapping(API_USER)
    ApiResult<Integer> addOrUpdateData(@RequestBody SysUserSaveDTO params);

}
