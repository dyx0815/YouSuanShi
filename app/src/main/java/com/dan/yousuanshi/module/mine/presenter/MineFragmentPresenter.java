package com.dan.yousuanshi.module.mine.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.login.bean.LoginRequest;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.mine.view.MineFragmentView;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class MineFragmentPresenter extends BasePresenter<MineFragmentView> {

    public void getUser(){
        addToRxLife(LoginRequest.getUser(new RequestListener<UserBean>() {
            @Override
            public void onStart() {
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
            }
        }));
    }
}
