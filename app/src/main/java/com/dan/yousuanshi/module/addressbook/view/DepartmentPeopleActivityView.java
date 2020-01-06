package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;

import java.util.List;

public interface DepartmentPeopleActivityView extends BaseView {
    void getDepartmentPeopleSuccess(int code, List<DepartmentPeopleBean> data);
    void getDepartmentPeopleFailed(int code,String msg);
    void addPeopleToDepartmentSuccess(int code,List list);
    void addPeopleToDepartmentFailed(int code,String msg);
}
