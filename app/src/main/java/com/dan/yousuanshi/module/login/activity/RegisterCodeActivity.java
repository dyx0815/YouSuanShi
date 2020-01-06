package com.dan.yousuanshi.module.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.login.presenter.RegisterPresenter;
import com.dan.yousuanshi.module.login.view.RegisterView;
import com.dan.yousuanshi.utils.MyHanlder;
import com.dan.yousuanshi.utils.PhoneCode;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册验证验证码类
 *
 * @author dyx
 */
public class RegisterCodeActivity extends BaseActivity<RegisterPresenter> implements RegisterView {

    @BindView(R.id.ll_arc_title)
    LinearLayout llArcTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_arc_code_title)
    TextView tvArcCodeTitle;
    @BindView(R.id.tv_arc_phone)
    TextView tvArcPhone;
    @BindView(R.id.tv_arc_send)
    TextView tvArcSend;
    @BindView(R.id.pc_arc_code)
    PhoneCode pcArcCode;

    private String phoneNum;
    private int type;//判断是注册还是忘记密码 0.注册 1.忘记密码
    private Timer timer;
    private String smsId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_code;
    }

    @Nullable
    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");
        type = intent.getIntExtra("type",0);
        smsId = intent.getStringExtra("smsId");
        if(type == 1){
            tvTitle.setText("忘记密码");
        }
        if(!StringUtils.isTrimEmpty(phoneNum)){
            tvArcPhone.setText("+86\t"+phoneNum);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        pcArcCode.showSoftInput();
        pcArcCode.setRunnable(runnable,handler);
    }


    @Override
    protected void loadData() {

    }


    @OnClick({R.id.tv_arc_send,R.id.ll_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_arc_send:
                startTimer();
                Map<String,String> map = new HashMap<>();
                map.put("mobile",phoneNum);
                if(type == 0){
                    map.put("sendType","2");
                }else if (type == 1){
                    map.put("sendType","3");
                }
                presenter.sendCode(map);
                break;
            case R.id.ll_back:
                finish();
                break;
        }
    }

    public void startTimer(){
        if(timer == null){
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
                            tvArcSend.setText("重新获取");
                            tvArcSend.setEnabled(true);
                        }
                    });
                    this.cancel();
                    return;
                }
                time--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvArcSend.setEnabled(false);
                        tvArcSend.setText(time + "秒后重发");
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyHanlder.getInstance().postDelayed(()->hideInput(this,pcArcCode),50);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }
    }

    @Override
    public void sendCodeSuccess(int code, SendCodeBean sendCodeBean) {
        ToastUtils.showToast(this,"重新发送验证码成功");
    }

    @Override
    public void sendCodeFailed(int code, String message) {
        ToastUtils.showToast(this,"code:"+code+"\t"+message);
    }

    @Override
    public void registerSuccess(int code, LoginBean loginBean) {

    }

    @Override
    public void registerFailed(int code, String message) {

    }

    @Override
    public void checkCodeSuccess(int code, CheckCodeBean checkCodeBean) {
        if(code == 0){
            Intent intent = new Intent(this,SetPasswordActivity.class);
            intent.putExtra("type",type);
            intent.putExtra("phoneNum",phoneNum);
            intent.putExtra("sign",checkCodeBean.getSign());
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void checkCodeFailed(int code, String message) {

    }

    @Override
    public void getUserInfoSuccess(int code, UserBean userBean) {

    }

    @Override
    public void getUserInfoFailed(int code, String message) {

    }

    @Override
    public void forgetPwdSuccess(int code, LoginBean loginBean) {

    }

    @Override
    public void forgetPwdFailed(int code, String message) {

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(666);
        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 666){
                Map<String,String> map = new HashMap<>();
                map.put("mobile",phoneNum);
                map.put("smsCode",pcArcCode.getPhoneCode());
                map.put("smsId",smsId);
                presenter.checkCode(map);
            }
        }
    };
    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
