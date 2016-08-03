package com.freeeland.demo.exception;

/**
 * Created by freeland on 2016/7/31.
 */

public class RepeatKillException  extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }


}
