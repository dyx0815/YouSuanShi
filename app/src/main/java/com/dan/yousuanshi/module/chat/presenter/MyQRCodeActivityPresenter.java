package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.TeamQrCodeBean;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.MyQRCodeActivityView;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class MyQRCodeActivityPresenter extends BasePresenter<MyQRCodeActivityView> {
    public void getMyQrcode(){
        addToRxLife(ChatRequest.getMyQrcode(new RequestListener<TeamQrCodeBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, TeamQrCodeBean data) {
                if(isAttach()){
                    getBaseView().getMyQrCodeSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getMyQrCodeFailed(code,msg);
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
