package com.zhengqing.system.feign;

import com.zhengqing.common.base.http.ApiResult;
import com.zhengqing.common.core.constant.AppConstant;
import com.zhengqing.common.feign.constant.RpcConstant;
import com.zhengqing.common.feign.rpc.IBaseClient;
import com.zhengqing.system.feign.fallback.ISystemClientFallback;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * Feign接口类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/1 21:45
 */
@FeignClient(value = RpcConstant.RPC_SYSTEM,
        contextId = "ISystemClient",
        fallback = ISystemClientFallback.class)
public interface ISystemClient extends IBaseClient {

    String API_DICT = API_PREFIX + "/" + AppConstant.RPC_SYSTEM + "/dict";
    String API_USER = API_PREFIX + "/" + AppConstant.RPC_SYSTEM + "/user";

    /**
     * 通过类型code获取数据字典列表数据 - 从缓存中取数据（只有启用的数据）
     *
     * @param code 类型编码
     * @return 数据字典列表数据
     * @author zhengqingya
     * @date 2020/9/12 17:38
     */
    @GetMapping(API_DICT + "/getUpDictListFromCacheByCode")
    List<SysDictVO> getUpDictListFromCacheByCode(@RequestParam String code);

    @PostMapping(API_USER)
    ApiResult<Integer> addOrUpdateData(@RequestBody SysUserSaveDTO params);

    /**
     * 测试多线程异步+feign传递请求头信息
     *
     * @return 用户id
     * @author zhengqingya
     * @date 2021/6/30 10:44 下午
     */
    @GetMapping(API_USER + "/getRequestHeaderUserId")
    Integer getRequestHeaderUserId();

}
