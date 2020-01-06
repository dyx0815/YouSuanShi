package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.UpdateAdmin2ActivityView;
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class UpdateAdmin2ActivityPresenter extends BasePresenter<UpdateAdmin2ActivityView> {
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

    public void getUserInfoById(Map<String,String> map){
        addToRxLife(AddressBookRequest.getUserInfoById(map, new RequestListener<QueryUserBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, QueryUserBean data) {
                if(isAttach()){
                    getBaseView().getUserByIdSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getUserByIdFailed(code,msg);
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

    public void updateAdmin(Map<String,Object> map) {
        addToRxLife(AddressBookRequest.updateAdmin(map,new RequestListener<List>() {
            @Override
            public void onStart() {
                if (isAttach()) {
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if (isAttach()) {
                    getBaseView().updateAdminSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().updateAdminFailed(code, msg);
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
