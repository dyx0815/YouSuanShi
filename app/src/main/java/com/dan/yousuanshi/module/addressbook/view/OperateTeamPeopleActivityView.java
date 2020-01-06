package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface OperateTeamPeopleActivityView extends BaseView {
    void deleteTeamPeopleSuccess(int code, List list);
    void deleteTeamPeopleFailed(int code,String msg);
}
