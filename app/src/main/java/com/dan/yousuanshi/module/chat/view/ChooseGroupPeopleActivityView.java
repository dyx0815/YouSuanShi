package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface ChooseGroupPeopleActivityView extends BaseView {
    void addGroupPeopleSuccess(int code, List list);
    void addGroupPeopleFailed(int code,String msg);
}
