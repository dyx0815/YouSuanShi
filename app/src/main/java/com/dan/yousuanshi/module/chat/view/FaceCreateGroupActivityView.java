package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.FaceCreateGroupBean;
import com.dan.yousuanshi.module.chat.bean.JoinFaceCreateGroupBean;

import java.util.List;

public interface FaceCreateGroupActivityView extends BaseView {
    void faceCreateGroupSuccess(int code, FaceCreateGroupBean data);
    void faceCreateGroupFailed(int code,String msg);
    void exitFaceCreateGroupSuccess(int code, List list);
    void exitFaceCreateGroupFailed(int code,String msg);
    void joinFaceCreateGroupSuccess(int code, JoinFaceCreateGroupBean data);
    void joinFaceCreateGroupFailed(int code,String msg);
}
