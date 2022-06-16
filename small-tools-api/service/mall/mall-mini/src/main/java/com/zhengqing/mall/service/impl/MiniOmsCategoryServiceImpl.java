package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengqing.mall.common.model.vo.PmsCategoryReSpuListVO;
import com.zhengqing.mall.entity.PmsCategory;
import com.zhengqing.mall.mapper.PmsCategoryMapper;
import com.zhengqing.mall.mini.model.dto.MiniPmsCategoryListDTO;
import com.zhengqing.mall.mini.model.dto.MiniPmsCategoryPageDTO;
import com.zhengqing.mall.mini.model.dto.MiniPmsCategoryReSpuPageDTO;
import com.zhengqing.mall.mini.model.vo.MiniPmsCategoryListVO;
import com.zhengqing.mall.mini.model.vo.MiniPmsCategoryPageVO;
import com.zhengqing.mall.mini.model.vo.MiniPmsCategoryReSpuPageVO;
import com.zhengqing.mall.service.MiniOmsCategoryService;
import com.zhengqing.mall.service.MiniPmsCategorySpuRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-分类 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Slf4j
@Service
public class MiniOmsCategoryServiceImpl extends OmsCategoryServiceImpl<PmsCategoryMapper, PmsCategory> implements MiniOmsCategoryService {

    @Resource
    private PmsCategoryMapper pmsCategoryMapper;

    @Resource
    private MiniPmsCategorySpuRelationService miniPmsCategorySpuRelationService;

    @Override
    public IPage<MiniPmsCategoryPageVO> page(MiniPmsCategoryPageDTO params) {
        return this.pmsCategoryMapper.selectPageForMini(new Page<>(), params);
    }

    @Override
    public List<MiniPmsCategoryListVO> list(MiniPmsCategoryListDTO params) {
        return this.pmsCategoryMapper.selectListForMini(params);
    }

    @Override
    public IPage<MiniPmsCategoryReSpuPageVO> reSpuPage(MiniPmsCategoryReSpuPageDTO params) {
        IPage<MiniPmsCategoryReSpuPageVO> categoryReSpuPage = this.pmsCategoryMapper.selectReSpuPageForMini(new Page<>(), params);
        List<MiniPmsCategoryReSpuPageVO> categoryReSpuList = categoryReSpuPage.getRecords();
        if (CollectionUtils.isEmpty(categoryReSpuList)) {
            return categoryReSpuPage;
        }
        // 分类ids
        List<String> categoryIdList = categoryReSpuList.stream().map(MiniPmsCategoryReSpuPageVO::getId).collect(Collectors.toList());
        Map<String, List<PmsCategoryReSpuListVO>> categoryReSpuMap = this.miniPmsCategorySpuRelationService.mapByCategoryIdList(categoryIdList);
        categoryReSpuList.forEach(item -> {
            item.setSpuList(categoryReSpuMap.get(item.getId()));
            item.handleData();
        });
        return categoryReSpuPage;
    }

}
