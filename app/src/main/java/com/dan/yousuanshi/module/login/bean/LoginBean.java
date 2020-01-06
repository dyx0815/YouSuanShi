package com.dan.yousuanshi.module.login.bean;

import per.goweii.rxhttp.request.base.BaseBean;

public class LoginBean extends BaseBean {
    private String token;
    private int isshow;

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
