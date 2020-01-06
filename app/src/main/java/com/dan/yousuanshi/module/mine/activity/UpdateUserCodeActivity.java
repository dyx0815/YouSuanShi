package com.dan.yousuanshi.module.mine.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.mine.presenter.UpdateUserCodeActivityPresenter;
import com.dan.yousuanshi.module.mine.view.UpdateUserCodeActivityView;
import com.dan.yousuanshi.utils.PhoneCode;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateUserCodeActivity extends BaseActivity<UpdateUserCodeActivityPresenter> implements UpdateUserCodeActivityView {

    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.pc_code)
    PhoneCode pcCode;


    private String phone;
    private String smsId;
    private String sign;
    private Timer timer;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(666);
        }
    };
    private int type;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 666) {
                Map<String, String> map = new HashMap<>();
                map.put("smsCode", pcCode.getPhoneCode());
                map.put("smsId", smsId);
                if(type == 1){
                    map.put("mobile",phone);
                    map.put("sign",sign);
                    presenter.updatePhoneCheckCode2(map);
                }else{
                    presenter.updatePhoneCheckCode(map);
                }
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_code;
    }

    @Nullable
    @Override
    protected UpdateUserCodeActivityPresenter initPresenter() {
        return new UpdateUserCodeActivityPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        smsId = intent.getStringExtra("smsId");
        sign = intent.getStringExtra("sign");
        type = intent.getIntExtra("type",0);
        tvPhone.setText("+86 " + phone);
    }

    @Override
    protected void loadData() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        pcCode.showSoftInput();
        pcCode.setRunnable(runnable, handler);
    }

    @Override
    public void sendCodeSuccess(int code, SendCodeBean sendCodeBean) {
        ToastUtils.showToast(this, "重新发送验证码成功");
    }

    @Override
    public void sendCodeFailed(int code, String message) {
        ToastUtils.showToast(this, "code:" + code + "\t" + message);
    }

    @Override
    public void updatePhoneCheckCodeSuccess(int code, CheckCodeBean checkCodeBean) {
        Log.e("UpdateUserCodeActivity", "验证成功：" + code);
        Intent intent = new Intent(this, UpdateUserPhoneNumberActivity.class);
        intent.putExtra("sign", checkCodeBean.getSign());
        startActivity(intent);
        finish();
    }

    @Override
    public void updatePhoneCheckCodeFailed(int code, String msg) {
        ToastUtils.showToast(this, "code:" + code + "\t" + msg);
    }

    @Override
    public void updatePhoneCheckCodeSuccess2(int code, List data) {
        Intent intent = new Intent(this, UpdateUserPhoneActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
        finish();
    }

    @Override
    public void updatePhoneCheckCodeFailed2(int code, String msg) {
        ToastUtils.showToast(this, "code:" + code + "\t" + msg);
        Log.e("UpdateUserCodeActivity","code:" + code + "\t" + msg);
    }


    @OnClick({R.id.ll_back, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_send:
                startTimer();
                presenter.sendCode();
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
                            tvSend.setText("重新获取");
                            tvSend.setEnabled(true);
                        }
                    });
                    this.cancel();
                    return;
                }
                time--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSend.setEnabled(false);
                        tvSend.setText(time + "秒后重发");
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
