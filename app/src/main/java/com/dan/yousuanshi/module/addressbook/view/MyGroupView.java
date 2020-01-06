package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.MyGroupBean;
import com.dan.yousuanshi.module.chat.bean.MessageId;

import java.util.List;

public interface MyGroupView extends BaseView {
    void getMyGroupSuccess(int code, List<MyGroupBean> data);
    void getMyGroupFailed(int code, String msg);
    void sendMessageSuccess(int code, MessageId data, long id);
    void sendMessageFailed(int code,String msg);
}
