package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;

public interface UpdateUserPhoneNumberActivityView extends BaseView {
    void sendCode2Success(int code, SendCodeBean sendCodeBean);
    void sendCode2Failed(int code,String msg);
}
