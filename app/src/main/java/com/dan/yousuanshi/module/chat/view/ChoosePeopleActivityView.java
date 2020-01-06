package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface ChoosePeopleActivityView extends BaseView {
    void removeGroupPeopleSuccess(int code, List list);
    void removeGroupPeopleFailed(int code,String msg);
    void addGroupMasterSuccess(int code,List list);
    void addGroupMasterFailed(int code,String msg);
}
