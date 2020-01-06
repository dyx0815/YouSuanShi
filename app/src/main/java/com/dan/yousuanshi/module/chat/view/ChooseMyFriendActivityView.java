package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;

import java.util.List;

public interface ChooseMyFriendActivityView extends BaseView {
    void getMyFriendSuccess(int code, List<MyFriendBean> data);
    void getMyFriendFailed(int code, String msg);
    void addGroupPeopleSuccess(int code,List list);
    void addGroupPeopleFailed(int code,String msg);
}
