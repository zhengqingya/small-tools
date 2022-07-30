package com.zhengqing.system.feign.fallback;

import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.system.feign.ISysPropertyFeignApi;
import com.zhengqing.system.model.dto.SysPropertySaveDTO;
import com.zhengqing.system.model.vo.SysPropertyVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p> 系统服务-系统属性 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:23
 */
@Component
public class ISysPropertyFeignFallback implements ISysPropertyFeignApi {

    @Override
    public ApiResult<SysPropertyVO> getByKey(String key) {
        return ApiResult.busy();
    }

    @Override
    public ApiResult<List<SysPropertyVO>> listByKey(List<String> keyList) {
        return ApiResult.busy();
    }

    @Override
    public ApiResult<Map<String, SysPropertyVO>> mapByKey(List<String> keyList) {
        return ApiResult.busy();
    }

    @Override
    public ApiResult<Boolean> save(SysPropertySaveDTO params) {
        return ApiResult.busy();
    }

    @Override
    public ApiResult<Boolean> saveBatch(ValidList<SysPropertySaveDTO> dataList) {
        return ApiResult.busy();
    }

}
