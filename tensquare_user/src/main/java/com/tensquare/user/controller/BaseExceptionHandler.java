package com.tensquare.user.controller;

import com.tensquare.user.exception.RepetitionRegisterException;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e) {
        if (e instanceof RepetitionRegisterException) {
            System.out.println(((RepetitionRegisterException) e).getMessage());
            return new Result(false, StatusCode.ERROR, e.getMessage());
        }
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
