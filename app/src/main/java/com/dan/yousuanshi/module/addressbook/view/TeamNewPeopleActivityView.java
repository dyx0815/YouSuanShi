package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.TeamNewApplyBean;

import java.util.List;

public interface TeamNewPeopleActivityView extends BaseView {
    void getNewApplySuccess(int code, TeamNewApplyBean data);
    void getNewApplyFailed(int code,String msg);
    void agreeApplySuccess(int code, List list,int position);
    void agreeApplyFailed(int code,String msg);
    void clearApplySuccess(int code,List list);
    void clearApplyFailed(int code,String msg);
}
