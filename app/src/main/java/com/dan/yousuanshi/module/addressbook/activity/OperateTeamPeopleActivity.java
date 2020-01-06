package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.ChooseTeamPeopleAdapter;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.OperateTeamPeopleActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.OperateTeamPeopleActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class OperateTeamPeopleActivity extends BaseActivity<OperateTeamPeopleActivityPresenter> implements OperateTeamPeopleActivityView {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_choose_count)
    TextView tvChooseCount;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.ll_sure)
    LinearLayout llSure;
    @BindView(R.id.ll_choose_all)
    LinearLayout llChooseAll;

    private List<TeamPeopleBean> teamPeopleList;
    private ChooseTeamPeopleAdapter adapter;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_operate_team_people;
    }

    @Nullable
    @Override
    protected OperateTeamPeopleActivityPresenter initPresenter() {
        return new OperateTeamPeopleActivityPresenter();
    }

    @Override
    protected void initView() {
        teamPeopleList = getIntent().getParcelableArrayListExtra("data");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChooseTeamPeopleAdapter(this,teamPeopleList);
        adapter.setType(2);
        adapter.setOnItemClick(new ChooseTeamPeopleAdapter.OnItemClick() {
            @Override
            public void onItemClick(int count) {
                if(count>0){
                    tvChooseCount.setText("已选择（"+count+"）人");
                    llSure.setVisibility(View.VISIBLE);
                    llSubmit.setVisibility(View.VISIBLE);
                    llChooseAll.setVisibility(View.GONE);
                }else{
                    llSure.setVisibility(View.GONE);
                    llSubmit.setVisibility(View.GONE);
                    llChooseAll.setVisibility(View.VISIBLE);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.tv_sure,R.id.ll_choose_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_sure:
                showDialog();
                break;
            case R.id.ll_choose_all:
                for(TeamPeopleBean item : teamPeopleList){
                    item.setChecked(true);
                }
                adapter.notifyDataSetChanged();
                llChooseAll.setVisibility(View.GONE);
                llSubmit.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void showDialog(){
        if(dialog == null){
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
                    for(TeamPeopleBean item : adapter.getCheckedPeople()){
                        leaveArray.add(item.getUser_id());
                    }
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
    public void deleteTeamPeopleSuccess(int code, List list) {
        finish();
    }

    @Override
    public void deleteTeamPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this,"批量删除公司成员失败！"+code+msg);
        Log.e("DeleteTeamPeople","批量删除公司成员失败："+code+msg);
    }
}
