package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.SettingTeamBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamInfoBean;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.ApplicationSettingActivityView;

import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class ApplicationSettingActivityPresenter extends BasePresenter<ApplicationSettingActivityView> {
    public void getTeamInfo(Map<String,Object> map){
        addToRxLife(AddressBookRequest.getTeamInfo(map, new RequestListener<TeamInfoBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, TeamInfoBean data) {
                if(isAttach()){
                    getBaseView().getTeamInfoSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getTeamInfoFailed(code,msg);
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

    public void settingTeam(Map<String,Object> map,int type){
        addToRxLife(AddressBookRequest.settingTeam(map, new RequestListener<SettingTeamBean>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, SettingTeamBean data) {
                if(isAttach()){
                    getBaseView().settingTeamSuccess(code,data,type);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().settingTeamFailed(code,msg);
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
