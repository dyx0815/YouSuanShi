package com.dan.yousuanshi.module.login.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.http.PublicHeadersInterceptor;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.login.presenter.LoginPresenter;
import com.dan.yousuanshi.module.login.view.LoginView;
import com.dan.yousuanshi.module.main.activity.MainActivity;
import com.dan.yousuanshi.utils.MD5Utils;
import com.dan.yousuanshi.utils.SPUtils;
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

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.iv_al_logo)
    ImageView ivAlLogo;
    @BindView(R.id.et_al_phone)
    EditText etAlPhone;
    @BindView(R.id.ll_al_phone)
    LinearLayout llAlPhone;
    @BindView(R.id.et_al_pwd)
    EditText etAlPwd;
    @BindView(R.id.iv_al_look)
    ImageView ivAlLook;
    @BindView(R.id.ll_al_pwd)
    LinearLayout llAlPwd;
    @BindView(R.id.btn_al_login)
    Button btnAlLogin;
    @BindView(R.id.tv_al_number_login)
    TextView tvAlNumberLogin;
    @BindView(R.id.iv_al_wechat_login)
    ImageView ivAlWechatLogin;
    @BindView(R.id.iv_al_alipay_login)
    ImageView ivAlAlipayLogin;
    @BindView(R.id.iv_al_phone_icon)
    ImageView ivAlPhoneIcon;
    @BindView(R.id.tv_al_get_code)
    TextView tvAlGetCode;
    @BindView(R.id.tv_al_forget_pwd)
    TextView tvAlForgetPwd;
    @BindView(R.id.iv_al_unlook)
    ImageView ivAlUnLook;

    private final String[] dialogItem = {"工号登录", "手机号登录", "注册账号"};//更多菜单选项
    private int loginType = 4;//登录类型 1,手机号验证码登录,2.工号密码登录,3,身份证号密码登录,4,手机号密码登录,其他:都返回错误
    private int tvType = 4;
    private AlertDialog.Builder moreDialog;
    private Timer timer;
    private String smsId;
    private int isShow;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Nullable
    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        etAlPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(loginType != 1){
                    if (ivAlUnLook.getVisibility() != View.VISIBLE) {
                        ivAlLook.setVisibility(View.VISIBLE);
                    }else{
                        ivAlLook.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    protected void loadData() {
        int logintype = getIntent().getIntExtra("loginType", 4);
        if (logintype == 2) {
            etAlPhone.setHint("请输入工号");
            ivAlPhoneIcon.setImageResource(R.mipmap.icon_work_number);
            loginType = 2;
        }
    }

    @OnClick({R.id.tv_al_more, R.id.tv_al_number_login, R.id.tv_al_forget_pwd, R.id.btn_al_login
            , R.id.tv_al_get_code, R.id.iv_al_look, R.id.iv_al_unlook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_al_more:
                showMoreDialog();
                break;
            case R.id.tv_al_number_login://密码登录4,验证码登录1
                if (tvType == 4) {
                    tvAlNumberLogin.setText("密码登录");
                    etAlPwd.setHint("请输入验证码");
                    tvAlGetCode.setVisibility(View.VISIBLE);
                    ivAlLook.setVisibility(View.GONE);
                    tvType = 1;
                } else if (tvType == 1) {
                    tvAlNumberLogin.setText("验证码登录");
                    etAlPwd.setHint("请输入密码");
                    tvAlGetCode.setVisibility(View.GONE);
                    ivAlLook.setVisibility(View.VISIBLE);
                    tvType = 4;
                }
                loginType = tvType;
                break;
            case R.id.tv_al_forget_pwd://忘记密码
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.btn_al_login://登录按钮
                if (!StringUtils.isTrimEmpty(etAlPhone.getText().toString()) && !StringUtils.isTrimEmpty(etAlPwd.getText().toString())) {
                    Map<String,String> map = new HashMap<>();
                    map.put("accountType",String.valueOf(loginType));
                    map.put("account",etAlPhone.getText().toString());
                    if(loginType == 1){
                        map.put("smsCode",etAlPwd.getText().toString());
                        map.put("smsId",smsId);
                    }else{
                        map.put("passWord", MD5Utils.getMd5Code(etAlPwd.getText().toString()));
                    }
                    presenter.login(map);
                } else {
                    if (loginType == 2) {
                        ToastUtils.showToast(this, "请输入工号和密码");
                    } else if (loginType == 4) {
                        ToastUtils.showToast(this, "请输入手机号和密码");
                    }
                }
                break;
            case R.id.tv_al_get_code://发送验证码
                if(!StringUtils.isEmpty(etAlPhone.getText().toString())){
                    startTimer();
                    Map<String,String> map = new HashMap<>();
                    map.put("mobile",etAlPhone.getText().toString());
                    map.put("sendType","1");
                    presenter.sendCode(map);
                } else{
                    ToastUtils.showToast(this,"请输入手机号");
                }
                break;
            case R.id.iv_al_look://查看密码
                etAlPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ivAlLook.setVisibility(View.GONE);
                ivAlUnLook.setVisibility(View.VISIBLE);
                etAlPwd.setSelection(etAlPwd.getText().toString().length());
                break;
            case R.id.iv_al_unlook://不查看密码
                etAlPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                ivAlLook.setVisibility(View.VISIBLE);
                ivAlUnLook.setVisibility(View.GONE);
                etAlPwd.setSelection(etAlPwd.getText().toString().length());
                break;
        }
    }

    public void showMoreDialog() {
        moreDialog = new AlertDialog.Builder(this);
        moreDialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    //工号登录
                    case 0:
                        etAlPhone.setHint("请输入工号");
                        ivAlPhoneIcon.setImageResource(R.mipmap.icon_work_number);
                        loginType = 2;
                        break;
                    //手机号登录
                    case 1:
                        etAlPhone.setHint("请输入手机号");
                        ivAlPhoneIcon.setImageResource(R.mipmap.icon_phone);
                        loginType = 4;
                        break;
                    //注册账号
                    case 2:
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                        break;
                }
            }
        });
        moreDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        moreDialog.show();
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
                            tvAlGetCode.setText("重新获取");
                            tvAlGetCode.setEnabled(true);
                        }
                    });
                    this.cancel();
                    return;
                }
                time--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvAlGetCode.setEnabled(false);
                        tvAlGetCode.setText(time + "秒后重发");
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    public void loginSuccess(int code, LoginBean loginBean) {
        if(code == 0){
            isShow = loginBean.getIsshow();
            PublicHeadersInterceptor.updateToken(loginBean.getToken());
            SPUtils.getInstance().save("KEY_WORD_TOKEN",loginBean.getToken());
            presenter.getUser();
        }
    }

    @Override
    public void loginFailed(int code, String message) {
        ToastUtils.showToast(this,message);
    }

    @Override
    public void getUserSuccess(int code, UserBean userBean) {
        userBean.setIsShow(isShow);
        UserUtils.getInstance().login(userBean);
        ToastUtils.showToast(this,"登录成功");
        presenter.chattable();
        if(isShow == 1){
            startActivity(new Intent(this, WriteDataActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }else{
            startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }

    @Override
    public void getUserFailed(int code, String message) {
        ToastUtils.showToast(this,message);
    }

    @Override
    public void sendCodeSuccess(int code, SendCodeBean sendCodeBean) {
        if(code == 0){
            smsId = sendCodeBean.getSmsId();
            ToastUtils.showToast(this,"发送验证码成功");
        }
    }

    @Override
    public void sendCodeFailed(int code, String message) {
        ToastUtils.showToast(this,message);
    }

    @Override
    public void chattableSuccess(int code, List list) {
        Log.e("Chat",code+"成功");
    }

    @Override
    public void chattableFailed(int code, String message) {
        ToastUtils.showToast(this,message);
        Log.e("NetWork",message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}
