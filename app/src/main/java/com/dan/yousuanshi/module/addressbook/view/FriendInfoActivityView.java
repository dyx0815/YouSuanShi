package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;

import java.util.List;

public interface FriendInfoActivityView extends BaseView {
    void getUserByIdSuccess(int code, QueryUserBean queryUserBean);
    void getUserByIdFailed(int code,String msg);
    void deleteFriendSuccess(int code, List list);
    void deleteFriendFailed(int code,String msg);
    void sendMessageSuccess(int code, MessageId messageId,long id);
    void sendMessageFailed(int code, String msg);
    void addBlackListSuccess(int code,List list);
    void addBlackListFailed(int code,String msg);
    void removeBlackListSuccess(int code,List list);
    void removeBlackListFailed(int code,String msg);
}
