package com.dan.yousuanshi.module.chat.bean;

public class EmojiBean {
    private String text;
    private Integer resId;

    public EmojiBean(String text, Integer resId) {
        this.text = text;
        this.resId = resId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }
}
