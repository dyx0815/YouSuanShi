package com.dan.yousuanshi.module.addressbook.presenter;

import android.util.Log;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.NewFriendBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.NewFriendView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class NewFriendPresenter extends BasePresenter<NewFriendView> {
    public void getNewFriend() {
        addToRxLife(AddressBookRequest.getNewFriend(new RequestListener<NewFriendBean>() {
            @Override
            public void onStart() {
                if (isAttach()) {
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, NewFriendBean data) {
                if (isAttach()) {
                    getBaseView().getNewFriendSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getNewFriendFailed(code, msg);
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

    public void agreeFriend(Map<String,String> map) {
        addToRxLife(AddressBookRequest.agreeFriend(map,new RequestListener<List>() {
            @Override
            public void onStart() {
                if (isAttach()) {
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if (isAttach()) {
                    getBaseView().agreeFriendSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().agreeFriendFailed(code, msg);
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

    public void clearNewFriend(final Map<String,int[]> map) {
        Map<String,String> map1 = new HashMap<>();
        map1.put("sendFriendIdArray",new Gson().toJson(map.get("sendFriendIdArray")));
        Log.e("NewFriendActivity",new Gson().toJson(map.get("sendFriendIdArray")));
        addToRxLife(AddressBookRequest.clearNewFriend(map1,new RequestListener<List>() {
            @Override
            public void onStart() {
                if (isAttach()) {
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if (isAttach()) {
                    getBaseView().clearNewFriendSuccess(code, data,map.get("sendFriendIdArray"));
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().clearNewFriendFailed(code, msg);
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
