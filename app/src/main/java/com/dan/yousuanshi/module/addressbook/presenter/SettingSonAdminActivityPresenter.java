package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.SonAdminBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.SettingSonAdminActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class SettingSonAdminActivityPresenter extends BasePresenter<SettingSonAdminActivityView> {
    public void getSonAdmin(Map<String,Object> map){
        addToRxLife(AddressBookRequest.getSonAdmin(map, new RequestListener<List<SonAdminBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<SonAdminBean> data) {
                if(isAttach()){
                    getBaseView().getSonAdminSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getSonAdminFailed(code,msg);
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

    public void removeSonAdmin(Map<String,Object> map){
        addToRxLife(AddressBookRequest.removeSonAdmin(map, new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                if(isAttach()){
                    getBaseView().removeSonAdminSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().removeSonAdminFailed(code,msg);
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
