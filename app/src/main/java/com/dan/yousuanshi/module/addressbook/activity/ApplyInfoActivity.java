package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.DiyApplicationSettingAdapter;
import com.dan.yousuanshi.module.addressbook.bean.DiyApplicationSettingBean;
import com.dan.yousuanshi.module.addressbook.bean.MyApplyRecordBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamNewApplyBean;
import com.dan.yousuanshi.module.addressbook.presenter.ApplyInfoActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.ApplyInfoActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplyInfoActivity extends BaseActivity<ApplyInfoActivityPresenter> implements ApplyInfoActivityView {

    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_apply_text_title)
    TextView tvApplyTextTitle;
    @BindView(R.id.tv_apply_text)
    TextView tvApplyText;
    @BindView(R.id.tv_phone_title)
    TextView tvPhoneTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_leave_message_title)
    TextView tvLeaveMessageTitle;
    @BindView(R.id.tv_leave_message)
    TextView tvLeaveMessage;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_team_name_title)
    TextView tvTeamNameTitle;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;

    private TeamNewApplyBean.DataBean data;
    private MyApplyRecordBean.DataBean data2;
    private DiyApplicationSettingAdapter adapter;
    private List<DiyApplicationSettingBean> diyList = new ArrayList<>();
    private int type = 0;//2.申请记录详情

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_info;
    }

    @Nullable
    @Override
    protected ApplyInfoActivityPresenter initPresenter() {
        return new ApplyInfoActivityPresenter();
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type",0);
        if(type == 2){//申请记录详情
            llInfo.setVisibility(View.GONE);
            tvAgree.setVisibility(View.GONE);
            data2 = getIntent().getParcelableExtra("data");
            tvTeamNameTitle.setVisibility(View.VISIBLE);
            tvTeamName.setText(data2.getC_name());
            tvTeamName.setVisibility(View.VISIBLE);
            tvApplyText.setText(data2.getSend_msg());
            Glide.with(this).load(data2.getUser_portrait()).into(rivHeadIcon);
            tvName.setText(data2.getReal_name());
            tvPhone.setText(data2.getUser_tel());
            tvLeaveMessage.setText(data2.getExplain());
            diyList.addAll(data2.getDiy_message());
        }else{//新成员申请详情
            data = getIntent().getParcelableExtra("data");
            if(data.getIs_pass() == 1){
                tvAgree.setText("已同意");
                tvAgree.setEnabled(false);
            }else{
                tvAgree.setText("同意");
                tvAgree.setEnabled(true);
            }
            tvApplyText.setText(data.getSend_msg());
            tvName.setText(data.getReal_name());
            Glide.with(this).load(data.getUser_portrait()).into(rivHeadIcon);
            tvPhone.setText(data.getUser_tel());
            tvLeaveMessage.setText(data.getExplain());
            diyList.addAll(data.getDiy_message());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiyApplicationSettingAdapter(this,diyList,3);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void agreeApplySuccess(int code, List list) {
        tvAgree.setText("已同意");
        tvAgree.setEnabled(false);
    }

    @Override
    public void agreeApplyFailed(int code, String msg) {
        ToastUtils.showToast(this, "同意申请失败：" + code + msg);
        Log.e("TeamHomeActivity", "同意申请失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_agree,R.id.ll_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_agree:
                Map<String,String> map = new HashMap<>();
                map.put("companyId",String.valueOf(data.getCid()));
                map.put("isPass","1");//1通过 2拒绝
                map.put("applyId",String.valueOf(data.getId()));
                presenter.agreeApply(map);
                break;
            case R.id.ll_info:
                Intent intent = new Intent(this,FriendInfoActivity.class);
                intent.putExtra("data",data.getUserid());
                startActivity(intent);
                break;
        }
    }
}
