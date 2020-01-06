package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.ChatActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class ChatActivityPresenter extends BasePresenter<ChatActivityView> {

    public void getQiniuToken(final ChatBean chatBean,long msgId,int position){
        addToRxLife(ChatRequest.getQiniuToken(new RequestListener<QiniuTokenBean>() {
            @Override
            public void onStart() {
//                if(isAttach()){
//                    getBaseView().showLoadingDialog();
//                }
            }

            @Override
            public void onSuccess(int code, QiniuTokenBean data) {
                if(isAttach()){
                    getBaseView().getQiniuTokenSuccess(code,data,chatBean,msgId,position);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getQiniuTokenFailed(code,msg,msgId,position);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
//                if(isAttach()){
//                    getBaseView().dismissLoadingDialog();
//                }
            }
        }));
    }

    public void sendMessage(Map<String,String> map, long id,int position){
        addToRxLife(ChatRequest.sendMessage(map, new RequestListener<MessageId>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, MessageId data) {
                if(isAttach()){
                    getBaseView().sendMessageSuccess(code,data,id,position);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().sendMessageFailed(code,msg,id,position);
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

    public void addCollect(Map<String,Object> map){
        addToRxLife(ChatRequest.addCollect(map, new RequestListener<List>() {
            @Override
            public void onStart() {
                if (isAttach()) {
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                getBaseView().addCollectSuccess(code,data);
            }

            @Override
            public void onFailed(int code, String msg) {
                getBaseView().addCollectFailed(code,msg);
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
