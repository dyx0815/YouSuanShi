package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;

import java.util.List;

public interface SharedBusinessCardActivityView extends BaseView {
    void getMyTeamSuccess(int code, List<MyTeamBean> data);
    void getMyTeamFailed(int code,String msg);
    void addGroupPeopleSuccess(int code,List list);
    void addGroupPeopleFailed(int code,String msg);
}
