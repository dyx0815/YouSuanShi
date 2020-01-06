package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface AddFriendActivityView extends BaseView {
    void sendAddFriendSuccess(int code, List list);
    void sendAddFriendFailed(int code,String msg);
}
