package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;

import per.goweii.rxhttp.request.base.BaseBean;

public interface UpdateModelNameActivityView extends BaseView {
    void updateModelNameSuccess(int code, BaseBean data);
    void updateModelNameFailed(int code,String msg);
}
