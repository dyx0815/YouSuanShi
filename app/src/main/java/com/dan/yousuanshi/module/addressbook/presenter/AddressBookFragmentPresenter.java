package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.AddressBookBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.AddressBookFragmentView;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class AddressBookFragmentPresenter extends BasePresenter<AddressBookFragmentView> {
    public void addressBook(){
        addToRxLife(AddressBookRequest.addressBook(new RequestListener<AddressBookBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int code, AddressBookBean data) {
                if(isAttach()){
                    getBaseView().addressBookSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().addressBookFailed(code,msg);
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
