package com.zhengqing.system.feign.fallback;

import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.system.feign.ISystemClient;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * Feign错误处理
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/4 11:53
 */
@Component
public class ISystemClientFallback implements ISystemClient {

    @Override
    public List<SysDictVO> getUpDictListFromCacheByCode(String code) {
        throw new MyException("通过类型code获取数据字典列表数据失败！");
    }

    @Override
    public ApiResult<Integer> addOrUpdateData(SysUserSaveDTO params) {
        return ApiResult.fail("更新数据失败！");
    }

    @Override
    public Integer getRequestHeaderUserId() {
        return null;
    }

}
