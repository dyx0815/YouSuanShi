package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.CreateGroupBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;

public interface SetGroupInfoActivityView extends BaseView {
    void createGroupSuccess(int code, CreateGroupBean data);
    void createGroupFailed(int code,String msg);
    void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean);
    void getQiniuTokenFailed(int code, String message);
}
