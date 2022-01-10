package com.zhengqing.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.constant.DataSourceConstant;
import com.zhengqing.common.constant.MybatisConstant;
import com.zhengqing.common.util.IdGeneratorUtil;
import com.zhengqing.common.util.MyDateUtil;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.mapper.DemoMapper;
import com.zhengqing.demo.model.dto.DemoListDTO;
import com.zhengqing.demo.model.dto.DemoSaveDTO;
import com.zhengqing.demo.model.vo.DemoListVO;
import com.zhengqing.demo.service.IDemoService;
import com.zhengqing.system.enums.SysUserSexEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 测试demo 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Slf4j
@Service
//@DS(DataSourceConstant.MASTER)
// @Transactional(rollbackFor = Exception.class)
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {

    @Resource
    private DemoMapper demoMapper;

    @Resource
    private IdGeneratorUtil idGeneratorUtil;

    @Override
    public void testDataScope() {
        Page page = new Page<Demo>(1, 2);
        this.demoMapper.selectTestListByDataScope(page, 1L, "Jack").forEach(System.out::println);
        // 观察 sql 变化这个方法没有注解权限
        Demo user = this.demoMapper.selectById(1L);
        user.setUsername("abc");
        this.demoMapper.updateById(user);
        this.demoMapper.insert(Demo.builder().username("admin").password("123456").build());
        this.demoMapper.deleteDataByDataScope(666L);
    }

    // @Transactional(rollbackFor = Exception.class)
    @Transactional
    @Override
    public void testTransactional() {
        Demo demo = Demo.builder().username("admin").password("123456").build();
        demo.insert();
        Long id = demo.getId();
        log.debug("主键id： 【{}】", id);

        log.debug("可能异常启动...");
        this.handleData();
        log.debug("无异常...");
    }

    private void handleData() {
        int a = 1 / 0;
    }

    @Override
    public IPage<DemoListVO> page(DemoListDTO params) {
        IPage<DemoListVO> result = this.demoMapper.selectDataList(new Page<>(), params);
        List<DemoListVO> list = result.getRecords();
        this.handleResultData(list);
        return result;
    }

    @Override
    public List<DemoListVO> list(DemoListDTO params) {
        List<DemoListVO> list = this.demoMapper.selectDataList(params);
        this.handleResultData(list);
        return list;
    }

    /**
     * 处理数据
     *
     * @param list: 数据
     * @return void
     * @author zhengqingya
     * @date 2021/01/13 10:11
     */
    private void handleResultData(List<DemoListVO> list) {

    }

    @Override
    public Long addOrUpdateData(DemoSaveDTO params) {
        Long id = params.getId();
        String username = params.getUsername();
        String password = params.getPassword();
        Integer sex = params.getSex();

        Demo demo = Demo.builder()
                .id(id)
                .username(username)
                .password(password)
                .sexEnum(SysUserSexEnum.getEnum(sex))
                .startTime(params.getStartTime())
                .endTime(params.getEndTime())
                .build();

        // FIXME 临时测试分页
        Demo demoInfo = this.demoMapper
                .selectOne(new LambdaQueryWrapper<Demo>().eq(Demo::getUsername, username)
                        .last(MybatisConstant.LIMIT_ONE));
        log.info("demoInfo ：{}", demoInfo);

        if (id == null) {
            id = this.idGeneratorUtil.snowflakeId();
            demo.setId(id);
            demo.insert();
        } else {
            demo.updateById();
        }
        return id;
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void asyncExecuteTransactional() {
        Demo demo = Demo.builder().username("test-03-async-method-01").password("123456").build();
        demo.insert();
//        int a1 = 1 / 0;

        // 设置回滚点,只回滚以下异常
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        try {
            Demo demo2 = Demo.builder().username("test-03-async-method-02").password("123456")
                    .build();
            demo2.insert();
            int exception = 1 / 0;
        } catch (Exception e) {
            // 手工回滚异常,回滚到savePoint
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            log.error("异步执行任务失败: {}", e.getMessage());

            Demo demo3 = Demo.builder().username("test-03-async-method-03").password("123456")
                    .build();
            demo3.insert();
//            int a2 = 1 / 0;
            return;
        }
    }

    @Override
    public String addBatchData(int addSum) {
        LocalDateTime saveBeforeDateTime = LocalDateTime.now();

        // 第1次插入
        int page = 1;
        // 每次插入数据条数
        int pageSize = 5000;
        // 累计插入数量
        int total = 0;
        // 循环插入数据
        for (int index = 1; index <= addSum; ) {
            total = page * pageSize;
            log.info("page:[{}] pageSize:[{}] total:[{}] index:[{}]", page, pageSize, total, index);
            if (total > addSum) {
                int finalNum = addSum - ((page - 1) * pageSize);
                log.info("最后一页新增数：[{}]", finalNum);
                this.insertData03(finalNum);
            } else {
                this.insertData03(pageSize);
            }
            page += 1;
            index = total + 1;
        }

        LocalDateTime saveAfterDateTime = LocalDateTime.now();
        Duration duration = Duration.between(saveBeforeDateTime, saveAfterDateTime);
        long millis = duration.toMillis();
        String msg = String.format("测试插入%s条数据用时: [%s ms]  [%s s]", addSum, millis, millis / 1000);
        log.info(msg);
        return msg;
    }

    @Override
    @DS(DataSourceConstant.DB_TEST)
    public Demo getDataByDbTest(Integer id) {
        return super.getById(id);
    }

    /**
     * 方式一：for循环中单条插入
     * 测试插入1000条数据用时: [21705 ms]  [21 s]
     */
    private void insertData01(int addSum) {
        for (int i = 0; i < addSum; i++) {
            Demo.builder().username("insertData01 - " + i).password("123456").build().insert();
        }
    }

    /**
     * 方式二：mybatis api 批量插入
     * 测试插入1000条数据用时: [14668 ms]  [14 s]
     */
    private void insertData02(int addSum) {
        List<Demo> demoList = Lists.newLinkedList();
        for (int i = 0; i < addSum; i++) {
            demoList.add(Demo.builder().username("insertData02 - " + i).password("123456").build());
        }
        this.saveBatch(demoList);
    }

    /**
     * 方式三：手写sql 批量插入
     * 测试插入1000条数据用时: [330 ms]  [0 s]
     * 测试插入10000条数据用时: [1636 ms]  [1 s]
     * 测试插入100000条数据用时: [16160 ms]  [16 s]
     * 测试插入1000000条数据用时: [126093 ms]  [126 s]
     * 测试插入3000000条数据用时: [331082 ms]  [331 s]
     */
//    @Async(ThreadPoolConstant.SMALL_TOOLS_THREAD_POOL)
    private void insertData03(int addSum) {
        List<Demo> demoList = Lists.newLinkedList();
        Date now = new Date();
        for (int i = 0; i < addSum; i++) {
            Demo item = new Demo();
            item.setUsername("insertData03 - " + i);
            item.setPassword("123456");
            item.setSexEnum(SysUserSexEnum.getEnum(i % 2));
            item.setStartTime(MyDateUtil.addTime(TimeUnit.MINUTES, -i));
            item.setEndTime(MyDateUtil.addTime(TimeUnit.MINUTES, i));
            item.setRemark("hello:" + i);
            item.setCreateBy(1);
            item.setCreateTime(now);
            item.setUpdateBy(1);
            item.setUpdateTime(now);
            item.setIsDeleted(false);
            demoList.add(item);
        }
        this.demoMapper.insertBatch(demoList);
    }

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 方式四：批处理
     * 测试插入1000条数据用时: [14991 ms]  [14 s]
     */
    private void insertData04(int addSum) {
        List<Demo> demoList = Lists.newLinkedList();
        Date now = new Date();
        for (int i = 0; i < addSum; i++) {
            Demo item = new Demo();
            item.setUsername("insertData04 - " + i);
            item.setPassword("123456");
            item.setCreateBy(1);
            item.setCreateTime(now);
            item.setUpdateBy(1);
            item.setUpdateTime(now);
            item.setIsDeleted(false);
            demoList.add(item);
        }
        SqlSession sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        DemoMapper demoMapperNew = sqlSession.getMapper(DemoMapper.class);
        demoList.stream().forEach(demoMapperNew::insert);
        sqlSession.commit();
        sqlSession.clearCache();
    }

}
