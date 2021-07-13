package com.zhengqing.tool.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.generator.entity.CgProjectPackage;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageTreeDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectPackageListVO;
import com.zhengqing.tool.generator.model.vo.CgProjectPackageTreeVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成器 - 项目关联包管理 服务类
 * </p>
 *
 * @author zhengqingya
 * @date 2019-09-09
 */
public interface ICgProjectPackageService extends IService<CgProjectPackage> {

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return 列表数据
     * @author zhengqingya
     * @date 2020/11/15 11:52
     */
    List<CgProjectPackageListVO> list(CgProjectPackageListDTO params);

    /**
     * 项目包架构树
     *
     * @param params: 查询参数
     * @return 树
     * @author zhengqingya
     * @date 2020/11/15 12:17
     */
    List<CgProjectPackageTreeVO> tree(CgProjectPackageTreeDTO params);

    /**
     * 根据项目id获取其下所有的 包id -> 包名
     *
     * @param projectId: 项目id
     * @return map: 包id -> 包名
     * @author zhengqingya
     * @date 2020/11/15 17:13
     */
    Map<Integer, String> packageNameInfoMap(Integer projectId);

    /**
     * 获取项目父包名
     *
     * @param projectId: 项目id
     * @return 父包名
     * @author zhengqingya
     * @date 2020/11/15 19:04
     */
    String getParentPackageName(Integer projectId);

    /**
     * 新增或更新
     *
     * @param params: 提交参数
     * @return 主键id
     * @author zhengqingya
     * @date 2020/11/15 12:44
     */
    Integer addOrUpdateData(CgProjectPackageSaveDTO params);

    /**
     * 删除数据
     *
     * @param id: 主键id
     * @return void
     * @author zhengqingya
     * @date 2020/11/15 12:54
     */
    void deleteData(Integer id);

    /**
     * 根据项目id删除数据
     *
     * @param projectId: 项目id
     * @return void
     * @author zhengqingya
     * @date 2020/11/15 13:43
     */
    void deleteDataByProjectId(Integer projectId);

}
