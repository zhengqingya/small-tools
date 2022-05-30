package com.zhengqing.demo.service;

public interface ITransactionalService {

    /**
     * 自动回滚
     *
     * @return void
     * @author zhengqingya
     * @date 2021/6/2 10:18 下午
     */
    void testTransactional01();

    /**
     * 手动回滚（进行try/catch，回滚并抛出）
     *
     * @return void
     * @author zhengqingya
     * @date 2021/6/2 10:18 下午
     */
    void testTransactional02();

    /**
     * 回滚部分异常
     *
     * @return void
     * @author zhengqingya
     * @date 2021/6/2 10:19 下午
     */
    void testTransactional03();

    /**
     * 无事务A方法 调用 有事务B方法 事务生效测试 -> 触发aop
     *
     * @return void
     * @author zhengqingya
     * @date 2021/6/2 10:19 下午
     */
    void testTransactional04();

    /**
     * 事务A方法( @Transactional )        调用          异步B方法( @Async 和 @Transactional )
     * B方法事务生效测试
     *
     * @return void
     * @author zhengqingya
     * @date 2022/5/30 15:02
     */
    void testTransactional05();

}
