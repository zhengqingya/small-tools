package ${package.impl};

import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.utils.MyBeanUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> ${tableComment} 服务实现类 </p>
 *
 * @author ${author}
 * @description
 * @date ${date}
 */
@Slf4j
@Service
public class ${entity}ServiceImpl extends ServiceImpl<${entity}Mapper, ${entity}> implements I${entity}Service {

    @Autowired
    private ${entity}Mapper ${entityNameLower}Mapper;

    @Override
    public IPage<${entity}ListVO> page(${entity}ListDTO params) {
        IPage<${entity}ListVO> result = this.${entityNameLower}Mapper.selectDataList(new Page<>(), params);
        List<${entity}ListVO> list = result.getRecords();
        this.handleResultData(list);
        return result;
    }

    @Override
    public List<${entity}ListVO> list(${entity}ListDTO params) {
        List<${entity}ListVO> list =  this.${entityNameLower}Mapper.selectDataList(params);
        this.handleResultData(list);
        return list;
    }

    /**
     * 处理数据
     *
     * @param list 数据
     * @return void
     * @author ${author}
     * @date ${date}
     */
    private void handleResultData(List<${entity}ListVO> list) {

    }

    @Override
    public ${entity} detail(${primaryColumnTypeJava} ${primaryColumnNameJavaLower}){
        ${entity} detailData = this.${entityNameLower}Mapper.selectById(${primaryColumnNameJavaLower});
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${primaryColumnTypeJava} addOrUpdateData(${entity}SaveDTO params) {
<#list columnInfoList as item>
<#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_deleted">
        ${item.columnTypeJava} ${item.columnNameJavaLower} = params.get${item.columnNameJavaUpper}();
</#if>
</#list>

        ${entity} ${entityNameLower} = ${entity}.builder()
<#list columnInfoList as item>
<#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_deleted">
            .${item.columnNameJavaLower}(${item.columnNameJavaLower})
</#if>
</#list>
            .build();

        if (${primaryColumnNameJavaLower}==null) {
            // 新增
            ${entityNameLower}.insert();
            ${primaryColumnNameJavaLower} = ${entityNameLower}.get${primaryColumnNameJavaUpper}();
        } else {
            // 更新
            ${entityNameLower}.updateById();
        }
        return ${primaryColumnNameJavaLower};
    }

    @Override
    public void deleteData(${primaryColumnTypeJava} ${primaryColumnNameJavaLower}){
        this.${entityNameLower}Mapper.deleteById(${primaryColumnNameJavaLower});
    }

}
