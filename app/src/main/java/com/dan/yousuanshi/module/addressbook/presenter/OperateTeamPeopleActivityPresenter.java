package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.OperateTeamPeopleActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class OperateTeamPeopleActivityPresenter extends BasePresenter<OperateTeamPeopleActivityView> {
    public void deleteTeamPeople(Map<String,Object> map){
        addToRxLife(AddressBookRequest.deleteTeamPeople(map, new RequestListener<List>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if(isAttach()){
                    getBaseView().deleteTeamPeopleSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().deleteTeamPeopleFailed(code,msg);
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
