package com.zhengqing.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.model.dto.DemoListDTO;
import com.zhengqing.demo.model.vo.DemoListVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 测试demo Mapper 接口
 * </p>
 *
 * @author : zhengqing
 * @description:
 * @date : 2021/01/13 10:11
 */
public interface DemoMapper extends BaseMapper<Demo> {

    /**
     * 列表分页
     *
     * @param page:   分页数据
     * @param filter: 查询过滤参数
     * @return: 查询结果
     * @author : zhengqing
     * @date : 2021/01/13 10:11
     */
    IPage<DemoListVO> selectDataList(IPage page, @Param("filter") DemoListDTO filter);

    /**
     * 列表
     *
     * @param filter: 查询过滤参数
     * @return: 查询结果
     * @author : zhengqing
     * @date : 2021/01/13 10:11
     */
    List<DemoListVO> selectDataList(@Param("filter") DemoListDTO filter);

    /**
     * 批量插入数据
     *
     * @param demoList: list
     * @return void
     * @author zhengqingya
     * @date 2021/5/28 14:28
     */
    void insertBatch(@Param("demoList") List<Demo> demoList);

}
