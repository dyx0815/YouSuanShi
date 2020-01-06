package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleInfo;
import com.dan.yousuanshi.module.addressbook.presenter.EditTeamPeopleActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.EditTeamPeopleActivityView;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class EditTeamPeopleActivity extends BaseActivity<EditTeamPeopleActivityPresenter> implements EditTeamPeopleActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_position)
    EditText etPosition;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_delete_people)
    TextView tvDeletePeople;

    private Dialog dialog;
    private int id;
    private TeamPeopleInfo teamPeopleInfo;
    private DepartmentBean departmentBean;
    private String date;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_team_people;
    }

    @Nullable
    @Override
    protected EditTeamPeopleActivityPresenter initPresenter() {
        return new EditTeamPeopleActivityPresenter();
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra("id",0);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("companyUserId",id);
        presenter.teamPeopleInfo(map);
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_delete_department);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);
            tvTitle.setText("删除后，选中员工将从组织架构中 移除，请慎重操作");
            TextView tvSure = dialog.findViewById(R.id.tv_sure);
            TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    List<Integer> leaveArray = new ArrayList<>();
                    leaveArray.add(teamPeopleInfo.getUser_id());
                    Map<String,Object> map = new HashMap<>();
                    map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
                    map.put("leaveArray",new Gson().toJson(leaveArray));
                    presenter.deleteTeamPeople(map);
                }
            });
        }
        dialog.show();
    }

    @Override
    public void getTeamPeopleInfoSuccess(int code, TeamPeopleInfo data) {
        teamPeopleInfo = data;
        etName.setText(data.getReal_name());
        etPhone.setText(data.getUser_tel());
        if(!StringUtils.isEmpty(data.getOffice())){
            etPosition.setText(data.getOffice());
        }
        for(TeamPeopleInfo.DepartmentIdListBean item : data.getDepartmentIdList()){
            tvDepartment.setText(item.getGroup_name()+"");
        }
        tvDate.setText(data.getJoin_time());
        if(!StringUtils.isEmpty(data.getExplain())){
            etRemark.setText(data.getExplain());
        }
    }

    @Override
    public void getTeamPeopleInfoFailed(int code, String msg) {
        ToastUtils.showToast(this,"获取员工详情失败："+code+msg);
        Log.e("EditTeamPeople","获取员工详情失败："+code+msg);
    }

    @Override
    public void deleteTeamPeopleSuccess(int code, List list) {
        finish();
    }

    @Override
    public void deleteTeamPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this,"删除员工失败："+code+msg);
        Log.e("EditTeamPeople","删除员工失败："+code+msg);
    }

    @Override
    public void editTeamPeopleSuccess(int code, List list) {
        finish();
    }

    @Override
    public void editTeamPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this,"编辑员工信息失败："+code+msg);
        Log.e("EditTeamPeople","编辑员工信息失败："+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit,R.id.tv_delete_people,R.id.ll_department,R.id.ll_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                Map<String,Object> map = new HashMap<>();
                map.put("personnelId",teamPeopleInfo.getUser_id());
                map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
                if(!StringUtils.isEmpty(etPosition.getText().toString())){
                    map.put("office",etPosition.getText().toString());
                }
                if(!StringUtils.isEmpty(date)){
                    map.put("joinTime",tvDate.getText().toString());
                }
                if(!StringUtils.isEmpty(etRemark.getText().toString())){
                    map.put("remark",etRemark.getText().toString());
                }
                if(departmentBean != null){
                    List<Integer> oldDepartmentId = new ArrayList<>();
                    for(TeamPeopleInfo.DepartmentIdListBean item : teamPeopleInfo.getDepartmentIdList()){
                        oldDepartmentId.add(item.getGroup_id());
                    }
                    map.put("OlddepartmentId",new Gson().toJson(oldDepartmentId));
                    List<Integer> departmentId = new ArrayList<>();
                    departmentId.add(departmentBean.getDid());
                    map.put("departmentId",new Gson().toJson(departmentId));
                }
                presenter.editTeamPeople(map);
                break;
            case R.id.tv_delete_people:
                showDialog();
                break;
            case R.id.ll_department:
                Intent intent = new Intent(this,DepartmentManagerActivity.class);
                intent.putExtra("type",2);
                startActivityForResult(intent,1);
                break;
            case R.id.ll_date:
                showDatePickerDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 3){
            departmentBean = data.getParcelableExtra("parent");
            tvDepartment.setText(departmentBean.getD_name());
        }
    }

    private void showDatePickerDialog() {
        DatePicker picker = new DatePicker(this);
        picker.setTextColor(getColor(R.color.color_F99E05));
        picker.setLabelTextColor(getColor(R.color.color_F99E05));
        picker.setCancelTextColor(getColor(R.color.color_F99E05));
        picker.setSubmitTextColor(getColor(R.color.color_F99E05));
        picker.setDividerColor(getColor(R.color.color_F99E05));
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setCycleDisable(false);
        picker.setTopLineVisible(false);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        Calendar calendar = Calendar.getInstance();
        picker.setRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setRangeStart(1949, 1, 1);
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                date = year + "-" + month + "-" + day;
                tvDate.setText(date);
            }
        });
//        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
//            @Override
//            public void onYearWheeled(int index, String year) {
//                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
//            }
//
//            @Override
//            public void onMonthWheeled(int index, String month) {
//                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
//            }
//
//            @Override
//            public void onDayWheeled(int index, String day) {
//                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
//            }
//        });
        picker.show();
    }
}
