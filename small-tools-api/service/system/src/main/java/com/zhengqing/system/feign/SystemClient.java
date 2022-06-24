package com.zhengqing.system.feign;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.feign.util.RequestContextUtil;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Feign接口类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/1 21:45
 */
@Slf4j
@ApiIgnore()
@RestController
@AllArgsConstructor
public class SystemClient implements ISystemClient {

    private ISysDictService dictService;

    private ISysUserService sysUserService;

    @Override
    @GetMapping(API_DICT + "/getUpDictListFromCacheByCode")
    public List<SysDictVO> getUpDictListFromCacheByCode(String code) {
        return this.dictService.listFromCacheByCode(Lists.newArrayList(code)).get(code);
    }

    @Override
    @PostMapping(API_USER)
    public ApiResult<Integer> addOrUpdateData(SysUserSaveDTO params) {
        return ApiResult.ok(this.sysUserService.addOrUpdateData(params));
    }

    @Override
    public Integer getRequestHeaderUserId() {
        Map<String, String> headerMap = RequestContextUtil.getHeaderMap();
        String userId = headerMap.get("userId");
        log.info("[system] 获取请求头值: {}", userId);
        return Integer.valueOf(userId);
    }

}
