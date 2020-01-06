package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleInfo;
import com.dan.yousuanshi.module.addressbook.http.AddressBookRequest;
import com.dan.yousuanshi.module.addressbook.view.EditTeamPeopleActivityView;

import java.util.List;
import java.util.Map;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class EditTeamPeopleActivityPresenter extends BasePresenter<EditTeamPeopleActivityView> {
    public void teamPeopleInfo(Map<String,Object> map){
        addToRxLife(AddressBookRequest.teamPeopleInfo(map, new RequestListener<TeamPeopleInfo>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, TeamPeopleInfo data) {
                if(isAttach()){
                    getBaseView().getTeamPeopleInfoSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getTeamPeopleInfoFailed(code,msg);
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
    public void deleteTeamPeople(Map<String,Object> map){
        addToRxLife(AddressBookRequest.deleteTeamPeople(map, new RequestListener<List>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if(isAttach()){
                    getBaseView().deleteTeamPeopleSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().deleteTeamPeopleFailed(code,msg);
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
    public void editTeamPeople(Map<String,Object> map){
        addToRxLife(AddressBookRequest.editTeamPeople(map, new RequestListener<List>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List data) {
                if(isAttach()){
                    getBaseView().editTeamPeopleSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().editTeamPeopleFailed(code,msg);
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
