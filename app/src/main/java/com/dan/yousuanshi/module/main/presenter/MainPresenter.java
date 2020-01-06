package com.dan.yousuanshi.module.main.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.LoginRequest;
import com.dan.yousuanshi.module.main.view.MainView;

import java.util.List;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class MainPresenter extends BasePresenter<MainView> {
    public void getNewToken(){
        addToRxLife(LoginRequest.getNewToken(new RequestListener<LoginBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int code, LoginBean data) {
                if (isAttach()){
                    getBaseView().getNewTokenSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()){
                    getBaseView().getNewTokenFailed(code,msg);
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

    public void socketSuccess(){
        addToRxLife(ChatRequest.socketSuccess(new RequestListener<List>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int code, List data) {
                if (isAttach()){
                    getBaseView().socketSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()){
                    getBaseView().socketFaied(code,msg);
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
}
