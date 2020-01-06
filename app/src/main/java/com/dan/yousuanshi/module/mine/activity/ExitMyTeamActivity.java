package com.dan.yousuanshi.module.mine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.mine.presenter.ExitMyTeamActivityPresenter;
import com.dan.yousuanshi.module.mine.view.ExitMyTeamActivityView;
import com.dan.yousuanshi.utils.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ExitMyTeamActivity extends BaseActivity<ExitMyTeamActivityPresenter> implements ExitMyTeamActivityView {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_team_icon)
    ImageView ivTeamIcon;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.iv_team_proof)
    ImageView ivTeamProof;
    @BindView(R.id.tv_first_team)
    TextView tvFirstTeam;
    @BindView(R.id.rl_team)
    RelativeLayout rlTeam;
    @BindView(R.id.tv_exit_team)
    TextView tvExitTeam;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exit_my_team;
    }

    @Nullable
    @Override
    protected ExitMyTeamActivityPresenter initPresenter() {
        return null;
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this,getColor(R.color.white),0);
    }

    @Override
    protected void initView() {
        MyTeamBean myTeamBean = getIntent().getParcelableExtra("data");
        tvTeamName.setText(myTeamBean.getCompanyName());
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void exitTeamSuccess(int code, List data) {

    }

    @Override
    public void exitTeamFailed(int code, String msg) {

    }

    @OnClick({R.id.ll_back, R.id.tv_exit_team})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_exit_team:
                break;
        }
    }
}
