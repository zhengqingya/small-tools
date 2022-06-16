package com.zhengqing.system.feign;

import com.zhengqing.common.base.http.ApiResult;
import com.zhengqing.common.feign.constant.RpcConstant;
import com.zhengqing.system.feign.fallback.ISysPropertyFeignFallback;
import com.zhengqing.system.model.dto.SysPropertySaveDTO;
import com.zhengqing.system.model.vo.SysPropertyVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统服务-系统属性
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:22
 */
@FeignClient(value = RpcConstant.RPC_SYSTEM,
        path = RpcConstant.RPC_API_PREFIX_SYSTEM + "/property",
        contextId = "ISysPropertyFeignApi",
        fallback = ISysPropertyFeignFallback.class)
public interface ISysPropertyFeignApi {

    /**
     * 根据属性key查询
     *
     * @param key {@link com.zhengqing.system.enums.SysPropertyKeyEnum}
     * @return 属性值
     */
    @ApiOperation("根据属性key查询")
    @GetMapping("/getByKey")
    ApiResult<SysPropertyVO> getByKey(@RequestParam String key);

    /**
     * 根据属性key查询
     *
     * @param keyList {@link com.zhengqing.system.enums.SysPropertyKeyEnum}
     * @return 属性值
     */
    @ApiOperation("根据属性key查询")
    @GetMapping("/listByKey")
    ApiResult<List<SysPropertyVO>> listByKey(@RequestParam List<String> keyList);

    /**
     * 根据属性key查询
     *
     * @param keyList {@link com.zhengqing.system.enums.SysPropertyKeyEnum}
     * @return key -> 属性值
     */
    @ApiOperation("根据属性key查询")
    @GetMapping("/mapByKey")
    ApiResult<Map<String, SysPropertyVO>> mapByKey(@RequestParam List<String> keyList);

    /**
     * 批量保存
     *
     * @param dataList 保存参数
     * @return true->成功 false->失败
     */
    @ApiOperation("批量保存")
    @PostMapping("/saveBatch")
    ApiResult<Boolean> saveBatch(@Validated @RequestBody List<SysPropertySaveDTO> dataList);

}
