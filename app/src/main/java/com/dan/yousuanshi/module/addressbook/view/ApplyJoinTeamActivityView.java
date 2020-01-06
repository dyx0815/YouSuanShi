package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.TeamApplyListBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamQrCodeBean;

import per.goweii.rxhttp.request.base.BaseBean;

public interface ApplyJoinTeamActivityView extends BaseView {
    void applyJoinTeamSuccess(int code, BaseBean data);
    void applyJoinTeamFailed(int code,String msg);
    void getTeamApplyListSuccess(int code, TeamApplyListBean data);
    void getTeamApplyListFailed(int code,String msg);
    void getTeamQrCodeSuccess(int code, TeamQrCodeBean data);
    void getTeamQrCodeFailed(int code,String msg);
}
