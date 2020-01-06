package com.dan.yousuanshi.event;

import com.dan.yousuanshi.module.chat.bean.ChatBean;

public class ReceiveMessageEvent {
    private ChatBean chatBean;

    public ChatBean getChatBean() {
        return chatBean;
    }

    public void setChatBean(ChatBean chatBean) {
        this.chatBean = chatBean;
    }

    public ReceiveMessageEvent(ChatBean chatBean) {
        this.chatBean = chatBean;
    }
}
