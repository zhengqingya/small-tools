package com.zhengqing.auth.exception;

import com.zhengqing.common.base.enums.ApiResultCodeEnum;
import com.zhengqing.common.base.model.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p> 认证异常处理 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/13 8:53 PM
 */
@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * 用户不存在
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiResult handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ApiResult.fail(ApiResultCodeEnum.USER_NOT_EXIST.getDesc());
    }

    /**
     * 用户名和密码异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidGrantException.class)
    public ApiResult handleInvalidGrantException(InvalidGrantException e) {
        return ApiResult.fail(ApiResultCodeEnum.USER_UNAUTHORIZED.getDesc());
    }

    /**
     * 账户异常(禁用、锁定、过期)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ApiResult handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return ApiResult.fail(e.getMessage());
    }

    /**
     * token 无效或已过期
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidTokenException.class})
    public ApiResult handleInvalidTokenExceptionException(InvalidTokenException e) {
        return ApiResult.fail(e.getMessage());
    }

}
