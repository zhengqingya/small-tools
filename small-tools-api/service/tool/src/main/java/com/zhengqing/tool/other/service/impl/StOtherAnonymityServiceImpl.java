package com.zhengqing.tool.other.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.util.MyBeanUtil;
import com.zhengqing.tool.other.entity.StOtherAnonymity;
import com.zhengqing.tool.other.enums.StOtherAnonymityStatusEnum;
import com.zhengqing.tool.other.mapper.StOtherAnonymityMapper;
import com.zhengqing.tool.other.model.dto.StOtherAnonymityHandleDTO;
import com.zhengqing.tool.other.model.dto.StOtherAnonymityListDTO;
import com.zhengqing.tool.other.model.dto.StOtherAnonymitySaveDTO;
import com.zhengqing.tool.other.model.vo.StOtherAnonymityListVO;
import com.zhengqing.tool.other.service.IStOtherAnonymityService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 小工具 - 其它 - 匿名事件表 服务实现类
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-10-25 13:27:16
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StOtherAnonymityServiceImpl extends ServiceImpl<StOtherAnonymityMapper, StOtherAnonymity>
    implements IStOtherAnonymityService {

    @Autowired
    private StOtherAnonymityMapper stOtherAnonymityMapper;

    @Override
    public IPage<StOtherAnonymityListVO> listPage(StOtherAnonymityListDTO params) {
        IPage<StOtherAnonymityListVO> result = stOtherAnonymityMapper.selectStOtherAnonymitys(new Page<>(), params);
        List<StOtherAnonymityListVO> list = result.getRecords();
        handleResultData(list);
        return result;
    }

    @Override
    public List<StOtherAnonymityListVO> list(StOtherAnonymityListDTO params) {
        List<StOtherAnonymityListVO> list = stOtherAnonymityMapper.selectStOtherAnonymitys(params);
        handleResultData(list);
        return list;
    }

    /**
     * 处理数据
     *
     * @param list:
     *            数据
     * @return: void
     * @author : zhengqing
     * @date : 2020-10-25 13:27:16
     */
    private void handleResultData(List<StOtherAnonymityListVO> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(e -> e.setStatusName(StOtherAnonymityStatusEnum.getEnum(e.getStatus()).getDesc()));
        }
    }

    @Override
    public Integer addOrUpdateData(StOtherAnonymitySaveDTO params) {
        Integer anonymityId = params.getId();
        StOtherAnonymity anonymity = MyBeanUtil.copyProperties(params, StOtherAnonymity.class);
        if (anonymityId == null) {
            anonymity.setStatus(StOtherAnonymityStatusEnum.未处理.getStatus());
            anonymity.insert();
        } else {
            anonymity.updateById();
        }
        return anonymity.getId();
    }

    @Override
    public void handle(StOtherAnonymityHandleDTO params) {
        Integer id = params.getId();
        String anonymousHandlerName = params.getAnonymousHandlerName();
        String remark = params.getRemark();
        StOtherAnonymity anonymity = new StOtherAnonymity();
        anonymity.setId(id);
        anonymity.setAnonymousHandlerName(anonymousHandlerName);
        anonymity.setStatus(StOtherAnonymityStatusEnum.已处理.getStatus());
        anonymity.setRemark(remark);
        anonymity.setHandleTime(new Date());
        anonymity.updateById();
    }

}
