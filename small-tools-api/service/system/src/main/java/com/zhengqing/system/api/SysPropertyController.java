package com.zhengqing.system.api;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.system.model.dto.SysPropertySaveDTO;
import com.zhengqing.system.model.vo.SysPropertyListVO;
import com.zhengqing.system.service.ISysPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * <p> 系统管理-系统属性 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@RestController
@RequestMapping("/web/api/property")
@Api(tags = {"系统管理 - 系统属性接口"})
public class SysPropertyController extends BaseController {

    @Autowired
    private ISysPropertyService sysPropertyService;

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysPropertyListVO> list(@RequestParam List<String> keyList) {
        return this.sysPropertyService.list(keyList);
    }

    @NoRepeatSubmit
    @PostMapping("saveBatch")
    @ApiOperation("批量保存")
    public void saveBatch(@Validated @RequestBody Map<String, List<SysPropertySaveDTO>> dataMap) {
        this.sysPropertyService.saveBatch(dataMap);
    }

    @DeleteMapping("deleteByKey")
    @ApiOperation("根据属性key删除数据")
    public void deleteByKey(@RequestParam String key) {
        this.sysPropertyService.deleteByKey(key);
    }

}
