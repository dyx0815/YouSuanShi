package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.UpdateGroupFileActivityView;

import java.util.Map;

import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class UpdateGroupFileActivityPresenter extends BasePresenter<UpdateGroupFileActivityView> {
    public void renameGroupFile(Map<String,Object> map){
        addToRxLife(ChatRequest.renameGroupFile(map, new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                if(isAttach()){
                    getBaseView().renameGroupFileSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().renameGroupFileFailed(code,msg);
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
