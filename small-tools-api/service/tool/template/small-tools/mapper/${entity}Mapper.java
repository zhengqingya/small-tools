package ${package.mapper};

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p> ${tableComment} Mapper 接口 </p>
 *
 * @author ${author}
 * @description
 * @date ${date}
 */
public interface ${entity}Mapper extends BaseMapper<${entity}> {

        /**
         * 列表分页
         *
         * @param page: 分页数据
         * @param filter: 查询过滤参数
         * @return 查询结果
         * @author ${author}
         * @date ${date}
         */
        IPage<${entity}ListVO> selectDataList(IPage page, @Param("filter") ${entity}ListDTO filter);

        /**
         * 列表
         *
         * @param filter: 查询过滤参数
         * @return 查询结果
         * @author ${author}
         * @date ${date}
         */
        List<${entity}ListVO> selectDataList(@Param("filter") ${entity}ListDTO filter);

        }
