package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.SearchPeopleBean;

public interface SearchPeopleActivityView extends BaseView {
    void searchPeopleSuccess(int code, SearchPeopleBean searchPeopleBean);
    void searchPeopleFailed(int code ,String msg);
}
