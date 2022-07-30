package com.zhengqing.system.feign;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.db.mapper.MyBaseMapper;
import com.zhengqing.common.web.util.RequestContextUtil;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
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

    private MyBaseMapper myBaseMapper;

    @Override
    public List<SysDictVO> getUpDictListFromCacheByCode(String code) {
        return this.dictService.listFromCacheByCode(Lists.newArrayList(code)).get(code);
    }

    @Override
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String test() {
        this.myBaseMapper.execSql("UPDATE t_demo SET username = 'system:" + RandomUtil.randomString(5) + "' WHERE id = 1;");
        return "666";
    }
}
