package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;

import java.util.List;

public interface TeamPeopleManagerActivityView extends BaseView {
    void getTeamPeopleSuccess(int code, List<TeamPeopleBean> data);
    void getTeamPeopleFailed(int code,String msg);
    void addTeamPeopleSuccess(int code,List list);
    void addTeamPeopleFailed(int code,String msg);
}
