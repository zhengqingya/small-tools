package com.zhengqing.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.demo.config.MybatisPlusDataScopeConfigDemo;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.model.dto.DemoListDTO;
import com.zhengqing.demo.model.vo.DemoListVO;
import mybatis.mate.annotation.DataColumn;
import mybatis.mate.annotation.DataScope;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 测试demo Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

    /**
     * 测试 test 类型数据权限范围，混合分页模式 {@link com.zhengqing.demo.config.MybatisPlusDataScopeConfigDemo}
     * <p>
     * 最终执行SQL语句： SELECT d.* FROM t_demo d WHERE (d.id IN ('1', '2', '3', '5')) AND d.username LIKE '%1533%'
     *
     * @param page     分页参数
     * @param id       id
     * @param username 用户名
     * @return 结果
     * @author zhengqingya
     * @date 2022/1/10 15:29
     */
    @DataScope(type = MybatisPlusDataScopeConfigDemo.TEST, value = {
            // 关联表 t_demo 别名 d 指定id字段权限
            @DataColumn(alias = "d", name = "id"),
            // 关联表 t_demo 别名 d 指定用户名字段权限（自己判断处理）
            @DataColumn(alias = "d", name = "username")
    })
    @Select("select d.* from t_demo d")
    List<Demo> selectTestListByDataScope(IPage<Demo> page, Long id, @Param("username") String username);

    /**
     * 删除数据
     *
     * @param id 主键id
     * @return void
     * @author zhengqingya
     * @date 2022/1/10 15:48
     */
    @DataScope(type = MybatisPlusDataScopeConfigDemo.TEST_CLASS, value = {
            @DataColumn(name = "id")
    })
    @Delete("DELETE FROM t_demo WHERE id = #{id}")
//    @Update("UPDATE t_demo SET is_deleted = 1 WHERE id = #{id}")
    void deleteDataByDataScope(@Param("id") Long id);

    /**
     * 自定义SQL：默认也会增加多租户条件
     * 参考打印的SQL
     *
     * @return 数据量
     * @author zhengqingya
     * @date 2022/1/10 16:03
     */
    @Select("select count(1) from t_demo")
    Long myCount();

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/01/13 10:11
     */
    IPage<DemoListVO> selectDataList(IPage page, @Param("filter") DemoListDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/01/13 10:11
     */
    List<DemoListVO> selectDataList(@Param("filter") DemoListDTO filter);

    /**
     * 批量插入数据
     *
     * @param demoList list
     * @return void
     * @author zhengqingya
     * @date 2021/5/28 14:28
     */
    void insertBatch(@Param("demoList") List<Demo> demoList);

}
