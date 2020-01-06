package com.dan.yousuanshi.module.chat.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.TeamHomeBean;

public interface InviteJoinTeamActivityView extends BaseView {
    void teamHomeSuccess(int code, TeamHomeBean data);
    void teamHomeFailed(int code,String msg);
}
