package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;

import java.util.List;

public interface GroupAdminActivityView extends BaseView {
    void removeGroupMasterSuccess(int code, List list, MyFriendBean myFriendBean);
    void removeGroupMasterFailed(int code,String msg);
}
