package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface AddDepartmentActivityView extends BaseView {
    void addDepartmentSuccess(int code, List list);
    void addDepartmentFailed(int code,String msg);
}
