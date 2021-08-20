package com.zhengqing.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.constant.MybatisConstant;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.mapper.DemoMapper;
import com.zhengqing.demo.model.dto.DemoListDTO;
import com.zhengqing.demo.model.dto.DemoSaveDTO;
import com.zhengqing.demo.model.vo.DemoListVO;
import com.zhengqing.demo.service.IDemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
// @Transactional(rollbackFor = Exception.class)
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {

    @Autowired
    private DemoMapper demoMapper;

    // @Transactional(rollbackFor = Exception.class)
    @Transactional
    @Override
    public void testTransactional() {
        Demo demo = Demo.builder().username("admin").password("123456").build();
        demo.insert();
        Integer id = demo.getId();
        log.debug("主键id： 【{}】", id);

        log.debug("可能异常启动...");
        this.handleData();
        log.debug("无异常...");
    }

    private void handleData() {
        int a = 1 / 0;
    }

    @Override
    public IPage<DemoListVO> listPage(DemoListDTO params) {
        IPage<DemoListVO> result = demoMapper.selectDataList(new Page<>(), params);
        List<DemoListVO> list = result.getRecords();
        this.handleResultData(list);
        return result;
    }

    @Override
    public List<DemoListVO> list(DemoListDTO params) {
        List<DemoListVO> list = demoMapper.selectDataList(params);
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
    public Integer addOrUpdateData(DemoSaveDTO params) {
        Integer id = params.getId();
        String username = params.getUsername();
        String password = params.getPassword();
        Integer sex = params.getSex();

        Demo demo = Demo.builder()
                .id(id)
                .username(username)
                .password(password)
                .sex(sex)
                .build();

        // FIXME 临时测试分页
        Demo demoInfo = this.demoMapper
                .selectOne(new LambdaQueryWrapper<Demo>().eq(Demo::getUsername, username)
                        .last(MybatisConstant.LIMIT_ONE));
        log.info("demoInfo ：{}", demoInfo);

        if (id == null) {
            demo.insert();
            id = demo.getId();
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
    public void addBatchData() {
        // 测试插入数据量
        int addSum = 1000000;
        LocalDateTime saveBeforeDateTime = LocalDateTime.now();
        this.insertData03(addSum);
        LocalDateTime saveAfterDateTime = LocalDateTime.now();
        Duration duration = Duration.between(saveBeforeDateTime, saveAfterDateTime);
        log.info("测试插入100w数据用时 :【{} s】", duration.toMillis());
    }

    /**
     * 方式一：for循环中单条插入 1000条数据耗时:8s
     */
    private void insertData01(int addSum) {
        for (int i = 0; i < addSum; i++) {
            Demo.builder().username("insertData01 - " + i).password("123456").build().insert();
        }
    }

    /**
     * 方式二：mybatis api 批量插入 1000条数据耗时:5s
     */
    private void insertData02(int addSum) {
        List<Demo> demoList = Lists.newLinkedList();
        for (int i = 0; i < addSum; i++) {
            demoList.add(Demo.builder().username("insertData02 - " + i).password("123456").build());
        }
        this.saveBatch(demoList);
    }

    /**
     * 方式三：手写sql 批量插入 1000条数据耗时:1s
     */
    private void insertData03(int addSum) {
        List<Demo> demoList = Lists.newLinkedList();
        Date now = new Date();
        for (int i = 0; i < addSum; i++) {
            Demo item = new Demo();
            item.setUsername("insertData03 - " + i);
            item.setPassword("123456");
            item.setCreateBy(1);
            item.setCreateTime(now);
            item.setUpdateBy(1);
            item.setUpdateTime(now);
            item.setIsDeleted(false);
            demoList.add(item);
        }
        demoMapper.insertBatch(demoList);
    }

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 方式四：批处理 1000条数据耗时:5s
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
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        DemoMapper demoMapperNew = sqlSession.getMapper(DemoMapper.class);
        demoList.stream().forEach(demoMapperNew::insert);
        sqlSession.commit();
        sqlSession.clearCache();
    }

}
