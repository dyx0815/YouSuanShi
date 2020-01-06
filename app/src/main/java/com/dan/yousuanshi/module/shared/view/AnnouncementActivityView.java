package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.shared.bean.AnnouncementLisBean;

import per.goweii.rxhttp.request.base.BaseBean;

public interface AnnouncementActivityView extends BaseView {
    void getAnnouncementSuccess(int code, AnnouncementLisBean data);
    void getAnnouncementFailed(int code,String msg);
    void deleteSharedAnnouncementSuccess(int code, BaseBean data,int position);
    void deleteSharedAnnouncementFailed(int code,String msg);
}
