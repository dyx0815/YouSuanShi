package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.ChatFragmentView;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class ChatPresenter extends BasePresenter<ChatFragmentView> {
    public void getUserInfoById(Map<String,String> map){
        addToRxLife(ChatRequest.getUserInfoById(map, new RequestListener<QueryUserBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, QueryUserBean data) {
                if(isAttach()){
                    getBaseView().getUserInfoSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getUserInfoFailed(code,msg);
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
