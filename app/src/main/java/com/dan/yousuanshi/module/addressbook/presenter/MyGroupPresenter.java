package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.MyGroupBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.MyGroupView;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.module.chat.http.ChatRequest;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class MyGroupPresenter extends BasePresenter<MyGroupView> {

    public void getMyGroup(){
        addToRxLife(AddressBookRequest.getMyGroup(new RequestListener<List<MyGroupBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<MyGroupBean> data) {
                if(isAttach()){
                    getBaseView().getMyGroupSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getMyGroupFailed(code,msg);
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
    public void sendMessage(Map<String,String> map, long id){
        addToRxLife(ChatRequest.sendMessage(map, new RequestListener<MessageId>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, MessageId data) {
                if(isAttach()){
                    getBaseView().sendMessageSuccess(code,data,id);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().sendMessageFailed(code,msg);
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
