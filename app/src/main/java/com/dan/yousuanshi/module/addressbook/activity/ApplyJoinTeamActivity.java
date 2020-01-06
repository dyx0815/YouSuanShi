package com.dan.yousuanshi.module.addressbook.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.adapter.DiyApplicationSettingAdapter;
import com.dan.yousuanshi.module.addressbook.bean.DiyApplicationSettingBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamApplyListBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamQrCodeBean;
import com.dan.yousuanshi.module.addressbook.presenter.ApplyJoinTeamActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.ApplyJoinTeamActivityView;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class ApplyJoinTeamActivity extends BaseActivity<ApplyJoinTeamActivityPresenter> implements ApplyJoinTeamActivityView {

    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_apply_reason)
    EditText etApplyReason;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_leave_message)
    EditText etLeaveMessage;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rl_submit)
    RelativeLayout rlSubmit;
    @BindView(R.id.rl_success)
    RelativeLayout rlSuccess;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.rl_scan)
    RelativeLayout rlScan;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_is_team_people)
    LinearLayout llIsTeamPeople;

    private int cId;
    private List<DiyApplicationSettingBean> diyList = new ArrayList<>();
    private DiyApplicationSettingAdapter adapter;
    private int type = 0; //为2时为扫一扫加入公司

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_join_team;
    }

    @Nullable
    @Override
    protected ApplyJoinTeamActivityPresenter initPresenter() {
        return new ApplyJoinTeamActivityPresenter();
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this,getColor(R.color.color_FC9F44),0);
    }

    @Override
    protected void initView() {
        cId = getIntent().getIntExtra("data",0);
        type = getIntent().getIntExtra("type",0);
        if(type == 2){//为扫一扫加入公司时
            rlSubmit.setVisibility(View.GONE);
            Map<String,Object> map = new HashMap<>();
            map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
            presenter.getTeamQrcode(map);
            rlScan.setVisibility(View.VISIBLE);
        }else{//为搜索入口进入时
            Map<String,Object> map = new HashMap<>();
            map.put("companyId",cId);
            presenter.getTeamApplyList(map);
            tvName.setText(UserUtils.getInstance().getUserBean().getReal_name());
            tvPhone.setText(UserUtils.getInstance().getUserBean().getUser_tel());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new DiyApplicationSettingAdapter(this,diyList,2);
            adapter.setOnItemClick(new DiyApplicationSettingAdapter.OnItemClick() {
                @Override
                public void inputText(int position, String text) {
                    diyList.get(position).setValue(text);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void applyJoinTeamSuccess(int code, BaseBean data) {
        rlSubmit.setVisibility(View.GONE);
        rlSuccess.setVisibility(View.VISIBLE);
    }

    @Override
    public void applyJoinTeamFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
        Log.e("ApplyJoinTeam","申请加入企业失败："+code+msg);
    }

    @Override
    public void getTeamApplyListSuccess(int code, TeamApplyListBean data) {
        tvTeamName.setText(data.getC_name());
        diyList.addAll(data.getDiyMessage());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getTeamApplyListFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
        if(code == 2){//已经是企业员工
            rlSubmit.setVisibility(View.GONE);
            llIsTeamPeople.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getTeamQrCodeSuccess(int code, TeamQrCodeBean data) {
        ivScan.setImageBitmap(CodeCreator.createQRCode(data.getUrl(), 400, 400,null));
    }

    @Override
    public void getTeamQrCodeFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_yes,R.id.tv_submit,R.id.tv_return_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_yes:
                MyApplication.addActivity(this);
                MyApplication.clearActivity();
                break;
            case R.id.tv_submit://提交申请
                Map<String,String> map = new HashMap<>();
                map.put("companyId",String.valueOf(cId));
                map.put("applyReason",etApplyReason.getText().toString());
                if(!StringUtils.isEmpty(etLeaveMessage.getText().toString())){
                    map.put("leaveMessage",etLeaveMessage.getText().toString());
                }
                for(DiyApplicationSettingBean item : diyList){
                    if(item.getIsRequired() == 1 && StringUtils.isEmpty(item.getValue())){
                        ToastUtils.showToast(this,"请输入"+item.getLabel()+"！");
                        return;
                    }
                }
                map.put("diyMessage",new Gson().toJson(diyList));
                presenter.applyJoinTeam(map);
                break;
            case R.id.tv_return_home:
                finish();
                break;
        }
    }
}
