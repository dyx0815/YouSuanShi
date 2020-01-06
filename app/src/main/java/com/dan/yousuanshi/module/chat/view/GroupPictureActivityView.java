package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;

import per.goweii.rxhttp.request.base.BaseBean;

public interface GroupPictureActivityView extends BaseView {
    void getGroupPictureSuccess(int code, GroupFileBean data);
    void getGroupPictureFailed(int code,String msg);
    void deleteGroupFileSuccess(int code, BaseBean data);
    void deleteGroupFileFailed(int code,String msg);
}
