package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;

import java.util.List;

public interface UpdateUserCodeActivityView extends BaseView {
    void sendCodeSuccess(int code, SendCodeBean sendCodeBean);
    void sendCodeFailed(int code,String message);
    void updatePhoneCheckCodeSuccess(int code, CheckCodeBean checkCodeBean);
    void updatePhoneCheckCodeFailed(int code,String msg);
    void updatePhoneCheckCodeSuccess2(int code, List data);
    void updatePhoneCheckCodeFailed2(int code,String msg);
}
