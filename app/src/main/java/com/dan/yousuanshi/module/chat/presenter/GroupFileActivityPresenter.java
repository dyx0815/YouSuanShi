package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.GroupFileActivityView;
import com.dan.yousuanshi.utils.FileItem;

import java.util.Map;

import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class GroupFileActivityPresenter extends BasePresenter<GroupFileActivityView> {
    public void getGroupFile(Map<String,Object> map){
        addToRxLife(ChatRequest.getGroupFile(map, new RequestListener<GroupFileBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, GroupFileBean data) {
                if(isAttach()){
                    getBaseView().getGroupFileSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getGroupFileFailed(code,msg);
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

    public void deleteGroupFile(Map<String,Object> map,int position){
        addToRxLife(ChatRequest.deleteGroupFile(map, new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                if(isAttach()){
                    getBaseView().deleteGroupFileSuccess(code,data,position);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().deleteGroupFileFailed(code,msg);
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

    public void getQiniuToken(FileItem fileItem){
        addToRxLife(ChatRequest.getQiniuToken(new RequestListener<QiniuTokenBean>() {
            @Override
            public void onStart() {
//                if(isAttach()){
//                    getBaseView().showLoadingDialog();
//                }
            }

            @Override
            public void onSuccess(int code, QiniuTokenBean data) {
                if(isAttach()){
                    getBaseView().getQiniuTokenSuccess(code,data,fileItem);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getQiniuTokenFailed(code,msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
//                if(isAttach()){
//                    getBaseView().dismissLoadingDialog();
//                }
            }
        }));
    }

    public void addGroupFile(Map<String,Object> map){
        addToRxLife(ChatRequest.addGroupFile(map, new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                if(isAttach()){
                    getBaseView().addGroupFileSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().addGroupFileFailed(code,msg);
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
