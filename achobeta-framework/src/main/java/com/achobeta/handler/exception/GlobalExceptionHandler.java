package com.achobeta.handler.exception;

import com.achobeta.domain.ResponseResult;
import com.achobeta.enums.AppHttpCodeEnum;
import com.achobeta.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常:" + e);
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常: " + e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode() ,e.getMessage());
    }
}
