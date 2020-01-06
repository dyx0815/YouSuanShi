package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.UpdateAdminActivityView;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class UpdateAdminActivityPresenter extends BasePresenter<UpdateAdminActivityView> {
    public void getCode(Map<String,Object> map) {
        addToRxLife(AddressBookRequest.getCode(map,new RequestListener<SendCodeBean>() {
            @Override
            public void onStart() {
                if (isAttach()) {
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, SendCodeBean data) {
                if (isAttach()) {
                    getBaseView().getCodeSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getCodeFailed(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
                if (isAttach()) {
                    getBaseView().dismissLoadingDialog();
                }
            }
        }));
    }

    public void checkCode(Map<String,Object> map) {
        addToRxLife(AddressBookRequest.updateAdminCheckCode(map,new RequestListener<CheckCodeBean>() {
            @Override
            public void onStart() {
                if (isAttach()) {
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, CheckCodeBean data) {
                if (isAttach()) {
                    getBaseView().checkCodeSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().checkCodeFailed(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
                if (isAttach()) {
                    getBaseView().dismissLoadingDialog();
                }
            }
        }));
    }
}
