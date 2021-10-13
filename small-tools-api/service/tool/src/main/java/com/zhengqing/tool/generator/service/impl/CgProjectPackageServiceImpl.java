package com.zhengqing.tool.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.exception.MyException;
import com.zhengqing.tool.generator.entity.CgProjectPackage;
import com.zhengqing.tool.generator.mapper.CgProjectPackageMapper;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageTreeDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectPackageListVO;
import com.zhengqing.tool.generator.model.vo.CgProjectPackageTreeVO;
import com.zhengqing.tool.generator.service.ICgProjectPackageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成器 - 项目关联包管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @date 2019-09-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CgProjectPackageServiceImpl extends ServiceImpl<CgProjectPackageMapper, CgProjectPackage>
        implements ICgProjectPackageService {

    @Autowired
    private CgProjectPackageMapper cgProjectPackageMapper;

    @Override
    public List<CgProjectPackageListVO> list(CgProjectPackageListDTO params) {
        return cgProjectPackageMapper.selectProjectPackages(params);
    }

    @Override
    public List<CgProjectPackageTreeVO> tree(CgProjectPackageTreeDTO params) {
        Integer projectId = params.getProjectId();
        Integer currentUserId = params.getCurrentUserId();

        CgProjectPackageListDTO cgProjectPackageListDTO = new CgProjectPackageListDTO();
        cgProjectPackageListDTO.setProjectId(projectId);
        cgProjectPackageListDTO.setCurrentUserId(currentUserId);

        // ①、拿到所有包
        List<CgProjectPackageListVO> packageList = this.list(cgProjectPackageListDTO);
        if (CollectionUtils.isEmpty(packageList)) {
            return Lists.newArrayList();
        }
        List<CgProjectPackageTreeVO> allPackage = Lists.newArrayList();
        packageList.forEach(e -> {
            CgProjectPackageTreeVO item = new CgProjectPackageTreeVO();
            item.setId(e.getId());
            item.setName(e.getName());
            item.setParentId(e.getParentId());
            item.setProjectId(projectId);
            allPackage.add(item);
        });

        // ②、准备一个空的父包集合
        List<CgProjectPackageTreeVO> parentPackageList = Lists.newArrayList();
        // ③、遍历子包 -> 进行对父包的设置
        for (CgProjectPackageTreeVO parent : allPackage) {
            Integer parentId = parent.getParentId();
            if (parentId.equals(AppConstant.PROJECT_RE_PACKAGE_PARENT_ID)) {
                CgProjectPackageTreeVO item = new CgProjectPackageTreeVO();
                item.setId(parent.getId());
                item.setName(parent.getName());
                item.setParentId(parentId);
                item.setProjectId(projectId);
                parentPackageList.add(item);
            }
        }
        // ④、遍历出父包对应的子包
        for (CgProjectPackageTreeVO parent : parentPackageList) {
            List<CgProjectPackageTreeVO> child = getChild(parent.getId(), parent.getName(), allPackage);
            parent.setChildren(child);
        }
        return parentPackageList;
    }

    @Override
    public Map<String, String> packageNameInfoMap(Integer projectId) {
        Map<String, String> packageNameInfoMap = Maps.newHashMap();
        List<CgProjectPackageTreeVO> treeData = this.tree(CgProjectPackageTreeDTO.builder().projectId(projectId).build());
        this.recursionTree(packageNameInfoMap, treeData, "");
        return packageNameInfoMap;
    }

    @Override
    public String getParentPackageName(Integer projectId) {
        return cgProjectPackageMapper.selectParentPackageName(projectId);
    }

    private void recursionTree(Map<String, String> packageNameInfoMap, List<CgProjectPackageTreeVO> treeData, String parentPackageName) {
        if (!CollectionUtils.isEmpty(treeData)) {
            treeData.forEach(e -> {
                String packageNameNew = (StringUtils.isBlank(parentPackageName) ? "" : parentPackageName + ".") + e.getName();
                packageNameInfoMap.put(String.valueOf(e.getId()), packageNameNew);
                List<CgProjectPackageTreeVO> children = e.getChildren();
                this.recursionTree(packageNameInfoMap, children, packageNameNew);
            });
        }
    }

    @Override
    public Integer addOrUpdateData(CgProjectPackageSaveDTO params) {
        Integer id = params.getId();
        String name = params.getName();
        Integer parentId = params.getParentId();
        Integer projectId = params.getProjectId();
        Integer currentUserId = params.getCurrentUserId();

        CgProjectPackage cgProjectPackage = new CgProjectPackage();
        cgProjectPackage.setId(id);
        cgProjectPackage.setName(name.trim());
        cgProjectPackage.setParentId(parentId);
        cgProjectPackage.setProjectId(projectId);
        cgProjectPackage.setDataReUserId(currentUserId);

        if (id != null) {
            cgProjectPackage.updateById();
        } else {
            cgProjectPackage.insert();
        }
        return cgProjectPackage.getProjectId();
    }

    @Override
    public void deleteData(Integer id) {
        List<CgProjectPackage> cgProjectPackageList = cgProjectPackageMapper
                .selectList(new LambdaQueryWrapper<CgProjectPackage>().eq(CgProjectPackage::getParentId, id));
        if (!CollectionUtils.isEmpty(cgProjectPackageList)) {
            throw new MyException("该包下存在子包，请先删除子包！");
        }
        CgProjectPackage cgProjectPackage = cgProjectPackageMapper.selectById(id);
        if (cgProjectPackage.getParentId().equals(AppConstant.PROJECT_RE_PACKAGE_PARENT_ID)) {
            throw new MyException("Sorry，您没有权限删除顶级父包！");
        }
        this.removeById(id);
    }

    @Override
    public void deleteDataByProjectId(Integer projectId) {
        cgProjectPackageMapper
                .delete(new LambdaQueryWrapper<CgProjectPackage>().eq(CgProjectPackage::getProjectId, projectId));
    }

    /**
     * 递归树子包数据
     *
     * @param id:                包id
     * @param parentPackageName: 包名
     * @param allPackage:        包数据
     * @return 子包数据
     * @author zhengqingya
     * @date 2020/11/15 12:45
     */
    private List<CgProjectPackageTreeVO> getChild(Integer id, String parentPackageName,
                                                  List<CgProjectPackageTreeVO> allPackage) {
        // ⑤、存放子包的集合
        List<CgProjectPackageTreeVO> listChild = Lists.newArrayList();
        for (CgProjectPackageTreeVO e : allPackage) {
            Integer parentId = e.getParentId();
            if (parentId != null && parentId.equals(id)) {
                e.setParentPackageName(parentPackageName);
                listChild.add(e);
            }
        }
        // ⑥、递归
        for (CgProjectPackageTreeVO e : listChild) {
            e.setChildren(getChild(e.getId(), e.getName(), allPackage));
        }
        if (listChild.size() == 0) {
            return Lists.newArrayList();
        }
        return listChild;
    }
}
