package com.dan.yousuanshi.module.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.mine.adapter.SetFirstTeamAdapter;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.mine.presenter.SetFirstTeamActivityPresenter;
import com.dan.yousuanshi.module.mine.view.SetFirstTeamActivityView;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SetFirstTeamActivity extends BaseActivity<SetFirstTeamActivityPresenter> implements SetFirstTeamActivityView {

    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<MyTeamBean> myTeamList = new ArrayList<>();
    private SetFirstTeamAdapter adapter;
    private Dialog dialog;
    private MyTeamBean myTeamBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_first_team;
    }

    @Nullable
    @Override
    protected SetFirstTeamActivityPresenter initPresenter() {
        return new SetFirstTeamActivityPresenter();
    }

    @Override
    protected void initView() {
        String teamName = getIntent().getStringExtra("data");
        tvTeamName.setText(teamName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SetFirstTeamAdapter(this,myTeamList);
        adapter.setOnItemClick(new SetFirstTeamAdapter.OnItemClick() {
            @Override
            public void onItemClick(MyTeamBean myTeamBean) {
                SetFirstTeamActivity.this.myTeamBean = myTeamBean;
                showChooseDialog(myTeamBean.getCompanyId());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        presenter.getMyTeam();
    }

    @Override
    public void setFirstTeamSuccess(int code, List list) {
        Intent intent = new Intent(this,MyTeamActivity.class);
        intent.putExtra("data",myTeamBean.getCompanyName());
        setResult(1,intent);
        finish();
    }

    @Override
    public void setFirstTeamFailed(int code, String msg) {
        ToastUtils.showToast(this,"设置主企业失败："+code+msg);
        Log.e("SetFirstTeam","设置主企业失败"+code+msg);
    }

    @Override
    public void getMyTeamSuccess(int code, List<MyTeamBean> data) {
        myTeamList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getMyTeamFailed(int code, String msg) {
        ToastUtils.showToast(this,"获取我的团队失败："+code+msg);
        Log.e("SetFirstTeam","获取我的团队失败"+code+msg);
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    private void showChooseDialog(Integer companyId){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_set_first_team);
            dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Map<String,String> map = new HashMap<>();
                    map.put("companyId",String.valueOf(companyId));
                    presenter.setFirstTeam(map);
                }
            });
        }
        dialog.show();
    }
}
