package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;

import java.util.List;

public interface SetFirstTeamActivityView extends BaseView {
    void setFirstTeamSuccess(int code, List list);
    void setFirstTeamFailed(int code,String msg);
    void getMyTeamSuccess(int code, List<MyTeamBean> data);
    void getMyTeamFailed(int code,String msg);
}
