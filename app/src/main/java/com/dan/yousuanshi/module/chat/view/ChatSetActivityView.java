package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface ChatSetActivityView extends BaseView {
    void deleteFriendSuccess(int code, List list);
    void deleteFriendFailed(int code,String msg);
}
