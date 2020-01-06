package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface InputGroupAmActivityView extends BaseView {
    void submitGroupAnnouncementSuccess(int code, List list);
    void submitGroupAnnouncementFailed(int code, String msg);
    void updateGroupAnnouncementSuccess(int code,List list);
    void updateGroupAnnouncementFailed(int code,String msg);
}
