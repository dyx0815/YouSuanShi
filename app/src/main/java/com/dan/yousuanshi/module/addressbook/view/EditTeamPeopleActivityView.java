package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleInfo;

import java.util.List;

public interface EditTeamPeopleActivityView extends BaseView {
    void getTeamPeopleInfoSuccess(int code, TeamPeopleInfo data);
    void getTeamPeopleInfoFailed(int code,String msg);
    void deleteTeamPeopleSuccess(int code, List list);
    void deleteTeamPeopleFailed(int code,String msg);
    void editTeamPeopleSuccess(int code,List list);
    void editTeamPeopleFailed(int code,String msg);
}
