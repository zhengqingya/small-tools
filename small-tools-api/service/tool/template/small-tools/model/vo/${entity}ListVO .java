package ${ package.vo };

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>${tableComment}-响应参数</p>
 *
 * @author ${ author }
 * @description
 * @date ${date}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("${tableComment}-响应参数")
public class ${entity}ListVO {

<#list columnInfoList as item>
    <#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_deleted">
    @ApiModelProperty("${item.columnComment}")
    private ${item.columnTypeJava} ${item.columnNameJavaLower};

    </#if>
</#list>
}
