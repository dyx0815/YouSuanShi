package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.PartPurviewBean;

import per.goweii.rxhttp.request.base.BaseBean;

public interface PartPurviewActivityView extends BaseView {
    void getPartPurviewSuccess(int code, PartPurviewBean data);
    void getPartPurviewFailed(int code,String msg);
    void addSonAdminSuccess(int code, BaseBean data);
    void addSonAdminFailed(int code,String msg);
}
