package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.SettingTeamBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamInfoBean;

public interface ApplicationSettingActivityView extends BaseView {
    void getTeamInfoSuccess(int code, TeamInfoBean data);
    void getTeamInfoFailed(int code,String msg);
    void settingTeamSuccess(int code, SettingTeamBean data,int type);
    void settingTeamFailed(int code,String msg);
}
