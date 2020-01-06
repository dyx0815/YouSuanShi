package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
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
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.adapter.DepartmentAdapter;
import com.dan.yousuanshi.module.addressbook.presenter.TeamStructureActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.TeamStructureActivityView;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.utils.DpToPxUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamStructureActivity extends BaseActivity<TeamStructureActivityPresenter> implements TeamStructureActivityView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    @BindView(R.id.v_util)
    View vUtil;
    @BindView(R.id.ll_more)
    LinearLayout llMore;

    private List<DepartmentBean> departmentList = new ArrayList<>();
    private DepartmentAdapter adapter;
    private List<DepartmentBean> parents = null;
    private int companyId = 0;
    private int parentId = 0;
    private PopupWindow popupWindow;
    private Dialog dialog;
    private Dialog notDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_structure;
    }

    @Nullable
    @Override
    protected TeamStructureActivityPresenter initPresenter() {
        return new TeamStructureActivityPresenter();
    }

    @Override
    protected void initView() {
        MyApplication.addActivity(this);
        companyId = UserUtils.getInstance().getUserBean().getUser_company();
        parents = getIntent().getParcelableArrayListExtra("parent");
        if(parents == null){
            tvTitle.setText(UserUtils.getInstance().getUserBean().getC_name());
        }else{
            vUtil.setVisibility(View.GONE);
            tvTitle.setText(parents.get(parents.size()-1).getD_name());
            parentId = parents.get(parents.size()-1).getDid();
            for(int i = 0;i<parents.size();i++){
                TextView textView = new TextView(this);
                textView.setText(parents.get(i).getD_name());
                textView.setTextSize(15);
                textView.setPadding(DpToPxUtils.dip2px(this,5),0,DpToPxUtils.dip2px(this,5),0);
                if(parents.get(i).getDid() == parentId){
                    textView.setTextColor(getColor(R.color.color_F99E05));
                    llDepartment.addView(textView);
                }else{
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(R.mipmap.icon_address_book_create_right);
                    llDepartment.addView(textView);
                    llDepartment.addView(imageView);
                }
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DepartmentAdapter(this,departmentList);
        adapter.setOnItemClick(new DepartmentAdapter.OnItemClick() {
            @Override
            public void subordinate(DepartmentBean parent) {
                Intent intent = new Intent(TeamStructureActivity.this,TeamStructureActivity.class);
                if(parents == null){
                    parents = new ArrayList<>();
                }
                parents.add(parent);
                intent.putParcelableArrayListExtra("parent", (ArrayList<? extends Parcelable>) parents);
                startActivity(intent);
            }

            @Override
            public void department(DepartmentBean departmentBean) {

            }

            @Override
            public void chooseDepartment(int count) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        Map<String,String> map = new HashMap<>();
        map.put("companyId",String.valueOf(companyId));
        map.put("parentId",String.valueOf(parentId));
        presenter.getDepartment(map);
    }

    @Override
    public void getDepartmentSuccess(int code, List<DepartmentBean> data) {
        if(data.size()>0){
            departmentList.addAll(data);
            adapter.notifyDataSetChanged();
        }else{
            showNotDialog();
        }
    }

    @Override
    public void getDepartmentFailed(int code, String msg) {
        ToastUtils.showToast(this, "请求部门失败：" + code + msg);
        Log.e("TeamStructureActivity", "请求部门失败：" + code + msg);
    }

    @Override
    public void exitTeamSuccess(int code, List data) {
        ToastUtils.showToast(this,"退出企业成功");
        MyApplication.clearActivity();
    }

    @Override
    public void exitTeamFailed(int code, String msg) {
        ToastUtils.showToast(this, "退出企业失败：" + code + msg);
        Log.e("TeamStructureActivity", "退出企业失败：" + code + msg);
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

    private void showPopupwindow(){
        if(popupWindow == null){
            View view = LayoutInflater.from(this).inflate(R.layout.layout_exit_team_window,null);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setTouchable(true);//设置可以触摸
            popupWindow.setOutsideTouchable(true);//点击外面可以隐藏window
            ColorDrawable dw = new ColorDrawable(0x00000000);//设置背景为透明
            popupWindow.setBackgroundDrawable(dw);
            TextView tvExit = view.findViewById(R.id.tv_exit);
            tvExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    showDialog();
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
                    map.put("companyId",String.valueOf(companyId));
                    presenter.exitTeam(map);
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void showNotDialog(){
        if (notDialog == null){
            notDialog = new Dialog(this);
            notDialog.setContentView(R.layout.dialog_not_have_department);
            notDialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notDialog.dismiss();
                    finish();
                }
            });
        }
        notDialog.show();
    }
}
