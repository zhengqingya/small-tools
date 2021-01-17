package com.zhengqing.common.exception;

/**
 * <p>
 * 参数异常
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/8/1 18:07
 */
public class ParameterException extends MyException {

    private static final long serialVersionUID = 1L;

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(String message, Throwable e) {
        super(message, e);
    }

}
