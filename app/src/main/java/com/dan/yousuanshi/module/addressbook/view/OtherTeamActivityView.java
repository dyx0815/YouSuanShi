package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;

import java.util.List;

public interface OtherTeamActivityView extends BaseView {
    void getMyTeamSuccess(int code, List<MyTeamBean> data);
    void getMyTeamFailed(int code,String msg);
}
