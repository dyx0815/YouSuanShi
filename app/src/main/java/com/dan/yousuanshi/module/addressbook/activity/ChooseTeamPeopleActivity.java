package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.ChooseTeamPeopleAdapter;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.ChooseTeamPeopleActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.ChooseTeamPeopleActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseTeamPeopleActivity extends BaseActivity<ChooseTeamPeopleActivityPresenter> implements ChooseTeamPeopleActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<TeamPeopleBean> teamPeopleList = new ArrayList<>();
    private ChooseTeamPeopleAdapter adapter;
    private List<DepartmentPeopleBean> departmentAdminList;
    private int type = 0;//2为添加成员

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_team_people;
    }

    @Nullable
    @Override
    protected ChooseTeamPeopleActivityPresenter initPresenter() {
        return new ChooseTeamPeopleActivityPresenter();
    }

    @Override
    protected void initView() {
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        presenter.getTeamPeople(map);
        departmentAdminList = getIntent().getParcelableArrayListExtra("departmentAdmin");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChooseTeamPeopleAdapter(this,teamPeopleList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getTeamPeopleSuccess(int code, List<TeamPeopleBean> data) {
        for(TeamPeopleBean item : data){
            for(DepartmentPeopleBean people : departmentAdminList){
                if(item.getUser_id() == people.getUser_id()){
                    item.setChecked(true);
                }
            }
        }
        teamPeopleList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getTeamPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this,"请求公司成员列表失败："+code+msg);
        Log.e("ChooseTeamPeople","请求公司成员列表失败："+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(adapter.getCheckedPeople().size() == 0){
                    ToastUtils.showToast(this,"请选择成员！");
                    return;
                }
                if(type == 2){
                    Intent intent = new Intent(this,DepartmentPeopleActivity.class);
                    intent.putParcelableArrayListExtra("choosePeople", (ArrayList<? extends Parcelable>) adapter.getCheckedPeople());
                    setResult(1,intent);
                    finish();
                }else{
                    Intent intent = new Intent(this,DepartmentSettingActivity.class);
                    intent.putParcelableArrayListExtra("choosePeople", (ArrayList<? extends Parcelable>) adapter.getCheckedPeople());
                    setResult(1,intent);
                    finish();
                }
                break;
        }
    }
}
