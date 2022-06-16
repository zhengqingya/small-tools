package com.zhengqing.tool.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.util.MyBeanUtil;
import com.zhengqing.tool.db.entity.StDbDataSource;
import com.zhengqing.tool.db.enums.StDbDataSourceTypeEnum;
import com.zhengqing.tool.db.mapper.StDbDataSourceMapper;
import com.zhengqing.tool.db.model.dto.StDbDataSourceListDTO;
import com.zhengqing.tool.db.model.dto.StDbDataSourceSaveDTO;
import com.zhengqing.tool.db.model.vo.StDbDataSourceListVO;
import com.zhengqing.tool.db.service.IStDbDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 小工具 - 数据库 - 数据源配置信息表 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-09-02 14:45:55
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StDbDataSourceServiceImpl extends ServiceImpl<StDbDataSourceMapper, StDbDataSource>
        implements IStDbDataSourceService {

    @Resource
    private StDbDataSourceMapper stDbDataSourceMapper;

    @Override
    public IPage<StDbDataSourceListVO> listPage(StDbDataSourceListDTO params) {
        IPage<StDbDataSourceListVO> result = this.stDbDataSourceMapper.selectStDbDataSources(new Page<>(), params);
        List<StDbDataSourceListVO> list = result.getRecords();
        this.handleResultData(list);
        return result;
    }

    @Override
    public List<StDbDataSourceListVO> list(StDbDataSourceListDTO params) {
        List<StDbDataSourceListVO> list = this.stDbDataSourceMapper.selectStDbDataSources(params);
        this.handleResultData(list);
        return list;
    }

    /**
     * 处理数据
     *
     * @param list: 数据
     * @return void
     * @author zhengqingya
     * @date 2020/9/2 15:22
     */
    private void handleResultData(List<StDbDataSourceListVO> list) {
        list.forEach(e -> e.setTypeName(StDbDataSourceTypeEnum.getEnum(e.getType()).getDesc()));
    }

    @Override
    public Integer addOrUpdateData(StDbDataSourceSaveDTO params) {
        Integer stDbDataSourceId = params.getId();
        StDbDataSource stDbDataSource = MyBeanUtil.copyProperties(params, StDbDataSource.class);
        stDbDataSource
                .setDriverClassName(StDbDataSourceTypeEnum.getEnum(stDbDataSource.getType()).getDriverClassName());
        if (stDbDataSourceId == null) {
            this.stDbDataSourceMapper.insert(stDbDataSource);
        } else {
            this.stDbDataSourceMapper.updateById(stDbDataSource);
        }
        return stDbDataSource.getId();
    }

    @Override
    public StDbDataSourceListVO detail(Integer id) {
        return this.stDbDataSourceMapper.detail(id);
    }

}
