package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;

import per.goweii.rxhttp.request.base.BaseBean;

public interface SortModelActivityView extends BaseView {
    void sortModelSuccess(int code, BaseBean data);
    void sortModelFailed(int code,String msg);
}
