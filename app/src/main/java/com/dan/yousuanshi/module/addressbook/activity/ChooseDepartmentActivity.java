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
import com.dan.yousuanshi.module.addressbook.adapter.ChooseDepartmentAdapter;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.ChooseDepartmentActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.ChooseDepartmentActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseDepartmentActivity extends BaseActivity<ChooseDepartmentActivityPresenter> implements ChooseDepartmentActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<DepartmentPeopleBean> peopleList = new ArrayList<>();
    private ChooseDepartmentAdapter adapter;
    private List<DepartmentPeopleBean> departmentAdminList;
    private int departmentId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_department;
    }

    @Nullable
    @Override
    protected ChooseDepartmentActivityPresenter initPresenter() {
        return new ChooseDepartmentActivityPresenter();
    }

    @Override
    protected void initView() {
        departmentAdminList = getIntent().getParcelableArrayListExtra("departmentAdmin");
        departmentId = getIntent().getIntExtra("departmentId", 0);
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("departmentId", departmentId);
        presenter.getDepartmentPeople(map);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChooseDepartmentAdapter(this, peopleList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getDepartmentPeopleSuccess(int code, List<DepartmentPeopleBean> data) {
        for (DepartmentPeopleBean item : data) {
            for (DepartmentPeopleBean people : departmentAdminList) {
                if (item.getUser_id() == people.getUser_id()) {
                    item.setChecked(true);
                }
            }
        }
        peopleList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDepartmentPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this, "请求部门成员列表失败：" + code + msg);
        Log.e("ChooseTeamPeople", "请求部门成员列表失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if (adapter.getCheckedPeople().size() == 0) {
                    ToastUtils.showToast(this, "请选择成员！");
                    return;
                }
                Intent intent = new Intent(this, DepartmentSettingActivity.class);
                intent.putParcelableArrayListExtra("choosePeople", (ArrayList<? extends Parcelable>) adapter.getCheckedPeople());
                setResult(1, intent);
                finish();
                break;
        }
    }
}
