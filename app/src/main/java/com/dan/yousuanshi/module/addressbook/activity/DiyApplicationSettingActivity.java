package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.DiyApplicationSettingAdapter;
import com.dan.yousuanshi.module.addressbook.bean.DiyApplicationSettingBean;
import com.dan.yousuanshi.module.addressbook.presenter.DiyApplicationSettingActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.DiyApplicationSettingActivityView;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DiyApplicationSettingActivity extends BaseActivity<DiyApplicationSettingActivityPresenter> implements DiyApplicationSettingActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.tv_diy)
    TextView tvDiy;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<DiyApplicationSettingBean> diyList = new ArrayList<>();
    private DiyApplicationSettingAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_diy_application_setting;
    }

    @Nullable
    @Override
    protected DiyApplicationSettingActivityPresenter initPresenter() {
        return new DiyApplicationSettingActivityPresenter();
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this, getColor(R.color.color_FC9F44), 0);
    }

    @Override
    protected void initView() {
        tvTeamName.setText(UserUtils.getInstance().getUserBean().getC_name());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiyApplicationSettingAdapter(this,diyList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("diyType",0);
        presenter.getDiyApplicationSetting(map);
    }

    @Override
    public void getDiyApplicationSettingSuccess(int code, List<DiyApplicationSettingBean> data) {
        diyList.clear();
        diyList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDiyApplicationSettingFailed(int code, String msg) {
        ToastUtils.showToast(this, "请求diy设置信息失败：" + code + msg);
        Log.e("DiyApplicationSetting", "请求diy设置信息失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.tv_diy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                break;
            case R.id.tv_diy:
                Intent intent = new Intent(this,SettingApplicationActivity.class);
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) diyList);
                startActivity(intent);
                break;
        }
    }
}
