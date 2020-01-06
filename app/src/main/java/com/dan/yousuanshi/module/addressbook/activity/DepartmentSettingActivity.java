package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentInfoBean;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.DepartmentSettingActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.DepartmentSettingActivityView;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DepartmentSettingActivity extends BaseActivity<DepartmentSettingActivityPresenter> implements DepartmentSettingActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.et_department_name)
    EditText etDepartmentName;
    @BindView(R.id.ll_department_name)
    LinearLayout llDepartmentName;
    @BindView(R.id.tv_department_admin)
    TextView tvDepartmentAdmin;
    @BindView(R.id.ll_department_admin)
    LinearLayout llDepartmentAdmin;
    @BindView(R.id.s_department_group)
    Switch sDepartmentGroup;
    @BindView(R.id.ll_department_group)
    LinearLayout llDepartmentGroup;
    @BindView(R.id.ll_set_group_create)
    LinearLayout llSetGroupCreate;
    @BindView(R.id.ll_before_department)
    LinearLayout llBeforeDepartment;
    @BindView(R.id.tv_delete_department)
    TextView tvDeleteDepartment;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.tv_creater)
    TextView tvCreater;
    @BindView(R.id.tv_parent_department)
    TextView tvparentDepartment;

    private DepartmentBean departmentBean;
    private List<DepartmentPeopleBean> departmentAdminList;//传来的部门主管
    private List<DepartmentPeopleBean> choosePeople;//选择的部门主管
    private DepartmentPeopleBean creater;//群主
    private DepartmentInfoBean departmentInfo;
    private DepartmentBean parent;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deparement_setting;
    }

    @Nullable
    @Override
    protected DepartmentSettingActivityPresenter initPresenter() {
        return new DepartmentSettingActivityPresenter();
    }

    @Override
    protected void initView() {
        departmentAdminList = getIntent().getParcelableArrayListExtra("departmentAdmin");
        if (departmentAdminList.size() > 1) {
            tvDepartmentAdmin.setText(departmentAdminList.size() + "人");
        } else if(departmentAdminList.size()>0){
            tvDepartmentAdmin.setText(departmentAdminList.get(0).getReal_name());
        }
        departmentBean = getIntent().getParcelableExtra("departmentBean");
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("departmentId", departmentBean.getDid());
        presenter.departmentInfo(map);
        sDepartmentGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llSetGroupCreate.setVisibility(View.VISIBLE);
                } else {
                    llSetGroupCreate.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void updateDepartmentSuccess(int code, List list) {
        ToastUtils.showToast(this,"修改部门信息成功！");
        finish();
    }

    @Override
    public void updateDepartmentFailed(int code, String msg) {
        ToastUtils.showToast(this, "修改部门信息失败：" + code + msg);
        Log.e("UpdateDepartment", "修改部门信息失败：" + code + msg);
    }

    @Override
    public void departmentInfoSuccess(int code, DepartmentInfoBean data) {
        departmentInfo = data;
        if (!StringUtils.isEmpty(data.getReal_name())) {
//            tvDepartmentAdmin.setText(data.getReal_name());
        }
        if (data.getStop_chat() == 0) {
            sDepartmentGroup.setChecked(true);
            tvCreater.setText(data.getGroup_creater_name());
        } else {
            sDepartmentGroup.setChecked(false);
        }
        tvparentDepartment.setText(data.getGroup_parent_name());
        etDepartmentName.setText(data.getGroup_name());
    }

    @Override
    public void departmentInfoFailed(int code, String msg) {
        ToastUtils.showToast(this, "请求部门详情失败：" + code + msg);
        Log.e("UpdateDepartment", "请求部门详情失败：" + code + msg);
    }

    @Override
    public void deleteDepartmentSuccess(int code, List list) {
        MyApplication.clearActivity();
    }

    @Override
    public void deleteDepartmentFailed(int code, String msg) {
        ToastUtils.showToast(this, "删除部门失败：" + code + msg);
        Log.e("UpdateDepartment", "删除部门失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.ll_department_admin, R.id.ll_set_group_create, R.id.ll_before_department, R.id.tv_delete_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit://完成
                if (StringUtils.isEmpty(etDepartmentName.getText().toString())) {
                    ToastUtils.showToast(this, "部门名称不能为空！");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
                map.put("departmentId", departmentBean.getDid());
                List<Integer> oldMasterId = new ArrayList<>();
                for (DepartmentPeopleBean item : departmentAdminList) {
                    oldMasterId.add(item.getUser_id());
                }
                map.put("OldmasterId", new Gson().toJson(oldMasterId));//旧主管
                Log.e("Department","旧主管："+new Gson().toJson(oldMasterId));
                map.put("stopChat", sDepartmentGroup.isChecked() ? 0 : 1);
                if (!departmentInfo.getGroup_name().equals(etDepartmentName.getText().toString())) {//如果部门名称变化再传
                    map.put("dName", etDepartmentName.getText().toString());
                }
                if (choosePeople != null && choosePeople.size() > 0) {//更改了主管
                    List<Integer> masterId = new ArrayList<>();
                    for (DepartmentPeopleBean item : choosePeople) {
                        masterId.add(item.getUser_id());
                    }
                    map.put("masterId", new Gson().toJson(masterId));//新主管
                    Log.e("Department","新主管："+new Gson().toJson(masterId));
                    if (parent != null) {//判断有没有选择上级部门
                        map.put("parentDepartmentId", parent.getDid());
                    } else {
                        map.put("parentDepartmentId", departmentInfo.getParent_id());
                    }
                    if (sDepartmentGroup.isChecked()) {//判断有没有开启部门群，如果开启了 则判断有没有选择群主
                        if (creater != null) {
                            map.put("chatCreater",creater.getUser_id());
                            Log.e("Department",creater.getUser_id()+"");
                        }else{
                            map.put("chatCreater",choosePeople.get(0).getUser_id());//没选择群主则传新管理员中第一个选择的
                        }
                    } else {
                        map.put("chatCreater", departmentInfo.getGroup_creater());// 如果没有就传接口返回的参数
                    }
                } else {
                    if (departmentAdminList != null && departmentAdminList.size() > 0) {
                        if (sDepartmentGroup.isChecked()) {//判断有没有开启部门群，如果开启了 则判断有没有选择群主
                            if (creater != null) {
                                map.put("chatCreater",creater.getUser_id());
                            }
                        } else {
                            map.put("chatCreater", departmentInfo.getGroup_creater());// 如果没有就传接口返回的参数
                        }
                        if (parent != null) {//父级部门
                            map.put("parentDepartmentId", parent.getDid());
                        } else {
                            map.put("parentDepartmentId", departmentInfo.getParent_id());
                        }
                    }
                }
                presenter.updateDepartment(map);
                break;
            case R.id.ll_department_admin://部门主管
                Intent intent = new Intent(this, ChooseDepartmentActivity.class);
                intent.putParcelableArrayListExtra("departmentAdmin", (ArrayList<? extends Parcelable>) departmentAdminList);
                intent.putExtra("departmentId",departmentBean.getDid());
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_set_group_create://设置部门群群主
                Intent intent1 = new Intent(this, DepartmentPeopleActivity.class);
                intent1.putExtra("type", 2);
                intent1.putExtra("data", (Parcelable) departmentBean);
                startActivityForResult(intent1, 2);
                break;
            case R.id.ll_before_department://上级部门
                Intent intent2 = new Intent(this, DepartmentManagerActivity.class);
                intent2.putExtra("type", 2);
                startActivityForResult(intent2, 3);
                break;
            case R.id.tv_delete_department://删除部门
                MyApplication.addActivity(this);
                showDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {//选择部门主管
            choosePeople = data.getParcelableArrayListExtra("choosePeople");
            if (choosePeople.size() > 1) {
                tvDepartmentAdmin.setText(choosePeople.size() + "人");
            } else {
                tvDepartmentAdmin.setText(choosePeople.get(0).getReal_name());
            }
        } else if (requestCode == 2 && resultCode == 2) {//选择部门群主
            creater = data.getParcelableExtra("creater");
            tvCreater.setText(creater.getReal_name());
        } else if (requestCode == 3 && resultCode == 3) {//选择上级部门
            parent = data.getParcelableExtra("parent");
            tvparentDepartment.setText(parent.getD_name());
        }
    }

    private void showDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete_department);
        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        tvTitle.setText("如果该部门有部门群，则部门群的 聊天信息和文件都会删除");
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvSure = dialog.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Map<String,Object> map = new HashMap<>();
                map.put("departmentId",departmentBean.getDid());
                map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
                presenter.deleteDepartment(map);
            }
        });
        dialog.show();
    }
}
