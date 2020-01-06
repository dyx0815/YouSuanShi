package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface ApplyInfoActivityView extends BaseView {
    void agreeApplySuccess(int code, List list);
    void agreeApplyFailed(int code,String msg);
}
