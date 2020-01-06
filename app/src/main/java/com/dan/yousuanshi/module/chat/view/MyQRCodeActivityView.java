package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.TeamQrCodeBean;

public interface MyQRCodeActivityView extends BaseView {
    void getMyQrCodeSuccess(int code, TeamQrCodeBean data);
    void getMyQrCodeFailed(int code,String msg);
}
