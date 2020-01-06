package com.dan.yousuanshi.module.main.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.login.bean.LoginBean;

import java.util.List;

public interface MainView extends BaseView {
    void getNewTokenSuccess(int code, LoginBean loginBean);
    void getNewTokenFailed(int code, String message);
    void socketSuccess(int code, List list);
    void socketFaied(int code,String msg);
}
