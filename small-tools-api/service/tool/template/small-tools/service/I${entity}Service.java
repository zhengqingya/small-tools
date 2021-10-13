package ${package.service};

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>  ${tableComment} 服务类 </p>
 *
 * @author ${author}
 * @description
 * @date ${date}
 */
public interface I${entity}Service extends IService<${entity}> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author ${author}
     * @date ${date}
     */
    IPage<${entity}ListVO> page(${entity}ListDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author ${author}
     * @date ${date}
     */
    List<${entity}ListVO> list(${entity}ListDTO params);

    /**
     * 详情
     *
     * @param ${primaryColumnNameJavaLower} 主键ID
     * @return 详情
     * @author ${author}
     * @date ${date}
     */
    ${entity} detail(${primaryColumnTypeJava} ${primaryColumnNameJavaLower});

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return 主键id
     * @author ${author}
     * @date ${date}
     */
     ${primaryColumnTypeJava} addOrUpdateData(${entity}SaveDTO params);

     /**
      * 删除数据
      *
      * @param ${primaryColumnNameJavaLower} 主键ID
      * @return void
      * @author ${author}
      * @date ${date}
      */
      void deleteData(${primaryColumnTypeJava} ${primaryColumnNameJavaLower});

}
