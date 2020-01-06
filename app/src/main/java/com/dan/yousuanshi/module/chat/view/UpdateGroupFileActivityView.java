package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;

import per.goweii.rxhttp.request.base.BaseBean;

public interface UpdateGroupFileActivityView extends BaseView {
    void renameGroupFileSuccess(int code, BaseBean data);
    void renameGroupFileFailed(int code,String msg);
}
