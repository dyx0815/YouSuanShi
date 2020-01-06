package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.SearchFriendBean;

public interface SearchFriendByPhoneActivityView extends BaseView {
    void searchFriendSuccess(int code, SearchFriendBean searchFriendBean);
    void searchFriendFailed(int code,String msg);
}
