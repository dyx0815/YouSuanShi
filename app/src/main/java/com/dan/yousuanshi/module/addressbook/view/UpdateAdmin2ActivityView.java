package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;

import java.util.List;

public interface UpdateAdmin2ActivityView extends BaseView {
    void getCodeSuccess(int code, SendCodeBean data);
    void getCodeFailed(int code,String msg);
    void getUserByIdSuccess(int code, QueryUserBean queryUserBean);
    void getUserByIdFailed(int code,String msg);
    void updateAdminSuccess(int code, List list);
    void updateAdminFailed(int code,String msg);
}
