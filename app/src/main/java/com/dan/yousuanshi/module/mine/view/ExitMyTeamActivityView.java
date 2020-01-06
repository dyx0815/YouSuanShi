package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface ExitMyTeamActivityView extends BaseView {
    void exitTeamSuccess(int code, List data);
    void exitTeamFailed(int code,String msg);
}
