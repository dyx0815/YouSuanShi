package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.bean.FaceCreateGroupBean;
import com.dan.yousuanshi.module.chat.bean.JoinFaceCreateGroupBean;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.FaceCreateGroupActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class FaceCreateGroupActivityPresenter extends BasePresenter<FaceCreateGroupActivityView> {
    public void faceCreateGroup(Map<String,Object> map){
        addToRxLife(ChatRequest.faceCreateGroup(map,new RequestListener<FaceCreateGroupBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, FaceCreateGroupBean data) {
                if(isAttach()){
                    getBaseView().faceCreateGroupSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().faceCreateGroupFailed(code,msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
                if(isAttach()){
                    getBaseView().dismissLoadingDialog();
                }
            }
        }));
    }

    public void exitFaceCreateGroup(Map<String,Object> map){
        addToRxLife(ChatRequest.exitFaceCreateGroup(map,new RequestListener<List>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if(isAttach()){
                    getBaseView().exitFaceCreateGroupSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().exitFaceCreateGroupFailed(code,msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
                if(isAttach()){
                    getBaseView().dismissLoadingDialog();
                }
            }
        }));
    }

    public void joinFaceCreateGroup(Map<String,Object> map){
        addToRxLife(ChatRequest.joinFaceCreateGroup(map,new RequestListener<JoinFaceCreateGroupBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, JoinFaceCreateGroupBean data) {
                if(isAttach()){
                    getBaseView().joinFaceCreateGroupSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().joinFaceCreateGroupFailed(code,msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
                if(isAttach()){
                    getBaseView().dismissLoadingDialog();
                }
            }
        }));
    }
}
