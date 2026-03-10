package com.doctor.exception;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


public class DuplicateResourceException extends Exception{
    public DuplicateResourceException(String message) {
        super(message);
    }
}
