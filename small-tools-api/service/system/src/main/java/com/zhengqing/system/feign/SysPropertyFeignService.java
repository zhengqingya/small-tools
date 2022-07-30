package com.zhengqing.system.feign;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.RpcConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.system.model.dto.SysPropertySaveDTO;
import com.zhengqing.system.model.vo.SysPropertyVO;
import com.zhengqing.system.service.ISysPropertyService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> 系统服务-系统属性 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 11:25
 */
@ApiIgnore
@Api(tags = "系统服务-系统属性")
@RestController
@RequestMapping(RpcConstant.RPC_API_PREFIX_SYSTEM + "/property")
public class SysPropertyFeignService implements ISysPropertyFeignApi {

    @Resource
    private ISysPropertyService sysPropertyService;

    @Override
    public ApiResult<SysPropertyVO> getByKey(String key) {
        return ApiResult.ok(this.sysPropertyService.mapByKey(Lists.newArrayList(key)).get(key));
    }

    @Override
    public ApiResult<List<SysPropertyVO>> listByKey(List<String> keyList) {
        return ApiResult.ok(this.sysPropertyService.listByKey(keyList));
    }

    @Override
    public ApiResult<Map<String, SysPropertyVO>> mapByKey(List<String> keyList) {
        return ApiResult.ok(this.sysPropertyService.mapByKey(keyList));
    }

    @Override
    public ApiResult<Boolean> save(SysPropertySaveDTO params) {
        ValidList<SysPropertySaveDTO> dataList = new ValidList<>();
        dataList.add(params);
        this.sysPropertyService.saveBatch(dataList);
        return ApiResult.ok(true);
    }

    @Override
    public ApiResult<Boolean> saveBatch(ValidList<SysPropertySaveDTO> dataList) {
        this.sysPropertyService.saveBatch(dataList);
        return ApiResult.ok(true);
    }

}
