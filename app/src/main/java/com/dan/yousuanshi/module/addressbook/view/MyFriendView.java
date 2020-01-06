package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.chat.bean.MessageId;

import java.util.List;

public interface MyFriendView extends BaseView {
    void getMyFriendSuccess(int code, List<MyFriendBean> data);
    void getMyFriendFailed(int code, String msg);
    void sendMessageSuccess(int code, MessageId data, long id);
    void sendMessageFailed(int code,String msg);
}
