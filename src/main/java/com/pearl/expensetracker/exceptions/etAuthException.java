package com.pearl.expensetracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class etAuthException extends RuntimeException {

    public etAuthException(String message) {
        super(message);
    }

}
