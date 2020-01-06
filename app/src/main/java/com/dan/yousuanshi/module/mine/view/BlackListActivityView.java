package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.mine.bean.BlackListBean;

public interface BlackListActivityView extends BaseView {
    void getBlackListSuccess(int code, BlackListBean data);
    void getBlackListFailed(int code, String msg);
}
