package com.dan.yousuanshi.module.shared.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.shared.SharedRequest;
import com.dan.yousuanshi.module.shared.bean.AnnouncementBean;
import com.dan.yousuanshi.module.shared.bean.BannerBean;
import com.dan.yousuanshi.module.shared.bean.WorkbenchBean;
import com.dan.yousuanshi.module.shared.view.SharedFragmentView;

import java.util.List;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class SharedFragmentPresenter extends BasePresenter<SharedFragmentView> {
    public void getNewAnnouncement(int teamId){
        addToRxLife(SharedRequest.getNewAnnouncement(teamId, new RequestListener<AnnouncementBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, AnnouncementBean data) {
                if(isAttach()){
                    getBaseView().getNewAnnouncementSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getNewAnnouncementFailed(code,msg);
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

    public void getWorkbench(int teamId){
        addToRxLife(SharedRequest.getWorkbench(teamId, new RequestListener<WorkbenchBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, WorkbenchBean data) {
                if(isAttach()){
                    getBaseView().getWorkbenchSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getWorkbenchFailed(code,msg);
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

    public void getBanner(int teamId){
        addToRxLife(SharedRequest.getBanner(teamId, new RequestListener<List<BannerBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<BannerBean> data) {
                if(isAttach()){
                    getBaseView().getBannerSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getBannerFailed(code,msg);
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
