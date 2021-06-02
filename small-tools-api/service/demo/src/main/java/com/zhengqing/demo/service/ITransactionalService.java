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

}
