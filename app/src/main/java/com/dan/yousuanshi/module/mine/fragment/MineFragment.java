package com.dan.yousuanshi.module.mine.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseFragment;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.mine.activity.FeedbackActivity;
import com.dan.yousuanshi.module.mine.activity.MyCollectActivity;
import com.dan.yousuanshi.module.mine.activity.MyInfoActivity;
import com.dan.yousuanshi.module.mine.activity.MyTeamActivity;
import com.dan.yousuanshi.module.mine.activity.SettingActivity;
import com.dan.yousuanshi.module.mine.presenter.MineFragmentPresenter;
import com.dan.yousuanshi.module.mine.view.MineFragmentView;
import com.dan.yousuanshi.utils.MyHanlder;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment<MineFragmentPresenter> implements MineFragmentView {


    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.rl_my_team)
    RelativeLayout rlMyTeam;

    private UserBean userBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Nullable
    @Override
    protected MineFragmentPresenter initPresenter() {
        return new MineFragmentPresenter();
    }

    @Override
    protected void initView() {
        userBean = UserUtils.getInstance().getUserBean();
        initUser();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MyHanlder.getInstance().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StatusBarUtil.setColor(getActivity(), getActivity().getColor(R.color.color_F9BF5D), 0);
                }
            }, 1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getUser();
    }

    @Override
    public void getUserSuccess(int code, UserBean userBean) {
        this.userBean = userBean;
        initUser();
        UserUtils.getInstance().login(userBean);
    }

    @Override
    public void getUserFailed(int code, String msg) {
        Log.e("MineFragment", "获取用户信息" + "\tcode:" + code + "\t" + msg);
    }

    @OnClick({R.id.rl_mine, R.id.rl_my_team, R.id.rl_my_like, R.id.rl_invite, R.id.rl_feedback, R.id.rl_customer_service, R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mine://我的资料
                startActivity(new Intent(getActivity(), MyInfoActivity.class));
                break;
            case R.id.rl_my_team://我的团队
                startActivity(new Intent(getActivity(), MyTeamActivity.class));
                break;
            case R.id.rl_my_like://我的收藏
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;
            case R.id.rl_invite://邀请
                break;
            case R.id.rl_feedback://意见反馈
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.rl_customer_service://客服
                break;
            case R.id.rl_setting://设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    private void initUser() {
        Glide.with(getActivity()).load(userBean.getUser_portrait()).into(rivHeadIcon);
        tvName.setText(userBean.getNick_name());
//        if (StringUtils.isEmpty(userBean.getC_name())) {
//            rlMyTeam.setVisibility(View.GONE);
//        }else{
//            rlMyTeam.setVisibility(View.VISIBLE);
//        }
        if(!StringUtils.isEmpty(userBean.getC_name())){
            tvInfo.setText(userBean.getC_name());
        }else{
            tvInfo.setText("加入企业将体验更多功能");
        }
        if (StringUtils.isEmpty(userBean.getOffice())) {
            tvJob.setVisibility(View.GONE);
        } else {
            tvJob.setVisibility(View.VISIBLE);
            tvJob.setText(userBean.getOffice());
        }
    }
}
