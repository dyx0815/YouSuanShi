package com.dan.yousuanshi.event;

public class AddMessageCountEvent {
    private int messageCount = 0;

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public AddMessageCountEvent(){

    }
    public AddMessageCountEvent(int messageCount) {
        this.messageCount = messageCount;
    }
}
