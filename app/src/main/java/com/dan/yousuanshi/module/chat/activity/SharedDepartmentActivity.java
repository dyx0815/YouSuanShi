package com.dan.yousuanshi.module.chat.activity;

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
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.chat.presenter.SharedDepartmentActivityPresenter;
import com.dan.yousuanshi.module.chat.view.SharedDepartmentActivityView;
import com.dan.yousuanshi.module.mine.adapter.DepartmentTitleAdapter;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.shared.activity.SharedTeamPeopleActivity;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SharedDepartmentActivity extends BaseActivity<SharedDepartmentActivityPresenter> implements SharedDepartmentActivityView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    @BindView(R.id.recyclerView_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.ll_not_department)
    LinearLayout llNotDepartment;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    private List<DepartmentBean> departmentList = new ArrayList<>();
    private DepartmentAdapter adapter;
    private MyTeamBean myTeamBean;
    private List<DepartmentBean> parents = new ArrayList<>();
    private DepartmentTitleAdapter departmentTitleAdapter;
    private int type = 0;
    private List<Integer> departmentIdList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shared_department;
    }

    @Nullable
    @Override
    protected SharedDepartmentActivityPresenter initPresenter() {
        return new SharedDepartmentActivityPresenter();
    }

    @Override
    protected void initView() {
        myTeamBean = getIntent().getParcelableExtra("data");
        departmentIdList = getIntent().getIntegerArrayListExtra("departmentList");
        type = getIntent().getIntExtra("type",0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewTitle.setLayoutManager(linearLayoutManager);
        adapter = new DepartmentAdapter(this, departmentList,type);
        adapter.setOnItemClick(new DepartmentAdapter.OnItemClick() {
            @Override
            public void subordinate(DepartmentBean parent) {
                if (parents.size() == 0) {
                    parents.add(new DepartmentBean(myTeamBean.getCompanyId(), myTeamBean.getCompanyName()));
                } else if (parents.get(0).getDid() != myTeamBean.getCompanyId()) {
                    parents.add(0, new DepartmentBean(myTeamBean.getCompanyId(), myTeamBean.getCompanyName()));
                }
                parents.add(parent);
                Map<String, String> map = new HashMap<>();
                map.put("companyId", String.valueOf(myTeamBean.getCompanyId()));
                map.put("parentId", String.valueOf(parent.getDid()));
                presenter.getDepartment(map);
            }

            @Override
            public void department(DepartmentBean departmentBean) {
                Intent intent = new Intent(getActivity(), SharedDepartmentPeopleActivity.class);
                intent.putExtra("data", (Parcelable) departmentBean);
                startActivityForResult(intent,2);
            }

            @Override
            public void chooseDepartment(int count) {
                if(count == 0){
                    tvSure.setVisibility(View.GONE);
                }else{
                    tvSure.setVisibility(View.VISIBLE);
                }
            }
        });
        departmentTitleAdapter = new DepartmentTitleAdapter(this, parents);
        departmentTitleAdapter.setOnItemClick(new DepartmentTitleAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                Map<String, String> map = new HashMap<>();
                map.put("companyId", String.valueOf(myTeamBean.getCompanyId()));
                if(parents.get(position).getDid() == myTeamBean.getCompanyId()){
                    map.put("parentId", String.valueOf(0));
                }else{
                    map.put("parentId", String.valueOf(parents.get(position).getDid()));
                }
                presenter.getDepartment(map);
                parents.remove(0);
                while (parents.size() > position) {
                    parents.remove(parents.size()-1);
                }
                parents.add(0,new DepartmentBean(myTeamBean.getCompanyId(),myTeamBean.getCompanyName()));
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
        map.put("companyId", String.valueOf(myTeamBean.getCompanyId()));
        map.put("parentId", String.valueOf(parents.size() > 0 ? parents.get(parents.size() - 1).getDid() : 0));
        presenter.getDepartment(map);
    }

    @Override
    public void getDepartmentSuccess(int code, List<DepartmentBean> data) {
        departmentList.clear();
        if(parents.size()>0 && parents.get(parents.size()-1).getDid() == myTeamBean.getCompanyId()){
            parents.remove(0);
        }
        departmentTitleAdapter.notifyDataSetChanged();
        if(parents.size()>0){
            llDepartment.setVisibility(View.VISIBLE);
        }else{
            llDepartment.setVisibility(View.GONE);
        }
        if (data.size() == 0) {
            llNotDepartment.setVisibility(View.VISIBLE);
        } else {
            llNotDepartment.setVisibility(View.GONE);
            departmentList.addAll(data);
            adapter.notifyDataSetChanged();
        }
        if(departmentIdList!=null){
            for(Integer item : departmentIdList){
                for(DepartmentBean department : data){
                    if(department.getDid() == item){
                        department.setCheck(true);
                    }
                }
            }
        }
    }

    @Override
    public void getDepartmentFailed(int code, String msg) {
        ToastUtils.showToast(this, "请求部门失败：" + code + msg);
        Log.e("TeamStructureActivity", "请求部门失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_search,R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                break;
            case R.id.tv_sure:
                Intent intent = new Intent(this, SharedTeamPeopleActivity.class);
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) adapter.getChooseDepartment());
                setResult(2,intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == 1){
            Intent intent = new Intent(getActivity(),SharedBusinessCardActivity.class);
            intent.putExtra("data", (Parcelable) data.getParcelableExtra("data"));
            setResult(1,intent);
            finish();
        }
    }
}
