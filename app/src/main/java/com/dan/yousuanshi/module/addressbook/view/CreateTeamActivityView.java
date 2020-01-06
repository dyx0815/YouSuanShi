package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface CreateTeamActivityView extends BaseView {
    void createTeamSuccess(int code, List data);
    void createTeamFailed(int code, String msg);
}
