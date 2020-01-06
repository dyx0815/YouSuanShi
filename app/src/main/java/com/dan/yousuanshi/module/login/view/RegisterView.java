package com.dan.yousuanshi.module.login.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.login.bean.UserBean;

import java.util.List;

public interface RegisterView extends BaseView {
    void sendCodeSuccess(int code, SendCodeBean sendCodeBean);
    void sendCodeFailed(int code,String message);
    void registerSuccess(int code, LoginBean loginBean);
    void registerFailed(int code, String message);
    void checkCodeSuccess(int code, CheckCodeBean checkCodeBean);
    void checkCodeFailed(int code, String message);
    void getUserInfoSuccess(int code, UserBean userBean);
    void getUserInfoFailed(int code, String message);
    void forgetPwdSuccess(int code,LoginBean loginBean);
    void forgetPwdFailed(int code, String message);
}
