package com.dan.yousuanshi.module.login.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.login.bean.UserBean;

import java.util.List;

public interface LoginView extends BaseView {
    void loginSuccess(int code, LoginBean loginBean);
    void loginFailed(int code, String message);
    void getUserSuccess(int code, UserBean userBean);
    void getUserFailed(int code,String message);
    void sendCodeSuccess(int code, SendCodeBean sendCodeBean);
    void sendCodeFailed(int code,String message);
    void chattableSuccess(int code, List list);
    void chattableFailed(int code,String message);
}
