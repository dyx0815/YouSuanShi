package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface UpdateTeamNameActivityView extends BaseView {
    void updateTeamInfoSuccess(int code, List list);
    void updateTeamInfoFailed(int code,String msg);
}
