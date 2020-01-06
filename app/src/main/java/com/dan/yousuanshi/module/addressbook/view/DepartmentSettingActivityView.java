package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentInfoBean;

import java.util.List;

public interface DepartmentSettingActivityView extends BaseView {
    void updateDepartmentSuccess(int code, List list);
    void updateDepartmentFailed(int code,String msg);
    void departmentInfoSuccess(int code, DepartmentInfoBean data);
    void departmentInfoFailed(int code,String msg);
    void deleteDepartmentSuccess(int code,List list);
    void deleteDepartmentFailed(int code,String msg);
}
