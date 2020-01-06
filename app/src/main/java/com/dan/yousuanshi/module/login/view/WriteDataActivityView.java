package com.dan.yousuanshi.module.login.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;

import java.util.List;

public interface WriteDataActivityView extends BaseView {
    void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean);
    void getQiniuTokenFailed(int code, String message);
    void writeDataSuccess(int code, List list);
    void writeDataFailed(int code,String msg);
}
