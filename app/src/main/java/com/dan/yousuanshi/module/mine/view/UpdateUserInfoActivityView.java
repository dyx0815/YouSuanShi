package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface UpdateUserInfoActivityView extends BaseView {
    void updateUserInfoSuccess(int code, List list);
    void updateUserInfoFailed(int code,String msg);
}
