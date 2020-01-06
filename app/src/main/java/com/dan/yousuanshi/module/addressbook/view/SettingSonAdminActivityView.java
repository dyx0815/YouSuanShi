package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.SonAdminBean;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public interface SettingSonAdminActivityView extends BaseView {
    void getSonAdminSuccess(int code, List<SonAdminBean> data);
    void getSonAdminFailed(int code,String msg);
    void removeSonAdminSuccess(int code, BaseBean data);
    void removeSonAdminFailed(int code,String msg);
}
