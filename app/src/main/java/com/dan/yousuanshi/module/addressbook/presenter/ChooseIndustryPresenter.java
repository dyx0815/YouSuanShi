package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.IndustryBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.ChooseIndustryView;

import java.util.List;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class ChooseIndustryPresenter extends BasePresenter<ChooseIndustryView> {

    public void getIndustry(){
        addToRxLife(AddressBookRequest.getIndustry(new RequestListener<List<IndustryBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<IndustryBean> data) {
                if(isAttach()){
                    getBaseView().getIndustrySuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getIndustryFailed(code,msg);
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
