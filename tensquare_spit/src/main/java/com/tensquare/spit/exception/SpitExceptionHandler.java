package com.tensquare.spit.exception;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SpitExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        if (e instanceof ThumbupException) {
            ThumbupException te = (ThumbupException) e;
            return new Result(false, StatusCode.ERROR, te.getMessage());
        }
        return new Result(false, StatusCode.ERROR, "我也不知道发生了什么");
    }
}
