package com.dan.yousuanshi.event;

import com.dan.yousuanshi.module.chat.bean.ChatBean;

public class SendMessageEvent {
    private ChatBean chatBean;

    public ChatBean getChatBean() {
        return chatBean;
    }

    public void setChatBean(ChatBean chatBean) {
        this.chatBean = chatBean;
    }

    public SendMessageEvent(ChatBean chatBean) {
        this.chatBean = chatBean;
    }
}
