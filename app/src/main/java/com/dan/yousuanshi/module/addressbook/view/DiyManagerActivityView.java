package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;

import java.util.List;

public interface DiyManagerActivityView extends BaseView {
    void updateTeamInfoSuccess(int code, List list);
    void updateTeamInfoFailed(int code,String msg);
    void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean);
    void getQiniuTokenFailed(int code, String message);
}
