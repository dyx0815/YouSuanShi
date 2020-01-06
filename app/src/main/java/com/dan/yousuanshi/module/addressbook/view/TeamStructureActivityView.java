package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;

import java.util.List;

public interface TeamStructureActivityView extends BaseView {
    void getDepartmentSuccess(int code, List<DepartmentBean> data);
    void getDepartmentFailed(int code,String msg);
    void exitTeamSuccess(int code,List data);
    void exitTeamFailed(int code,String msg);
}
