package com.dan.yousuanshi.module.mine.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.mine.http.MineRequest;
import com.dan.yousuanshi.module.mine.view.SetFirstTeamActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class SetFirstTeamActivityPresenter extends BasePresenter<SetFirstTeamActivityView> {

    public void setFirstTeam(Map<String,String> map){
        addToRxLife(MineRequest.setFirstTeam(map, new RequestListener<List>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if(isAttach()){
                    getBaseView().setFirstTeamSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().setFirstTeamFailed(code,msg);
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

    public void getMyTeam(Map<String,String> map){
        addToRxLife(MineRequest.getMyTeam(map,new RequestListener<List<MyTeamBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<MyTeamBean> data) {
                if(isAttach()){
                    getBaseView().getMyTeamSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getMyTeamFailed(code,msg);
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

    public void getMyTeam(){
        addToRxLife(MineRequest.getMyTeam(new RequestListener<List<MyTeamBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<MyTeamBean> data) {
                if(isAttach()){
                    getBaseView().getMyTeamSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getMyTeamFailed(code,msg);
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
