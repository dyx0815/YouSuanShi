package com.dan.yousuanshi.module.login.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.login.bean.LoginRequest;
import com.dan.yousuanshi.module.login.view.WriteDataActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class WriteDataActivityPresenter extends BasePresenter<WriteDataActivityView> {
    public void getQiniuToken(){
        addToRxLife(ChatRequest.getQiniuToken(new RequestListener<QiniuTokenBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, QiniuTokenBean data) {
                if(isAttach()){
                    getBaseView().getQiniuTokenSuccess(code,data);
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
            }
        }));
    }

    public void writeData(Map<String,String> map){
        addToRxLife(LoginRequest.writeData(map,new RequestListener<List>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if(isAttach()){
                    getBaseView().writeDataSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().writeDataFailed(code,msg);
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
