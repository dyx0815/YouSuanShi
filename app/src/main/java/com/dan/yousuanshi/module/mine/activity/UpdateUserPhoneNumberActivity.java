package com.dan.yousuanshi.module.mine.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.mine.presenter.UpdateUserPhoneNumberActivityPresenter;
import com.dan.yousuanshi.module.mine.view.UpdateUserPhoneNumberActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateUserPhoneNumberActivity extends BaseActivity<UpdateUserPhoneNumberActivityPresenter> implements UpdateUserPhoneNumberActivityView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_clear)
    ImageView ivClear;

    private String sign;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_phone_number;
    }

    @Nullable
    @Override
    protected UpdateUserPhoneNumberActivityPresenter initPresenter() {
        return new UpdateUserPhoneNumberActivityPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        sign = intent.getStringExtra("sign");
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!StringUtils.isEmpty(etPhone.getText().toString())){
                    ivClear.setVisibility(View.VISIBLE);
                }else{
                    ivClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.ll_back, R.id.btn_get_code,R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_get_code:
                Map<String,String> map = new HashMap<>();
                map.put("mobile",etPhone.getText().toString());
                presenter.sendCode2(map);
                break;
            case R.id.iv_clear:
                etPhone.setText("");
                break;
        }
    }

    @Override
    public void sendCode2Success(int code, SendCodeBean sendCodeBean) {
        Intent intent = new Intent(this,UpdateUserCodeActivity.class);
        intent.putExtra("phone",etPhone.getText().toString());
        intent.putExtra("type",1);
        intent.putExtra("smsId",sendCodeBean.getSmsId());
        intent.putExtra("sign",sign);
        startActivity(intent);
        finish();
    }

    @Override
    public void sendCode2Failed(int code, String msg) {
        ToastUtils.showToast(this,"发送验证码失败："+code+"\t"+msg);
        Log.e("UpdateUserPhoneNumber","发送验证码失败："+code+"\t"+msg);
    }
}
