package com.tensquare.spit.exception;

public class ThumbupException extends Exception {
    private String msg;

    public ThumbupException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
