package com.tensquare.user.exception;

public class RepetitionRegisterException extends Exception {

    private String message;

    public RepetitionRegisterException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
