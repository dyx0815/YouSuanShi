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
import com.dan.yousuanshi.module.addressbook.adapter.DepartmentAdapter;
import com.dan.yousuanshi.module.addressbook.presenter.DepartmentManagerActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.DepartmentManagerActivityView;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.mine.adapter.DepartmentTitleAdapter;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DepartmentManagerActivity extends BaseActivity<DepartmentManagerActivityPresenter> implements DepartmentManagerActivityView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.ll_not_department)
    LinearLayout llNotDepartment;
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    @BindView(R.id.tv_add_department)
    TextView tvAddDepartment;

    private List<DepartmentBean> departmentList = new ArrayList<>();
    private DepartmentAdapter adapter;
    private int companyId;
    private List<DepartmentBean> parents = new ArrayList<>();
    private DepartmentTitleAdapter departmentTitleAdapter;
    private int type = 0;//2为选择部门

    @Override
    protected int getLayoutId() {
        return R.layout.activity_department_manager;
    }

    @Nullable
    @Override
    protected DepartmentManagerActivityPresenter initPresenter() {
        return new DepartmentManagerActivityPresenter();
    }

    @Override
    protected void initView() {
        companyId = UserUtils.getInstance().getUserBean().getUser_company();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        type = getIntent().getIntExtra("type",0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewTitle.setLayoutManager(linearLayoutManager);
        adapter = new DepartmentAdapter(this, departmentList);
        adapter.setOnItemClick(new DepartmentAdapter.OnItemClick() {
            @Override
            public void subordinate(DepartmentBean parent) {
                if(parents.size() == 0){
                    parents.add(new DepartmentBean(UserUtils.getInstance().getUserBean().getUser_company(),UserUtils.getInstance().getUserBean().getC_name()));
                }else if(parents.get(0).getDid() != UserUtils.getInstance().getUserBean().getUser_company()){
                    parents.add(0,new DepartmentBean(UserUtils.getInstance().getUserBean().getUser_company(),UserUtils.getInstance().getUserBean().getC_name()));
                }
                parents.add(parent);
                Map<String, String> map = new HashMap<>();
                map.put("companyId", String.valueOf(companyId));
                map.put("parentId", String.valueOf(parent.getDid()));
                presenter.getDepartment(map);
            }

            @Override
            public void department(DepartmentBean departmentBean) {
                if(type == 2){
                    Intent intent = new Intent(getActivity(),DepartmentSettingActivity.class);
                    intent.putExtra("parent", (Parcelable) departmentBean);
                    setResult(3,intent);
                    finish();
                }else{
                    Intent intent = new Intent(getActivity(),DepartmentPeopleActivity.class);
                    intent.putExtra("data", (Parcelable) departmentBean);
                    startActivity(intent);
                }
            }

            @Override
            public void chooseDepartment(int count) {
            }
        });
        departmentTitleAdapter = new DepartmentTitleAdapter(this, parents);
        departmentTitleAdapter.setOnItemClick(new DepartmentTitleAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                Map<String, String> map = new HashMap<>();
                map.put("companyId", String.valueOf(companyId));
                if(parents.get(position).getDid() == UserUtils.getInstance().getUserBean().getUser_company()){
                    map.put("parentId", String.valueOf(0));
                }else{
                    map.put("parentId", String.valueOf(parents.get(position).getDid()));
                }
                presenter.getDepartment(map);
                parents.remove(0);
                while (parents.size() > position) {
                    parents.remove(parents.size()-1);
                }
                parents.add(0,new DepartmentBean(UserUtils.getInstance().getUserBean().getUser_company(),UserUtils.getInstance().getUserBean().getC_name()));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerViewTitle.setAdapter(departmentTitleAdapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new HashMap<>();
        map.put("companyId", String.valueOf(companyId));
        map.put("parentId", String.valueOf(parents.size() > 0 ? parents.get(parents.size() - 1).getDid() : 0));
        presenter.getDepartment(map);
    }

    @Override
    public void getDepartmentSuccess(int code, List<DepartmentBean> data) {
        departmentList.clear();
        if(parents.size()>0 && parents.get(parents.size()-1).getDid() == UserUtils.getInstance().getUserBean().getUser_company()){
            parents.remove(0);
        }
        departmentTitleAdapter.notifyDataSetChanged();
        if(parents.size()>0){
            llDepartment.setVisibility(View.VISIBLE);
            tvAddDepartment.setText("添加子部门");
        }else{
            llDepartment.setVisibility(View.GONE);
            tvAddDepartment.setText("添加部门");
        }
        if (data.size() == 0) {
            llNotDepartment.setVisibility(View.VISIBLE);
        } else {
            llNotDepartment.setVisibility(View.GONE);
            departmentList.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDepartmentFailed(int code, String msg) {
        ToastUtils.showToast(this, "请求部门失败：" + code + msg);
        Log.e("TeamStructureActivity", "请求部门失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.rl_add_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_add_department:
                Intent intent = new Intent(this, AddDepartmentActivity.class);
                intent.putExtra("parentId", parents.size() > 0 ? parents.get(parents.size() - 1).getDid() : 0);
                startActivity(intent);
                break;
        }
    }

}
