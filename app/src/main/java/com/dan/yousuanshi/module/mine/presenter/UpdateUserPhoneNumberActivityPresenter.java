package com.dan.yousuanshi.module.mine.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.mine.http.MineRequest;
import com.dan.yousuanshi.module.mine.view.UpdateUserPhoneNumberActivityView;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class UpdateUserPhoneNumberActivityPresenter extends BasePresenter<UpdateUserPhoneNumberActivityView> {

    public void sendCode2(Map<String,String> map){
        addToRxLife(MineRequest.sendCode2(map,new RequestListener<SendCodeBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int code, SendCodeBean data) {
                if (isAttach()){
                    getBaseView().sendCode2Success(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().sendCode2Failed(code,msg);
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
