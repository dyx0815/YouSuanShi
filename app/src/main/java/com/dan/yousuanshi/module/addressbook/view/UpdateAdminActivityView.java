package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;

public interface UpdateAdminActivityView extends BaseView {
    void getCodeSuccess(int code, SendCodeBean data);
    void getCodeFailed(int code,String msg);
    void checkCodeSuccess(int code, CheckCodeBean data);
    void checkCodeFailed(int code,String msg);
}
