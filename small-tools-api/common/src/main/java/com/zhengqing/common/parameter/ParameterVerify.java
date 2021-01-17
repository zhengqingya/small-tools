package com.zhengqing.common.parameter;

import com.zhengqing.common.exception.ParameterException;

/**
 * <p>
 * 参数校验
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/8/1 18:08
 */
public interface ParameterVerify {

    /**
     * 传入参数验证
     *
     * @throws ParameterException
     */
    void checkParam() throws ParameterException;

}
