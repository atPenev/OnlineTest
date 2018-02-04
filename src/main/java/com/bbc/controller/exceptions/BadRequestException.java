package com.bbc.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 4463460203220759890L;
    private String message;

    public BadRequestException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}