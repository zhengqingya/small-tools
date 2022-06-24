package com.zhengqing.system.feign;

import com.zhengqing.common.base.constant.RpcConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.system.feign.fallback.ISysDictFeignFallback;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统服务-数据字典
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:22
 */
@FeignClient(value = RpcConstant.RPC_SYSTEM,
        path = RpcConstant.RPC_API_PREFIX_SYSTEM + "/dict",
        contextId = "ISysDictFeignApi",
        fallback = ISysDictFeignFallback.class)
public interface ISysDictFeignApi {

    @ApiOperation("通过编码获取数据字典列表（只含启用数据）")
    @GetMapping("/getByOpenCode")
    ApiResult<List<SysDictVO>> getByOpenCode(@RequestParam String code);

    @ApiOperation("通过编码获取数据字典列表（只含启用数据）")
    @GetMapping("/listByOpenCode")
    ApiResult<Map<String, List<SysDictVO>>> listByOpenCode(@RequestParam List<String> codeList);

    @ApiOperation("批量保存")
    @PutMapping("/addOrUpdateBatch")
    ApiResult<Boolean> addOrUpdateBatch(@Validated @RequestBody Map<String, ValidList<SysDictSaveBatchDTO>> dictDataMap);

}
