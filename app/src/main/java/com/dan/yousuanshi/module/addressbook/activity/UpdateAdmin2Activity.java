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
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.UpdateAdmin2ActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.UpdateAdmin2ActivityView;
import com.dan.yousuanshi.module.chat.activity.SharedBusinessCardActivity;
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateAdmin2Activity extends BaseActivity<UpdateAdmin2ActivityPresenter> implements UpdateAdmin2ActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.tv_new_admin)
    TextView tvNewAdmin;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.ll_update_admin)
    LinearLayout llUpdateAdmin;
    @BindView(R.id.tv_success)
    TextView tvSuccess;
    @BindView(R.id.ll_success)
    LinearLayout llSuccess;

    private SendCodeBean sendCodeBean = new SendCodeBean();
    private Timer timer;
    private TeamPeopleBean teamPeopleBean;
    private String sign;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_admin2;
    }

    @Nullable
    @Override
    protected UpdateAdmin2ActivityPresenter initPresenter() {
        return new UpdateAdmin2ActivityPresenter();
    }

    @Override
    protected void initView() {
        teamPeopleBean = getIntent().getParcelableExtra("data");
        if (teamPeopleBean != null) {
            Map<String, String> map = new HashMap<>();
            map.put("uid", String.valueOf(teamPeopleBean.getUser_id()));
            presenter.getUserInfoById(map);
        }
        sign = getIntent().getStringExtra("sign");
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getCodeSuccess(int code, SendCodeBean data) {
        sendCodeBean.setSmsId(data.getSmsId());
    }

    @Override
    public void getCodeFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取验证码失败：" + code + msg);
        Log.e("UpdateAdmin", "获取验证码失败：" + code + msg);
    }

    @Override
    public void getUserByIdSuccess(int code, QueryUserBean queryUserBean) {
        tvNewAdmin.setText(queryUserBean.getReal_name());
        etName.setText(queryUserBean.getReal_name());
        etPhone.setText(queryUserBean.getUser_tel());
        Map<String, Object> map1 = new HashMap<>();
        map1.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map1.put("step", 2);
        map1.put("userId", teamPeopleBean.getUser_id());
        presenter.getCode(map1);
        startTimer();
    }

    @Override
    public void getUserByIdFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取用户信息失败：" + code + msg);
        Log.e("UpdateAdmin", "获取用户信息失败：" + code + msg);
    }

    @Override
    public void updateAdminSuccess(int code, List list) {
        llUpdateAdmin.setVisibility(View.GONE);
        llSuccess.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateAdminFailed(int code, String msg) {
        ToastUtils.showToast(this, "更换管理员失败：" + code + msg);
        Log.e("UpdateAdmin", "更换管理员失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.ll_new_admin, R.id.tv_get_code, R.id.tv_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                MyApplication.addActivity(this);
                MyApplication.clearActivity();
                break;
            case R.id.ll_submit:
                if(StringUtils.isEmpty(etCode.getText().toString())){
                    ToastUtils.showToast(this,"请输入验证码！");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
                map.put("smsCode",etCode.getText().toString());
                map.put("smsId",sendCodeBean.getSmsId());
                map.put("sign",sign);
                map.put("userId",teamPeopleBean.getUser_id());
                presenter.updateAdmin(map);
                break;
            case R.id.ll_new_admin:
                Intent intent = new Intent(this, SharedBusinessCardActivity.class);
                intent.putExtra("type", 6);
                startActivityForResult(intent, 1);
                finish();
                break;
            case R.id.tv_get_code:
                Map<String, Object> map1 = new HashMap<>();
                map1.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
                map1.put("step", 2);
                map1.put("userId", teamPeopleBean.getUser_id());
                presenter.getCode(map1);
                startTimer();
                etCode.setText("");
                break;
            case R.id.tv_success:
                MyApplication.addActivity(this);
                MyApplication.clearActivity();
                break;
        }
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            int time = 30;

            @Override
            public void run() {
                if (time == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvGetCode.setText("重新获取");
                            tvGetCode.setEnabled(true);
                        }
                    });
                    this.cancel();
                    return;
                }
                time--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvGetCode.setEnabled(false);
                        tvGetCode.setText(time + "秒后重发");
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
