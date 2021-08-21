package ${ package.vo };

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>${tableComment}展示视图</p>
 *
 * @author ${ author }
 * @description
 * @date ${date}
 */
@Data
@ApiModel("${tableComment}展示视图")
public class ${entity}ListVO {

<#list columnInfoList as item>
    <#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_valid">
        @ApiModelProperty("${item.columnComment}")
        private ${item.columnTypeJava} ${item.columnNameJavaLower};

    </#if>
</#list>
}
