package com.zhengqing.tool.generator.model.bo;

import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.generator.model.vo.CgProjectTemplateListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p> 代码生成参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/21 2:44 下午
 */
@Data
@ApiModel("代码生成参数")
public class CgGenerateCodeBO {

    @ApiModelProperty(value = "数据源ID")
    private Integer projectReDbDataSourceId;

    @ApiModelProperty(value = "数据库表名")
    private String tableName;

    @ApiModelProperty(value = "可用于检索的字段")
    private List<String> queryColumnList;

    @ApiModelProperty(value = "父包名")
    private String parentPackageName;

    @ApiModelProperty(value = "包名")
    private String packageName;

    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @ApiModelProperty(value = "数据类型（1：默认数据 2：测试模板生成配置数据）")
    private Integer dataType;

    @ApiModelProperty(value = "是否为测试模板生成数据（true: 是 false: 不是）")
    private Boolean ifTestTemplateData;

    @ApiModelProperty(value = "模板ID (测试模板生成数据时使用)")
    private Integer projectTemplateId;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "包路径信息")
    private Map<Integer, String> packageNameInfoMap;

    @ApiModelProperty(value = "关联模板数据")
    private List<CgProjectTemplateListVO> projectTemplateList;

    @ApiModelProperty(value = "表字段信息")
    private StDbTableColumnListVO columnInfo;

}
