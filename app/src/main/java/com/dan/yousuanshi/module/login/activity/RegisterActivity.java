package com.dan.yousuanshi.module.login.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
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
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.login.presenter.RegisterPresenter;
import com.dan.yousuanshi.module.login.view.RegisterView;
import com.dan.yousuanshi.utils.MyHanlder;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_ar_register_title)
    TextView tvArRegisterTitle;
    @BindView(R.id.tv_ar_phone_title)
    TextView tvArPhoneTitle;
    @BindView(R.id.iv_ar_clear)
    ImageView ivArClear;
    @BindView(R.id.ll_ar_phone)
    LinearLayout llArPhone;
    @BindView(R.id.btn_ar_next)
    Button btnArNext;
    @BindView(R.id.et_ar_phone)
    EditText etArPhone;

    private int type;//判断是注册还是忘记密码 0.注册 1.忘记密码
    private String smsId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Nullable
    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            tvTitle.setText("忘记密码");
            tvArRegisterTitle.setText("找回密码");
        }
        etArPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!StringUtils.isEmpty(etArPhone.getText().toString())){
                    ivArClear.setVisibility(View.VISIBLE);
                }else{
                    ivArClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etArPhone.setFocusable(true);
        etArPhone.setFocusableInTouchMode(true);
        etArPhone.requestFocus();//获取焦点 光标出现
        MyHanlder.getInstance().postDelayed(()-> showInput(this,etArPhone),50);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.iv_ar_clear, R.id.btn_ar_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_ar_clear:
                etArPhone.setText("");
                break;
            case R.id.btn_ar_next:
                String phoneNum = etArPhone.getText().toString();
                if (!StringUtils.isTrimEmpty(phoneNum)) {
                    if (StringUtils.isMobilePhone(phoneNum)) {
                        Map<String,String> map = new HashMap<>();
                        map.put("mobile",phoneNum);
                        if(type == 0){//注册
                            map.put("sendType","2");
                        }else if(type == 1){//忘记密码
                            map.put("sendType","3");
                        }
                        presenter.sendCode(map);
                    } else {
                        ToastUtils.showToast(this, "手机号格式不正确");
                    }
                } else {
                    ToastUtils.showToast(this, "请输入手机号");
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyHanlder.getInstance().postDelayed(()->hideInput(this,etArPhone),50);
    }

    @Override
    public void sendCodeSuccess(int code, SendCodeBean sendCodeBean) {
        if(code == 0){
            smsId = sendCodeBean.getSmsId();
            ToastUtils.showToast(this,"发送验证码成功");
            Intent intent = new Intent(this, RegisterCodeActivity.class);
            intent.putExtra("phoneNum", etArPhone.getText().toString());
            intent.putExtra("type",type);
            intent.putExtra("smsId",smsId);
            startActivity(intent);
        }
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

    private void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
