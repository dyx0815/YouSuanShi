package com.dan.yousuanshi.module.mine.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.mine.adapter.MyTeamAdapter;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.mine.presenter.MyTeamActivityPresenter;
import com.dan.yousuanshi.module.mine.view.MyTeamActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTeamActivity extends BaseActivity<MyTeamActivityPresenter> implements MyTeamActivityView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;

    private List<MyTeamBean> myTeamList = new ArrayList<>();
    private MyTeamAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_team;
    }

    @Nullable
    @Override
    protected MyTeamActivityPresenter initPresenter() {
        return new MyTeamActivityPresenter();
    }

    @Override
    protected void initView() {
        String teamName = getIntent().getStringExtra("data");
        if (StringUtils.isEmpty(teamName)) {
            tvTeamName.setText(UserUtils.getInstance().getUserBean().getC_name());
        } else {
            tvTeamName.setText(teamName);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyTeamAdapter(this, myTeamList);
        adapter.setOnItemClick(new MyTeamAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MyTeamActivity.this, ExitMyTeamActivity.class);
                intent.putExtra("data", myTeamList.get(position));
                startActivity(intent);
            }

            @Override
            public void setting(int position) {
//                Intent intent = new Intent(MyTeamActivity.this, ExitMyTeamActivity.class);
//                intent.putExtra("data", myTeamList.get(position));
//                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("isAllCompany", String.valueOf(1));
        presenter.getMyTeam(map);
    }

    @Override
    public void getMyTeamSuccess(int code, List<MyTeamBean> data) {
        myTeamList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getMyTeamFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取我的企业失败：" + code + "\t" + msg);
        Log.e("MyTeamActivity", "获取我的企业失败：" + code + "\t" + msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_setting, R.id.ll_first_team})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_setting://设置主企业
                Intent intent = new Intent(this, SetFirstTeamActivity.class);
                intent.putExtra("data", tvTeamName.getText().toString());
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_first_team://设置主企业
                Intent intent1 = new Intent(this, SetFirstTeamActivity.class);
                intent1.putExtra("data", tvTeamName.getText().toString());
                startActivityForResult(intent1, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&&resultCode == 1){
            String teamName = data.getStringExtra("data");
            tvTeamName.setText(teamName);
        }
    }
}
