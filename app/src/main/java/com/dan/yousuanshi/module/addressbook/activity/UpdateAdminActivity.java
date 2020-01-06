package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.presenter.UpdateAdminActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.UpdateAdminActivityView;
import com.dan.yousuanshi.module.chat.activity.SharedBusinessCardActivity;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateAdminActivity extends BaseActivity<UpdateAdminActivityPresenter> implements UpdateAdminActivityView {

    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;

    private Timer timer;
    private SendCodeBean sendCodeBean = new SendCodeBean();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_admin;
    }

    @Nullable
    @Override
    protected UpdateAdminActivityPresenter initPresenter() {
        return new UpdateAdminActivityPresenter();
    }

    @Override
    protected void initView() {
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("step",1);
        map.put("userId",UserUtils.getInstance().getUserBean().getUser_id());
        presenter.getCode(map);
        startTimer();
        tvPhone.setText(UserUtils.getInstance().getUserBean().getUser_tel().replaceFirst(UserUtils.getInstance().getUserBean().getUser_tel().substring(3, 7), "****"));
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
        ToastUtils.showToast(this,"获取验证码失败："+code+msg);
        Log.e("UpdateAdmin","获取验证码失败："+code+msg);
    }

    @Override
    public void checkCodeSuccess(int code, CheckCodeBean data) {
        Intent intent = new Intent(this, SharedBusinessCardActivity.class);
        intent.putExtra("type",5);
        intent.putExtra("sign",data.getSign());
        startActivity(intent);
        finish();
    }

    @Override
    public void checkCodeFailed(int code, String msg) {
        ToastUtils.showToast(this,"验证验证码失败："+code+msg);
        Log.e("UpdateAdmin","验证验证码失败："+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.tv_get_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
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
                presenter.checkCode(map);
                break;
            case R.id.tv_get_code:
                Map<String,Object> map1 = new HashMap<>();
                map1.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
                map1.put("step",1);
                map1.put("userId",UserUtils.getInstance().getUserBean().getUser_id());
                presenter.getCode(map1);
                startTimer();
                etCode.setText("");
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
