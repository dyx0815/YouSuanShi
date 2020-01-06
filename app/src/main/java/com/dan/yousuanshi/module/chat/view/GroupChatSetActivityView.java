package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.MyGroupInfoBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;

import java.util.List;

public interface GroupChatSetActivityView extends BaseView {
    void getGroupInfoSuccess(int code, MyGroupInfoBean data);
    void getGroupInfoFailed(int code,String msg);
    void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean);
    void getQiniuTokenFailed(int code, String message);
    void updateUserInfoSuccess(int code, List list);
    void updateUserInfoFailed(int code,String msg);
    void exitGroupSuccess(int code,List list);
    void exitGroupFailed(int code,String msg);
    void disbandGroupSuccess(int code,List list);
    void disbandGroupFailed(int code,String msg);
    void updateMyGroupNickNameSuccess(int code,List list);
    void updateMyGroupNickNameFailed(int code,String msg);
}
