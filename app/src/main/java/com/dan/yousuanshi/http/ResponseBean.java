package com.dan.yousuanshi.http;



import per.goweii.rxhttp.request.base.BaseResponse;

public class ResponseBean<E> implements BaseResponse<E> {

    private int error;
    private E data;
    private String message;

    @Override
    public int getCode() {
        return error;
    }

    @Override
    public void setCode(int code) {
        this.error = code;
    }

    @Override
    public E getData() {
        return data;
    }

    @Override
    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public void setMsg(String msg) {
        this.message = msg;
    }
}
