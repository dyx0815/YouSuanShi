package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.SearchFriendBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.SearchFriendByPhoneActivityView;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class SearchFriendByPhoneActivityPresenter extends BasePresenter<SearchFriendByPhoneActivityView> {
    public void searchFriend(Map<String,String> map){
        addToRxLife(AddressBookRequest.searchFriendByPhone(map, new RequestListener<SearchFriendBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, SearchFriendBean data) {
                if(isAttach()){
                    getBaseView().searchFriendSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().searchFriendFailed(code,msg);
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
