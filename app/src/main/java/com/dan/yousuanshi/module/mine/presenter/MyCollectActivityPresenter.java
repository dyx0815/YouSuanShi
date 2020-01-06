package com.dan.yousuanshi.module.mine.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.mine.bean.MyCollectBean;
import com.dan.yousuanshi.module.mine.http.MineRequest;
import com.dan.yousuanshi.module.mine.view.MyCollectActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class MyCollectActivityPresenter extends BasePresenter<MyCollectActivityView> {
    public void getMyCollect(Map<String,Object> map){
        addToRxLife(MineRequest.getMyCollect(map,new RequestListener<MyCollectBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, MyCollectBean data) {
                if(isAttach()){
                    getBaseView().getMyCollectSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getMyCollectFailed(code,msg);
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
    public void deleteCollect(Map<String,Object> map, int position){
        addToRxLife(MineRequest.deleteCollect(map,new RequestListener<List>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if(isAttach()){
                    getBaseView().deleteCollectSuccess(code,data,position);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().deleteCollectFailed(code,msg);
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
