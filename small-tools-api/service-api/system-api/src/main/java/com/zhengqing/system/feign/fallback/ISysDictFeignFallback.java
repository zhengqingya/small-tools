package com.zhengqing.system.feign.fallback;

import com.zhengqing.common.base.http.ApiResult;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.system.feign.ISysDictFeignApi;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p> 系统服务-数据字典 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:23
 */
@Component
public class ISysDictFeignFallback implements ISysDictFeignApi {

    @Override
    public ApiResult<List<SysDictVO>> getByOpenCode(String code) {
        return ApiResult.busy();
    }

    @Override
    public ApiResult<Map<String, List<SysDictVO>>> listByOpenCode(List<String> codeList) {
        return ApiResult.busy();
    }

    @Override
    public ApiResult<Boolean> addOrUpdateBatch(Map<String, ValidList<SysDictSaveBatchDTO>> dictDataMap) {
        return ApiResult.busy();
    }

}
