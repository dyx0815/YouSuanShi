package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;

import java.util.List;

public interface ChatActivityView extends BaseView {
    void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean, ChatBean chatBean,long msgId,int position);
    void getQiniuTokenFailed(int code, String message,long id,int position);
    void sendMessageSuccess(int code, MessageId data, long id,int position);
    void sendMessageFailed(int code,String msg,long id,int position);
    void addCollectSuccess(int code, List list);
    void addCollectFailed(int code,String msg);
}
