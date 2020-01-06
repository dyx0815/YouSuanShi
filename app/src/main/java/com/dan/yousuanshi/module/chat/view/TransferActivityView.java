package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface TransferActivityView extends BaseView {
    void transferGroupSuccess(int code, List list);
    void transferGroupFailed(int code,String msg);
    void exitGroupSuccess(int code,List list);
    void exitGroupFailed(int code,String msg);
}
