package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.PeopleSizeBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.ChoosePeopleSizeView;

import java.util.List;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class ChoosePeopleSizePresenter extends BasePresenter<ChoosePeopleSizeView> {
    public void getPeopleSize(){
        addToRxLife(AddressBookRequest.getPeopleSize(new RequestListener<List<PeopleSizeBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<PeopleSizeBean> data) {
                if(isAttach()){
                    getBaseView().getPeopleSizeSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getPeopleSizeFailed(code,msg);
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
