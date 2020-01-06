package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.SearchTeamBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.SearchTeamActivityView;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class SearchTeamActivityPresenter extends BasePresenter<SearchTeamActivityView> {
    public void searchTeam(Map<String,String> map){
        addToRxLife(AddressBookRequest.searchTeam(map, new RequestListener<SearchTeamBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, SearchTeamBean data) {
                if(isAttach()){
                    getBaseView().searchTeamSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().searchTeamFailed(code,msg);
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
