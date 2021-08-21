package ${package.dto};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> ${tableComment}保存提交参数 </p>
 *
 * @author ${ author }
 * @description
 * @date ${date}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("${tableComment}保存提交参数")
public class ${entity}SaveDTO extends BaseDTO {

<#list columnInfoList as item>
<#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_valid">
@ApiModelProperty("${item.columnComment}")
<#if item.ifPrimaryKey>
@NotNull(groups = {Update.class}, message = "${item.columnComment}不能为空!")
</#if>
private ${item.columnTypeJava} ${item.columnNameJavaLower};

</#if>
</#list>

        }
