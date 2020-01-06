package com.dan.yousuanshi.module.chat.bean;

public class ChatResponseBean {
    private int error;
    private SendMessageBean data;
    private String message;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public SendMessageBean getSendMessageBean() {
        return data;
    }

    public void setSendMessageBean(SendMessageBean sendMessageBean) {
        this.data = sendMessageBean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
