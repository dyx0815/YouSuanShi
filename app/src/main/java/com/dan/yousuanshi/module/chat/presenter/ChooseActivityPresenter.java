package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.ChooseActivityView;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class ChooseActivityPresenter extends BasePresenter<ChooseActivityView> {
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
