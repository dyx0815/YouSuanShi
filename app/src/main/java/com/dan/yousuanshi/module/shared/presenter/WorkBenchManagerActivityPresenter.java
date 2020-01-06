package com.dan.yousuanshi.module.shared.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.shared.SharedRequest;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;
import com.dan.yousuanshi.module.shared.view.WorkBenchManagerActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class WorkBenchManagerActivityPresenter extends BasePresenter<WorkBenchManagerActivityView> {
    public void getWorkbenchModel(int teamId){
        addToRxLife(SharedRequest.getWorkbenchModel(teamId, new RequestListener<List<AddWorkBenchBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<AddWorkBenchBean> data) {
                if(isAttach()){
                    getBaseView().getWorkBenchModelSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getWorkBenchModelFailed(code,msg);
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

    public void offLineModel(Map<String,Object> map){
        addToRxLife(SharedRequest.offLineModel(map, new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                if(isAttach()){
                    getBaseView().offLineModelSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().offLineModelFailed(code,msg);
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

    public void deleteWorkBench(Map<String,Object> map){
        addToRxLife(SharedRequest.deleteWorkBench(map, new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                if(isAttach()){
                    getBaseView().deleteWorkBenchSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().deleteWorkBenchFailed(code,msg);
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
