package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.SharedDepartmentActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class SharedDepartmentActivityPresenter extends BasePresenter<SharedDepartmentActivityView> {

    public void getDepartment(Map<String,String> map){
        addToRxLife(ChatRequest.getDepartment(map, new RequestListener<List<DepartmentBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<DepartmentBean> data) {
                if(isAttach()){
                    getBaseView().getDepartmentSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getDepartmentFailed(code,msg);
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