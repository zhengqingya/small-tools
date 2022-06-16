package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.mall.common.model.bo.PmsSkuBO;
import com.zhengqing.mall.common.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.entity.PmsSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-商品规格 Mapper 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    /**
     * 列表
     *
     * @param spuIdList 商品ids
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 18:22
     */
    List<PmsSkuBO> selectDataListBySpuIdList(@Param("spuIdList") List<String> spuIdList);

    /**
     * 更新商品sku库存
     *
     * @param list 商品sku
     * @return 更新条数
     * @author zhengqingya
     * @date 2021/6/28 15:44
     */
    int updateSkuStock(@Param("list") List<PmsSkuStockBO> list);

}
