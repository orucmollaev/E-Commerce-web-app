package com.ecommerce.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String msg) {
        super(msg);
    }
}