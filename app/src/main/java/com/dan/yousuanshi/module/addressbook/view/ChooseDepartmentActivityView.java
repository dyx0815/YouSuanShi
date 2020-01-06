package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;

import java.util.List;

public interface ChooseDepartmentActivityView extends BaseView {
    void getDepartmentPeopleSuccess(int code, List<DepartmentPeopleBean> data);
    void getDepartmentPeopleFailed(int code,String msg);
}
