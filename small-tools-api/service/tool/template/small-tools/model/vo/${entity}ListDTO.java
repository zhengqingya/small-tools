package ${package.dto};

import com.zhengqing.modules.common.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> ${tableComment}查询参数 </p>
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
@ApiModel("${tableComment}查询参数")
public class ${entity}ListDTO extends BaseDTO {

<#list queryColumnInfoList as item>
@ApiModelProperty("${item.columnComment}")
private ${item.columnTypeJava} ${item.columnNameJavaLower};

</#list>
        }
