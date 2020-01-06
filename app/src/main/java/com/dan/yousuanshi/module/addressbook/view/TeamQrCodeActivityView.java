package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.TeamQrCodeBean;

public interface TeamQrCodeActivityView extends BaseView {
    void getTeamQrCodeSuccess(int code, TeamQrCodeBean data);
    void getTeamQrCodeFailed(int code,String msg);
}
