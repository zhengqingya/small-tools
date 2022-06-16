package com.zhengqing.system.feign;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.http.ApiResult;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.common.feign.constant.RpcConstant;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> 系统服务-数据字典 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:25
 */
@ApiIgnore
@Api(tags = "系统服务-数据字典")
@RestController
@RequestMapping(RpcConstant.RPC_API_PREFIX_SYSTEM + "/dict")
public class SysDictFeignService implements ISysDictFeignApi {

    @Resource
    private ISysDictService sysDictService;

    @Override
    public ApiResult<List<SysDictVO>> getByOpenCode(String code) {
        return ApiResult.ok(this.sysDictService.listByOpenCode(Lists.newArrayList(code)).get(code));
    }

    @Override
    public ApiResult<Map<String, List<SysDictVO>>> listByOpenCode(List<String> codeList) {
        return ApiResult.ok(this.sysDictService.listByOpenCode(codeList));
    }

    @Override
    public ApiResult<Boolean> addOrUpdateBatch(Map<String, ValidList<SysDictSaveBatchDTO>> dictDataMap) {
        this.sysDictService.addOrUpdateBatch(dictDataMap, true);
        return ApiResult.ok(true);
    }

}
