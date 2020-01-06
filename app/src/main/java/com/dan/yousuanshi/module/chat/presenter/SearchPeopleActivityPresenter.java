package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.bean.SearchPeopleBean;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.SearchPeopleActivityView;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class SearchPeopleActivityPresenter extends BasePresenter<SearchPeopleActivityView> {
    public void searchPeople(Map<String,String> map){
        addToRxLife(ChatRequest.searchPeople(map, new RequestListener<SearchPeopleBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, SearchPeopleBean data) {
                if(isAttach()){
                    getBaseView().searchPeopleSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().searchPeopleFailed(code,msg);
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
