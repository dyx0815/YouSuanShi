package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.chat.view.SharedDepartmentPeopleActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class SharedDepartmentPeopleActivityPresenter extends BasePresenter<SharedDepartmentPeopleActivityView> {
    public void getDepartmentPeople(Map<String,Object> map){
        addToRxLife(AddressBookRequest.getDepartmentPeople(map, new RequestListener<List<DepartmentPeopleBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<DepartmentPeopleBean> data) {
                if(isAttach()){
                    getBaseView().getDepartmentPeopleSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getDepartmentPeopleFailed(code,msg);
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
