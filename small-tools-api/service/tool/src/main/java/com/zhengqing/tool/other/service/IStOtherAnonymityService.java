package com.zhengqing.tool.other.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.other.entity.StOtherAnonymity;
import com.zhengqing.tool.other.model.dto.StOtherAnonymityHandleDTO;
import com.zhengqing.tool.other.model.dto.StOtherAnonymityListDTO;
import com.zhengqing.tool.other.model.dto.StOtherAnonymitySaveDTO;
import com.zhengqing.tool.other.model.vo.StOtherAnonymityListVO;

import java.util.List;

/**
 * <p>
 * 小工具 - 其它 - 匿名事件表 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-10-25 13:27:16
 */
public interface IStOtherAnonymityService extends IService<StOtherAnonymity> {

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return: 查询结果
     * @author zhengqingya
     * @date 2020-10-25 13:27:16
     */
    IPage<StOtherAnonymityListVO> listPage(StOtherAnonymityListDTO params);

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return: 查询结果
     * @author zhengqingya
     * @date 2020-10-25 13:27:16
     */
    List<StOtherAnonymityListVO> list(StOtherAnonymityListDTO params);

    /**
     * 新增或更新
     *
     * @param params: 保存参数
     * @return: 主键id
     * @author zhengqingya
     * @date 2020-10-25 13:27:16
     */
    Integer addOrUpdateData(StOtherAnonymitySaveDTO params);

    /**
     * 匿名处理
     *
     * @param params: 处理提交参数
     * @return: void
     * @author zhengqingya
     * @date 2020/10/25 13:56
     */
    void handle(StOtherAnonymityHandleDTO params);

}
