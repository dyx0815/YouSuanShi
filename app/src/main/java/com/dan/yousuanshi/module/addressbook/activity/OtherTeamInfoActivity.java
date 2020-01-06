package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.presenter.OtherTeamInfoActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.OtherTeamInfoActivityView;
import com.dan.yousuanshi.module.chat.adapter.SharedDepartmentAdapter;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class OtherTeamInfoActivity extends BaseActivity<OtherTeamInfoActivityPresenter> implements OtherTeamInfoActivityView {

    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.iv_team_proof)
    ImageView ivTeamProof;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_more)
    LinearLayout llMore;

    private PopupWindow popupWindow;
    private Dialog dialog;
    private Dialog chooseDialog;
    private MyTeamBean myTeamBean;
    private SharedDepartmentAdapter adapter;
    private List<DepartmentBean> departmentList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_team_info;
    }

    @Nullable
    @Override
    protected OtherTeamInfoActivityPresenter initPresenter() {
        return new OtherTeamInfoActivityPresenter();
    }

    @Override
    protected void initView() {
        myTeamBean = getIntent().getParcelableExtra("team");
        tvTeamName.setText(myTeamBean.getCompanyName());
        Map<String,String> map = new HashMap<>();
        map.put("companyId",String.valueOf(myTeamBean.getCompanyId()));
        map.put("parentId",String.valueOf(0));
        presenter.getDepartment(map);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SharedDepartmentAdapter(this,departmentList);
        adapter.setOnItemClick(new SharedDepartmentAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                Map<String,String> map = new HashMap<>();
                map.put("companyId",String.valueOf(myTeamBean.getCompanyId()));
                map.put("parentId",String.valueOf(departmentList.get(position).getDid()));
                presenter.getDepartment(map);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_more:
                showPopupwindow();
                break;
        }
    }

    @Override
    public void getDepartmentSuccess(int code, List<DepartmentBean> data) {
        departmentList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDepartmentFailed(int code, String msg) {
        ToastUtils.showToast(this,"获取部门失败："+code+msg);
        Log.e("OtherTeamInfo","获取部门失败："+code+msg);
    }

    @Override
    public void exitTeamSuccess(int code, List data) {
        finish();
    }

    @Override
    public void exitTeamFailed(int code, String msg) {
        ToastUtils.showToast(this,"退出企业："+code+msg);
        Log.e("OtherTeamInfo","退出企业："+code+msg);
    }

    @Override
    public void setFirstTeamSuccess(int code, List list) {
        finish();
    }

    @Override
    public void setFirstTeamFailed(int code, String msg) {
        ToastUtils.showToast(this,"设置主企业："+code+msg);
        Log.e("OtherTeamInfo","设置主企业："+code+msg);
    }

    private void showPopupwindow(){
        if(popupWindow == null){
            View view = LayoutInflater.from(this).inflate(R.layout.layout_other_team_window,null);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setTouchable(true);//设置可以触摸
            popupWindow.setOutsideTouchable(true);//点击外面可以隐藏window
            ColorDrawable dw = new ColorDrawable(0x00000000);//设置背景为透明
            popupWindow.setBackgroundDrawable(dw);
            TextView tvExit = view.findViewById(R.id.tv_exit);
            TextView tvSet = view.findViewById(R.id.tv_set);
            tvExit.setOnClickListener(new View.OnClickListener() {//退出企业
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    showDialog();
                }
            });
            tvSet.setOnClickListener(new View.OnClickListener() {//设置主企业
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    showChooseDialog();
                }
            });
        }
        popupWindow.showAsDropDown(llMore);
    }

    private void showDialog(){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_exit_team);
            dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> map = new HashMap<>();
                    map.put("companyId",String.valueOf(myTeamBean.getCompanyId()));
                    presenter.exitTeam(map);
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void showChooseDialog(){
        if(chooseDialog == null){
            chooseDialog = new Dialog(this);
            chooseDialog.setContentView(R.layout.dialog_set_first_team);
            chooseDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseDialog.dismiss();
                }
            });
            chooseDialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseDialog.dismiss();
                    Map<String,String> map = new HashMap<>();
                    map.put("companyId",String.valueOf(myTeamBean.getCompanyId()));
                    presenter.setFirstTeam(map);
                }
            });
        }
        chooseDialog.show();
    }
}
