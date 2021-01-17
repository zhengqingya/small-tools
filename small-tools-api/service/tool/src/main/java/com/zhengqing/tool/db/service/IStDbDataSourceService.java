package com.zhengqing.tool.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.db.entity.StDbDataSource;
import com.zhengqing.tool.db.model.dto.StDbDataSourceListDTO;
import com.zhengqing.tool.db.model.dto.StDbDataSourceSaveDTO;
import com.zhengqing.tool.db.model.vo.StDbDataSourceListVO;
import java.util.List;

/**
 * <p>
 * 小工具 - 数据库 - 数据源配置信息表 服务类
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-09-02 14:45:55
 */
public interface IStDbDataSourceService extends IService<StDbDataSource> {

    /**
     * 列表分页
     *
     * @param params:
     *            查询参数
     * @return
     */
    IPage<StDbDataSourceListVO> listPage(StDbDataSourceListDTO params);

    /**
     * 列表
     *
     * @param params:
     *            查询参数
     * @return
     */
    List<StDbDataSourceListVO> list(StDbDataSourceListDTO params);

    /**
     * 新增或更新
     *
     * @param params
     */
    Integer addOrUpdateData(StDbDataSourceSaveDTO params);

    /**
     * 详情
     *
     * @param id:
     * @return: com.zhengqing.modules.smalltools.db.model.vo.StDbDataSourceVO
     * @author : zhengqing
     * @date : 2020/9/4 13:59
     */
    StDbDataSourceListVO detail(Integer id);

}
