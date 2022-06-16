package com.zhengqing.tool.other.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.other.entity.StOtherAnonymity;
import com.zhengqing.tool.other.model.dto.StOtherAnonymityListDTO;
import com.zhengqing.tool.other.model.vo.StOtherAnonymityListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小工具 - 其它 - 匿名事件表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-10-25 13:27:16
 */
public interface StOtherAnonymityMapper extends BaseMapper<StOtherAnonymity> {

    /**
     * 列表分页
     *
     * @param page    分页数据
     * @param filter: 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2020-10-25 13:27:16
     */
    IPage<StOtherAnonymityListVO> selectStOtherAnonymitys(IPage page, @Param("filter") StOtherAnonymityListDTO filter);

    /**
     * 列表
     *
     * @param filter: 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2020-10-25 13:27:16
     */
    List<StOtherAnonymityListVO> selectStOtherAnonymitys(@Param("filter") StOtherAnonymityListDTO filter);

}
