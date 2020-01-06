package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface SettingApplicationActivityView extends BaseView {
    void settingApplicationSuccess(int code, List list);
    void settingApplicationFailed(int code,String msg);
}
