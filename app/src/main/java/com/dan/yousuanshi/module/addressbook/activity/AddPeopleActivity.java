package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatUserInfoBean;
import com.dan.yousuanshi.module.chat.bean.InviteJoinTeamBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.Date;

import butterknife.OnClick;

public class AddPeopleActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_people;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.rl_friend_invite, R.id.rl_qr_invite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_friend_invite:
                UserBean userBean = UserUtils.getInstance().getUserBean();
                ChatBean chatBean = new ChatBean(0,userBean.getNick_name()+"邀请你加入"+userBean.getC_name()
                ,userBean.getUser_id(), Constant.CHAT_ONE_TYPE,Constant.INVITE_JOIN_TEAM,1,UserUtils.getInstance().getUserBean().getUser_portrait()
                ,new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(),UserUtils.getInstance().getUserBean().getUser_portrait())
                , DateUtil.dateToString(new Date()),new ChatUserInfoBean());
                chatBean.setTemp(new InviteJoinTeamBean(userBean.getC_name(),userBean.getCompany_logo(),userBean.getUser_company()).toJson());
                Intent intent = new Intent(this,MyFriendActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("chatData",chatBean);
                startActivity(intent);
                finish();
                break;
            case R.id.rl_qr_invite:
                startActivity(new Intent(this,TeamQrCodeActivity.class));
                break;
        }
    }
}
