package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface UpdateRemarkActivityView extends BaseView {
    void updateRemarkSuccess(int code, List data);
    void updateRemarkFailed(int code,String msg);
}
