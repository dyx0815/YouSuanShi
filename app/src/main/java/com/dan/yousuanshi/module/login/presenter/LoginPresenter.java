package com.dan.yousuanshi.module.login.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.LoginRequest;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.login.view.LoginView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class LoginPresenter extends BasePresenter<LoginView> {
    public void login(Map<String,String> map){
        addToRxLife(LoginRequest.login(map, new RequestListener<LoginBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, LoginBean data) {
                if (isAttach()) {
                    getBaseView().loginSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().loginFailed(code,msg);
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

    public void getUser(){
        addToRxLife(LoginRequest.getUser(new RequestListener<UserBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, UserBean data) {
                if(isAttach()){
                    getBaseView().getUserSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getUserFailed(code,msg);
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

    public void sendCode(Map<String,String> map){
        addToRxLife(LoginRequest.sendCode(map, new RequestListener<SendCodeBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, SendCodeBean data) {
                if (isAttach()){
                    getBaseView().sendCodeSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().sendCodeFailed(code,msg);
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

    public void chattable(){
        addToRxLife(LoginRequest.chattable(new RequestListener<List>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int code, List data) {
                if (isAttach()){
                    getBaseView().chattableSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()){
                    getBaseView().chattableFailed(code,msg);
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
