package com.zhengqing.demo.service.impl;

import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.demo.model.vo.UserVO;
import com.zhengqing.demo.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p> 测试 service </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/10/27 20:40
 */
@Slf4j
@Service
public class ITestServiceImpl implements ITestService {


    @Override
    public UserVO test(Long id, Byte sex) {
        // 1、处理业务1
        this.test1();

        // 2、处理业务2
        this.test2();

        // 3、返回数据
        return UserVO.builder()
                .id(1L)
                .nickname("zhengqingya")
                .sex(UserSexEnum.男.getType())
                .build();
    }


    /**
     * 测试方法1
     *
     * @return void
     * @author zhengqingya
     * @date 2022/10/27 20:40
     */
    private void test1() {
        System.out.println(666);
    }

    /**
     * 测试方法2
     *
     * @return void
     * @author zhengqingya
     * @date 2022/10/27 20:40
     */
    private void test2() {
        System.out.println(666);
    }

}
