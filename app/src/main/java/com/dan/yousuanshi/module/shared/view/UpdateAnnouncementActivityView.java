package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;

import per.goweii.rxhttp.request.base.BaseBean;

public interface UpdateAnnouncementActivityView extends BaseView {
    void addAnnouncementSuccess(int code, BaseBean data);
    void addAnnouncementFailed(int code,String msg);
    void updateAnnouncementSuccess(int code, BaseBean data);
    void updateAnnouncementFailed(int code,String msg);
}
