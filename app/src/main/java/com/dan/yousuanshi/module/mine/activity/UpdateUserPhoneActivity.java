package com.dan.yousuanshi.module.mine.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.mine.presenter.UpdateUserPhoneActivityPresenter;
import com.dan.yousuanshi.module.mine.view.UpdateUserPhoneActivityView;
import com.dan.yousuanshi.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateUserPhoneActivity extends BaseActivity<UpdateUserPhoneActivityPresenter> implements UpdateUserPhoneActivityView {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_update)
    TextView tvUpdate;

    private String phone;
    private String smsId;
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_phone;
    }

    @Nullable
    @Override
    protected UpdateUserPhoneActivityPresenter initPresenter() {
        return new UpdateUserPhoneActivityPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        phone = intent.getStringExtra("phone");
        if (type == 1) {
            tvPhone.setText("手机号修改成功");
            tvUpdate.setText("确定");
            ivIcon.setImageResource(R.mipmap.icon_mine_update_phone_success);
        }else{
            tvPhone.setText("已绑定手机号：" + phone);
        }
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.tv_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_update:
                if (type == 1) {
                    finish();
                } else {
                    presenter.sendCode();
                }
                break;
        }
    }

    @Override
    public void sendCodeSuccess(int code, SendCodeBean sendCodeBean) {
        if (code == 0) {
            smsId = sendCodeBean.getSmsId();
            ToastUtils.showToast(this, "发送验证码成功");
            Intent intent = new Intent(this, UpdateUserCodeActivity.class);
            intent.putExtra("phone", phone);
            intent.putExtra("smsId", smsId);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void sendCodeFailed(int code, String message) {
        ToastUtils.showToast(this, "验证码发送失败：" + code + "\t" + message);
        Log.e("UpdateUserPhoneActivity", "验证码发送失败：" + code + "\t" + message);
    }

}
