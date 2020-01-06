package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.OtherTeamAdapter;
import com.dan.yousuanshi.module.addressbook.presenter.OtherTeamActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.OtherTeamActivityView;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OtherTeamActivity extends BaseActivity<OtherTeamActivityPresenter> implements OtherTeamActivityView {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.iv_team_proof)
    ImageView ivTeamProof;

    private List<MyTeamBean> teamList = new ArrayList<>();
    private OtherTeamAdapter adapter;
    private MyTeamBean mainTeam;//主企业

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_team;
    }

    @Nullable
    @Override
    protected OtherTeamActivityPresenter initPresenter() {
        return new OtherTeamActivityPresenter();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        tvTeamName.setText(UserUtils.getInstance().getUserBean().getC_name());
        adapter = new OtherTeamAdapter(this,teamList);
        adapter.setOnItemClick(new OtherTeamAdapter.OnItemClick() {
            @Override
            public void onItemClick(MyTeamBean myTeamBean) {
                Intent intent = new Intent(getActivity(),OtherTeamInfoActivity.class);
                intent.putExtra("team",myTeamBean);
                startActivity(intent);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
        presenter.getMyTeam();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getMyTeamSuccess(int code, List<MyTeamBean> data) {
//        for(MyTeamBean item : data){
//            if(item.getMainCompany() == 1){//主企业
//                tvTeamName.setText(item.getCompanyName());
//                mainTeam = item;
//            }else{
//                teamList.add(item);
//            }
//        }
        teamList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getMyTeamFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取其他团队失败：" + code + msg);
        Log.e("OtherTeam", "获取其他团队失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_setting:
                break;
        }
    }
}
