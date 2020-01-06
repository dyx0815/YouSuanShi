package com.dan.yousuanshi.module.login.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.http.PublicHeadersInterceptor;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.login.presenter.RegisterPresenter;
import com.dan.yousuanshi.module.login.view.RegisterView;
import com.dan.yousuanshi.module.main.activity.MainActivity;
import com.dan.yousuanshi.utils.MD5Utils;
import com.dan.yousuanshi.utils.MyHanlder;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SetPasswordActivity extends BaseActivity<RegisterPresenter> implements RegisterView {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_asp_title)
    LinearLayout llAspTitle;
    @BindView(R.id.tv_asp_title)
    TextView tvAspTitle;
    @BindView(R.id.tv_asp_pwd_title)
    TextView tvAspPwdTitle;
    @BindView(R.id.et_asp_pwd)
    EditText etAspPwd;
    @BindView(R.id.iv_asp_clear)
    ImageView ivAspClear;
    @BindView(R.id.iv_asp_look)
    ImageView ivAspLook;
    @BindView(R.id.ll_asp_pwd)
    LinearLayout llAspPwd;
    @BindView(R.id.btn_asp_next)
    Button btnAspNext;
    @BindView(R.id.iv_asp_un_look)
    ImageView ivAspUnLook;
    private int type;//判断是注册还是忘记密码 0.注册 1.忘记密码
    private String phoneNum;
    private String sign;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_password;
    }

    @Nullable
    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        phoneNum = intent.getStringExtra("phoneNum");
        sign = intent.getStringExtra("sign");
        etAspPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(etAspPwd.getText().toString())) {
                    ivAspClear.setVisibility(View.VISIBLE);
                    if (ivAspUnLook.getVisibility() != View.VISIBLE) {
                        ivAspLook.setVisibility(View.VISIBLE);
                    } else {
                        ivAspLook.setVisibility(View.GONE);
                    }
                    if (etAspPwd.getText().toString().length() >= 6) {
                        btnAspNext.setEnabled(true);
                    }
                    if (etAspPwd.getText().toString().length() < 6) {
                        btnAspNext.setEnabled(false);
                    }
                } else {
                    ivAspClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etAspPwd.setFocusable(true);
        etAspPwd.setFocusableInTouchMode(true);
        etAspPwd.requestFocus();//获取焦点 光标出现
        MyHanlder.getInstance().postDelayed(()-> showInput(this,etAspPwd),50);
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.iv_asp_clear, R.id.iv_asp_look, R.id.iv_asp_un_look, R.id.btn_asp_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_asp_clear://清空输入框
                etAspPwd.setText("");
                break;
            case R.id.iv_asp_look://查看密码
                etAspPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ivAspLook.setVisibility(View.GONE);
                ivAspUnLook.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_asp_un_look://隐藏密码
                etAspPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                ivAspLook.setVisibility(View.VISIBLE);
                ivAspUnLook.setVisibility(View.GONE);
                break;
            case R.id.btn_asp_next:
                Map<String, String> map = new HashMap<>();
                map.put("mobile", phoneNum);
                map.put("sign",sign);
                if (type == 0) {
                    map.put("passWord", MD5Utils.getMd5Code(etAspPwd.getText().toString()));
                    presenter.register(map);
                } else if (type == 1) {
                    map.put("passWord", MD5Utils.getMd5Code(etAspPwd.getText().toString()));
                    presenter.forgetPwd(map);
                }
                break;
        }
    }

    @Override
    public void sendCodeSuccess(int code, SendCodeBean sendCodeBean) {

    }

    @Override
    public void sendCodeFailed(int code, String message) {

    }

    @Override
    public void registerSuccess(int code, LoginBean loginBean) {
        PublicHeadersInterceptor.updateToken(loginBean.getToken());
        SPUtils.getInstance().save("KEY_WORD_TOKEN",loginBean.getToken());
        presenter.getUserInfo();
    }

    @Override
    public void registerFailed(int code, String message) {
        ToastUtils.showToast(this, "code:" + code + "\t" + message);
    }

    @Override
    public void checkCodeSuccess(int code, CheckCodeBean checkCodeBean) {

    }

    @Override
    public void checkCodeFailed(int code, String message) {

    }

    @Override
    public void getUserInfoSuccess(int code, UserBean userBean) {
        UserUtils.getInstance().login(userBean);
        if(type == 0){
            ToastUtils.showToast(this, "注册成功");
//            startActivity(new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            startActivity(new Intent(this,WriteDataActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }else{
            startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    @Override
    public void getUserInfoFailed(int code, String message) {
        ToastUtils.showToast(this, "code:" + code + "\t" + message);
    }

    @Override
    public void forgetPwdSuccess(int code, LoginBean loginBean) {
        PublicHeadersInterceptor.updateToken(loginBean.getToken());
        SPUtils.getInstance().save("KEY_WORD_TOKEN",loginBean.getToken());
        presenter.getUserInfo();
    }

    @Override
    public void forgetPwdFailed(int code, String message) {
        ToastUtils.showToast(this, "code:" + code + "\t" + message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideInput(this,etAspPwd);
    }

    private void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
