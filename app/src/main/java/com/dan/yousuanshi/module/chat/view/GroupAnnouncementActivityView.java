package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.GroupAnnouncementBean;

import java.util.List;

public interface GroupAnnouncementActivityView extends BaseView {
    void getGroupAnnouncementSuccess(int code, GroupAnnouncementBean data);
    void getGroupAnnouncementFailed(int code,String msg);
    void deleteGroupAnnouncementSuccess(int code, List list,int position);
    void deleteGroupAnnouncementFailed(int code,String msg);
}
