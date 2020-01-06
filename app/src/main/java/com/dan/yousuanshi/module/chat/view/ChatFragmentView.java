package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;

public interface ChatFragmentView extends BaseView {
    void getUserInfoSuccess(int code, QueryUserBean user);
    void getUserInfoFailed(int code,String message);
}
