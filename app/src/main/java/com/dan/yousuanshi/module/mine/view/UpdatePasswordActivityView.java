package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface UpdatePasswordActivityView extends BaseView {
    void setPasswordSuccess(int code, List list);
    void setPasswordFailed(int code, String msg);
}
