package com.pearl.expensetracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class etBadRequestException extends RuntimeException {

    public etBadRequestException(String message) {
        super(message);
    }
}
