package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.SearchTeamBean;

public interface SearchTeamActivityView extends BaseView {
    void searchTeamSuccess(int code, SearchTeamBean searchTeamBean);
    void searchTeamFailed(int code,String msg);
}
