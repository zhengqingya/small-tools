package ${package.api};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.modules.common.api.BaseController;
import com.zhengqing.modules.common.validator.fieldrepeat.Update;
import com.zhengqing.modules.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.modules.smalltools.db.service.IStDbDataSourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * <p> ${tableComment} 接口 </p>
 *
 * @author ${author}
 * @description
 * @date ${date}
 */
@RestController
@RequestMapping("/api/${moduleName}/${entityNameLower}")
@Api(tags = {"${tableComment}接口"})
public class ${entity}Controller extends BaseController {

    @Autowired
    private  I${entity}Service ${entityNameLower}Service;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<${entity}ListVO> page(@Validated @ModelAttribute ${entity}ListDTO params) {
        return this.${entityNameLower}Service.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<${entity}ListVO> list(@Validated @ModelAttribute ${entity}ListDTO params) {
        return this.${entityNameLower}Service.list(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public ${entity} detail(@RequestParam ${primaryColumnTypeJava} ${primaryColumnNameJavaLower}) {
        return this.${entityNameLower}Service.detail(${primaryColumnNameJavaLower});
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public ${primaryColumnTypeJava} add(@Validated @RequestBody ${entity}SaveDTO params) {
        return this.${entityNameLower}Service.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public ${primaryColumnTypeJava} update(@Validated(UpdateGroup.class) @RequestBody ${entity}SaveDTO params) {
        return this.${entityNameLower}Service.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam ${primaryColumnTypeJava} ${primaryColumnNameJavaLower}) {
        this.${entityNameLower}Service.deleteData(${primaryColumnNameJavaLower});
    }

}
