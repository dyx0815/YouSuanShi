package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.DiyApplicationSettingBean;

import java.util.List;

public interface DiyApplicationSettingActivityView extends BaseView {
    void getDiyApplicationSettingSuccess(int code, List<DiyApplicationSettingBean> data);
    void getDiyApplicationSettingFailed(int code,String msg);
}
