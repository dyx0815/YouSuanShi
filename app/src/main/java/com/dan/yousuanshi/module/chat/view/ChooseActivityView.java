package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.MessageId;

public interface ChooseActivityView extends BaseView {
    void sendMessageSuccess(int code, MessageId data, long id);
    void sendMessageFailed(int code,String msg);
}
