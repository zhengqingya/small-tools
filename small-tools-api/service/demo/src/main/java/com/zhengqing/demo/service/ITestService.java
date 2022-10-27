package com.zhengqing.demo.service;


import com.zhengqing.demo.model.vo.UserVO;

/**
 * 测试服务
 *
 * @author zhengqingya
 * @date 2022/08/28
 */
public interface ITestService {

    /**
     * 测试方法
     *
     * @param id  主键id
     * @param sex 性别 {@link com.zhengqing.common.core.enums.UserSexEnum}
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/10/27 20:40
     */
    UserVO test(Long id, Byte sex);


}
