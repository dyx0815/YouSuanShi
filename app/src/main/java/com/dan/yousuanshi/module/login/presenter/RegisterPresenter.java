package com.dan.yousuanshi.module.login.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.LoginRequest;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.login.view.RegisterView;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class RegisterPresenter extends BasePresenter<RegisterView> {
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

    public void register(Map<String,String> map){
        addToRxLife(LoginRequest.register(map, new RequestListener<LoginBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, LoginBean data) {
                if(isAttach()){
                    getBaseView().registerSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().registerFailed(code,msg);
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

    public void checkCode(Map<String,String> map){
        addToRxLife(LoginRequest.checkCode(map, new RequestListener<CheckCodeBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, CheckCodeBean data) {
                if(isAttach()){
                    getBaseView().checkCodeSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().checkCodeFailed(code,msg);
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

    public void getUserInfo(){
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
                    getBaseView().getUserInfoSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getUserInfoFailed(code,msg);
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
    public void forgetPwd(Map<String,String> map){
        addToRxLife(LoginRequest.forgetPwd(map,new RequestListener<LoginBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, LoginBean data) {
                if(isAttach()){
                    getBaseView().forgetPwdSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().forgetPwdFailed(code,msg);
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
