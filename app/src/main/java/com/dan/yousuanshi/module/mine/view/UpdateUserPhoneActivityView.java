package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;

public interface UpdateUserPhoneActivityView extends BaseView {
    void sendCodeSuccess(int code, SendCodeBean sendCodeBean);
    void sendCodeFailed(int code,String message);
}
