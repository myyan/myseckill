package com.freeeland.demo.dto;

/**
 * Created by freeland on 2016/7/31.
 */
//封装所有 ajax 请求的返回类型
public class SeckillResult<T> {

    private boolean isSuccess;

    private T data;

    private String error;

    public SeckillResult(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public SeckillResult(boolean isSuccess, String error) {
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
