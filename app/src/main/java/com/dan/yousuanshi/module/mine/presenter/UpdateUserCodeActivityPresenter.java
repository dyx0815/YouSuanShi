package com.dan.yousuanshi.module.mine.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.mine.http.MineRequest;
import com.dan.yousuanshi.module.mine.view.UpdateUserCodeActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class UpdateUserCodeActivityPresenter extends BasePresenter<UpdateUserCodeActivityView> {
    public void sendCode(){
        addToRxLife(MineRequest.sendCode(new RequestListener<SendCodeBean>() {
            @Override
            public void onStart() {

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

            }
        }));
    }

    public void updatePhoneCheckCode(Map<String,String> map){
        addToRxLife(MineRequest.checkCode(map,new RequestListener<CheckCodeBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, CheckCodeBean data) {
                if (isAttach()){
                    getBaseView().updatePhoneCheckCodeSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().updatePhoneCheckCodeFailed(code,msg);
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

    public void updatePhoneCheckCode2(Map<String,String> map){
        addToRxLife(MineRequest.checkCode2(map,new RequestListener<List>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if (isAttach()){
                    getBaseView().updatePhoneCheckCodeSuccess2(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().updatePhoneCheckCodeFailed2(code,msg);
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
