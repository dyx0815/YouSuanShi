package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.UpdateRemarkActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class UpdateRemarkActivityPresenter extends BasePresenter<UpdateRemarkActivityView> {
    public void updateRemark(Map<String,String> map) {
        addToRxLife(AddressBookRequest.updateRemark(map,new RequestListener<List>() {
            @Override
            public void onStart() {
                if (isAttach()) {
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if (isAttach()) {
                    getBaseView().updateRemarkSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().updateRemarkFailed(code, msg);
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
