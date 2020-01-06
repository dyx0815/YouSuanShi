package com.dan.yousuanshi.module.mine.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.mine.http.MineRequest;
import com.dan.yousuanshi.module.mine.view.UpdateUserPhoneActivityView;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class UpdateUserPhoneActivityPresenter extends BasePresenter<UpdateUserPhoneActivityView> {

    public void sendCode(){
        addToRxLife(MineRequest.sendCode(new RequestListener<SendCodeBean>() {
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
}
