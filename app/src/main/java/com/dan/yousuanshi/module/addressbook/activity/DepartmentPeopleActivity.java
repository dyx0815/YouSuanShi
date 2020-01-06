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
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.adapter.DepartmentPeopleAdapter;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.DepartmentPeopleActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.DepartmentPeopleActivityView;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DepartmentPeopleActivity extends BaseActivity<DepartmentPeopleActivityPresenter> implements DepartmentPeopleActivityView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_add_people)
    TextView tvAddPeople;
    @BindView(R.id.tv_department_setting)
    TextView tvDepartmentSetting;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.ll_util)
    LinearLayout llUtil;

    private List<DepartmentPeopleBean> departmentPeopleList = new ArrayList<>();
    private DepartmentBean departmentBean;
    private DepartmentPeopleAdapter adapter;
    private List<DepartmentPeopleBean> departmentAdminList = new ArrayList<>();//部门主管
    private int type = 0;//2.选择部门群主（只显示主管）

    @Override
    protected int getLayoutId() {
        return R.layout.activity_department_people;
    }

    @Nullable
    @Override
    protected DepartmentPeopleActivityPresenter initPresenter() {
        return new DepartmentPeopleActivityPresenter();
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);
        departmentBean = getIntent().getParcelableExtra("data");
        tvDepartment.setText(departmentBean.getD_name());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DepartmentPeopleAdapter(this, departmentPeopleList, type);
        adapter.setOnItemClick(new DepartmentPeopleAdapter.OnItemClick() {
            @Override
            public void onItemClick(DepartmentPeopleBean departmentPeopleBean) {
                if (type == 2) {
                    Intent intent = new Intent(getActivity(), DepartmentSettingActivity.class);
                    intent.putExtra("creater", departmentPeopleBean);
                    setResult(2, intent);
                    finish();
                }
            }
        });
        if(type == 2){
            llUtil.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("departmentId", departmentBean.getDid());
        presenter.getDepartmentPeople(map);
    }

    @Override
    public void getDepartmentPeopleSuccess(int code, List<DepartmentPeopleBean> data) {
        departmentPeopleList.clear();
        for (DepartmentPeopleBean item : data) {
            if (item.getIs_supervisor() == 1) {
                departmentAdminList.add(item);
            }
        }
        if (type == 2) {
            for (DepartmentPeopleBean item : departmentAdminList) {
                if(item.getIs_supervisor() == 1){
                    departmentPeopleList.add(item);
                }
            }
        } else {
            departmentPeopleList.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDepartmentPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取部门成员列表失败：" + code + msg);
        Log.e("DepartmentPeople", "获取部门成员列表失败：" + code + msg);
    }

    @Override
    public void addPeopleToDepartmentSuccess(int code, List list) {
        onResume();
    }

    @Override
    public void addPeopleToDepartmentFailed(int code, String msg) {
        ToastUtils.showToast(this, "添加部门成员失败：" + code + msg);
        Log.e("DepartmentPeople", "添加部门成员失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_add_people, R.id.tv_department_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_add_people:
                Intent intent1 = new Intent(this, ChooseTeamPeopleActivity.class);
                intent1.putParcelableArrayListExtra("departmentAdmin", (ArrayList<? extends Parcelable>) departmentPeopleList);
                intent1.putExtra("type",2);
                startActivityForResult(intent1, 1);
                break;
            case R.id.tv_department_setting:
                MyApplication.addActivity(this);
                Intent intent = new Intent(this, DepartmentSettingActivity.class);
                intent.putParcelableArrayListExtra("departmentAdmin", (ArrayList<? extends Parcelable>) departmentAdminList);
                intent.putExtra("departmentBean", (Parcelable) departmentBean);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            List<TeamPeopleBean> peopleList = data.getParcelableArrayListExtra("choosePeople");
            if(peopleList !=null&&peopleList.size()>0){
                Map<String,Object> map = new HashMap<>();
                map.put("departmentId",departmentBean.getDid());
                List<Integer> userArray = new ArrayList<>();
                for(TeamPeopleBean item : peopleList){
                    userArray.add(item.getUser_id());
                }
                map.put("userArray",new Gson().toJson(userArray));
                map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
                presenter.addPeopleToDepartment(map);
            }
        }
    }
}
