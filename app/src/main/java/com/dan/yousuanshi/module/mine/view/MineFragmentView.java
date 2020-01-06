package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.login.bean.UserBean;

public interface MineFragmentView extends BaseView {
    void getUserSuccess(int code, UserBean userBean);

    void getUserFailed(int code, String msg);
}
