package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.utils.FileItem;

import per.goweii.rxhttp.request.base.BaseBean;

public interface GroupFileActivityView extends BaseView {
    void getGroupFileSuccess(int code, GroupFileBean data);
    void getGroupFileFailed(int code,String msg);
    void deleteGroupFileSuccess(int code, BaseBean data,int position);
    void deleteGroupFileFailed(int code,String msg);
    void getQiniuTokenSuccess(int code, QiniuTokenBean data, FileItem fileItem);
    void getQiniuTokenFailed(int code,String msg);
    void addGroupFileSuccess(int code,BaseBean baseBean);
    void addGroupFileFailed(int code,String msg);
}
