package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;

import per.goweii.rxhttp.request.base.BaseBean;

public interface AddModelActivityView extends BaseView {
    void addModelSuccess(int code, BaseBean data);
    void addModelFailed(int code,String msg);
}
