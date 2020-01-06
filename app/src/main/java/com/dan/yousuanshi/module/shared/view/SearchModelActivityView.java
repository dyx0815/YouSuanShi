package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;

import per.goweii.rxhttp.request.base.BaseBean;

public interface SearchModelActivityView extends BaseView {
    void setCompanyModelSuccess(int code, BaseBean data);
    void setCompanyModelFailed(int code,String msg);
    void onLineModelSuccess(int code,BaseBean data);
    void onLineModelFailed(int code,String msg);
}
