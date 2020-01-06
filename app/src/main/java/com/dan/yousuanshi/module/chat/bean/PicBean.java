package com.dan.yousuanshi.module.chat.bean;

import per.goweii.rxhttp.request.base.BaseBean;

public class PicBean extends BaseBean {
    private int width;
    private int height;

    public PicBean(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
