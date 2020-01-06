package com.dan.yousuanshi.module.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.login.activity.LoginActivity;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_my_info, R.id.ll_set_password, R.id.ll_privacy, R.id.ll_common, R.id.ll_about, R.id.ll_not_disturb, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_my_info://我的信息
                startActivity(new Intent(this,MyInfoActivity.class));
                break;
            case R.id.ll_set_password://设置密码
                startActivity(new Intent(this,UpdatePasswordActivity.class));
                break;
            case R.id.ll_privacy://隐私
                startActivity(new Intent(this,PrivacyActivity.class));
                break;
            case R.id.ll_common://通用
                startActivity(new Intent(this,CommonActivity.class));
                break;
            case R.id.ll_about://关于我们
                startActivity(new Intent(this,AboutMeActivity.class));
                break;
            case R.id.ll_not_disturb://勿扰模式
                startActivity(new Intent(this,NotDisturbActivity.class));
                break;
            case R.id.tv_exit:
                showDialog();
                break;
        }
    }

    private void showDialog(){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_exit_user);
            TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
            TextView tvSure = dialog.findViewById(R.id.tv_sure);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    UserUtils.getInstance().logout();
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    ToastUtils.showToast(SettingActivity.this,"退出成功！");
                }
            });
        }
        dialog.show();
    }
}
