package com.freeeland.demo.exception;

/**
 * Created by freeland on 2016/7/31.
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
