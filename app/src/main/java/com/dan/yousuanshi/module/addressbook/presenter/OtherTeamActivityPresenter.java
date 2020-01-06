package com.dan.yousuanshi.module.addressbook.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.addressbook.view.OtherTeamActivityView;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.mine.http.MineRequest;

import java.util.List;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class OtherTeamActivityPresenter extends BasePresenter<OtherTeamActivityView> {
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
