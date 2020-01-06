package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.login.bean.UserBean;

import java.util.List;

public interface MyInfoActivityView extends BaseView {
    void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean);
    void getQiniuTokenFailed(int code, String message);
    void updateUserInfoSuccess(int code, List list);
    void updateUserInfoFailed(int code,String msg);
    void getUserSuccess(int code, UserBean userBean);
    void getUserFailed(int code, String msg);
}
