package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.chat.adapter.SharedDepartmentPeopleAdapter;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.chat.presenter.SharedDepartmentPeopleActivityPresenter;
import com.dan.yousuanshi.module.chat.view.SharedDepartmentPeopleActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SharedDepartmentPeopleActivity extends BaseActivity<SharedDepartmentPeopleActivityPresenter> implements SharedDepartmentPeopleActivityView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<DepartmentPeopleBean> departmentPeopleList = new ArrayList<>();
    private SharedDepartmentPeopleAdapter adapter;
    private Dialog dialog;
    private DepartmentBean departmentBean;
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shared_department_people;
    }

    @Nullable
    @Override
    protected SharedDepartmentPeopleActivityPresenter initPresenter() {
        return new SharedDepartmentPeopleActivityPresenter();
    }

    @Override
    protected void initView() {
        departmentBean = getIntent().getParcelableExtra("data");
        type = getIntent().getIntExtra("type",0);
        tvTitle.setText(departmentBean.getD_name());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SharedDepartmentPeopleAdapter(this,departmentPeopleList);
        adapter.setOnItemClick(new SharedDepartmentPeopleAdapter.OnItemClick() {
            @Override
            public void onItemClick(DepartmentPeopleBean departmentPeopleBean) {
                showDialog(departmentPeopleBean);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("departmentId", departmentBean.getDid());
        presenter.getDepartmentPeople(map);
    }

    @Override
    public void getDepartmentPeopleSuccess(int code, List<DepartmentPeopleBean> data) {
        departmentPeopleList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDepartmentPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取部门成员列表失败：" + code + msg);
        Log.e("DepartmentPeople", "获取部门成员列表失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                break;
        }
    }

    private void showDialog(DepartmentPeopleBean departmentPeopleBean){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_shared_my_friend);
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
                    Intent intent = new Intent(getActivity(),SharedBusinessCardActivity.class);
                    intent.putExtra("data", (Parcelable) new MyFriendBean(departmentPeopleBean.getUser_id(),departmentPeopleBean.getReal_name(),departmentPeopleBean.getUser_portrait()));
                    setResult(1,intent);
                    finish();
                }
            });
        }
        dialog.show();
    }
}
