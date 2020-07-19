package com.pearl.expensetracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class etResourceNotFoundException extends RuntimeException{

    public etResourceNotFoundException(String message) {
        super(message);
    }
}
