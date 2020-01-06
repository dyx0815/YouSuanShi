package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.AddDepartmentActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.AddDepartmentActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddDepartmentActivity extends BaseActivity<AddDepartmentActivityPresenter> implements AddDepartmentActivityView {

    @BindView(R.id.et_department_name)
    EditText etDepartmentName;
    @BindView(R.id.tv_department_admin)
    TextView tvDepartmentAdmin;
    @BindView(R.id.ll_department_admin)
    LinearLayout llDepartmentAdmin;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private TeamPeopleBean teamPeopleBean;
    private int parentId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_department;
    }

    @Nullable
    @Override
    protected AddDepartmentActivityPresenter initPresenter() {
        return new AddDepartmentActivityPresenter();
    }

    @Override
    protected void initView() {
        parentId = getIntent().getIntExtra("parentId", 0);
        if (parentId != 0) {
            tvTitle.setText("添加子部门");
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void addDepartmentSuccess(int code, List list) {
        ToastUtils.showToast(this, "添加部门成功！");
        finish();
    }

    @Override
    public void addDepartmentFailed(int code, String msg) {
        ToastUtils.showToast(this, "添加部门失败！" + code + msg);
        Log.e("AddDepartment", "添加部门失败！" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.ll_department_admin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if (StringUtils.isEmpty(etDepartmentName.getText().toString())) {
                    ToastUtils.showToast(this, "请输入部门昵称！");
                    return;
                }
                if (teamPeopleBean == null) {
                    ToastUtils.showToast(this, "请选择部门主管！");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
                map.put("dName", etDepartmentName.getText().toString());
                map.put("depMaster", teamPeopleBean.getUser_id());
                map.put("parentId", parentId);
                presenter.addDepartment(map);
                break;
            case R.id.ll_department_admin:
                startActivityForResult(new Intent(this, TeamPeopleActivity.class), 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            teamPeopleBean = data.getParcelableExtra("data");
            tvDepartmentAdmin.setText(teamPeopleBean.getReal_name());
        }
    }
}
