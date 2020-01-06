package com.dan.yousuanshi.module.shared.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.shared.SharedRequest;
import com.dan.yousuanshi.module.shared.bean.AnnouncementLisBean;
import com.dan.yousuanshi.module.shared.view.AnnouncementActivityView;

import java.util.Map;

import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class AnnouncementActivityPresenter extends BasePresenter<AnnouncementActivityView> {
    public void getAnnouncement(Map<String,Object> map){
        addToRxLife(SharedRequest.getAnnouncement(map, new RequestListener<AnnouncementLisBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, AnnouncementLisBean data) {
                if(isAttach()){
                    getBaseView().getAnnouncementSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getAnnouncementFailed(code,msg);
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

    public void deleteSharedAnnouncement(Map<String,Object> map,int position){
        addToRxLife(SharedRequest.deleteSharedAnnouncement(map, new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                if(isAttach()){
                    getBaseView().deleteSharedAnnouncementSuccess(code,data,position);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().deleteSharedAnnouncementFailed(code,msg);
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
