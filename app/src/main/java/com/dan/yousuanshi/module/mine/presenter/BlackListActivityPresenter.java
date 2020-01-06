package com.dan.yousuanshi.module.mine.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.mine.bean.BlackListBean;
import com.dan.yousuanshi.module.mine.http.MineRequest;
import com.dan.yousuanshi.module.mine.view.BlackListActivityView;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class BlackListActivityPresenter extends BasePresenter<BlackListActivityView> {

    public void getBlackList(Map<String,Object> map){
        addToRxLife(MineRequest.getBlackList(map, new RequestListener<BlackListBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, BlackListBean data) {
                if(isAttach()){
                    getBaseView().getBlackListSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getBlackListFailed(code,msg);
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
