package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.NewFriendBean;

import java.util.List;

public interface NewFriendView extends BaseView {
    void getNewFriendSuccess(int code, NewFriendBean data);
    void getNewFriendFailed(int code, String msg);
    void agreeFriendSuccess(int code, List data);
    void agreeFriendFailed(int code, String msg);
    void clearNewFriendSuccess(int code, List data, int[] ids);
    void clearNewFriendFailed(int code,String msg);
}
