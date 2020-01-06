package com.dan.yousuanshi.module.main.activity;

import android.Manifest;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.http.PublicHeadersInterceptor;
import com.dan.yousuanshi.module.login.activity.LoginActivity;
import com.dan.yousuanshi.module.login.activity.WriteDataActivity;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.Timer;
import java.util.TimerTask;

import rx.functions.Action1;

public class WelcomeActivity extends BaseActivity {

    private Timer timer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }


    @Override
    protected void initView() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                RxPermissions.getInstance(WelcomeActivity.this)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA
                                , Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.READ_PHONE_STATE
                                , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE
                                , Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                UserBean userBean = UserUtils.getInstance().getUserBean();
                                if (userBean != null && !DataBaseHelper.tabbleIsExist(getContext(), DataBaseHelper.getOftenModelTableName(userBean.getUser_id()))) {
                                    DataBaseHelper.createOftenModelTable(getContext(), userBean.getUser_id());
                                }
                                if (UserUtils.getInstance().isLogin()) {
                                    Log.e("Welcome", UserUtils.getInstance().getUserBean().getNick_name() + "\tisshow:" + UserUtils.getInstance().getUserBean().getIsShow());
                                    if (UserUtils.getInstance().getUserBean().getIsShow() == 1) {
                                        PublicHeadersInterceptor.updateToken(SPUtils.getInstance().get("KEY_WORD_TOKEN", ""));
                                        startActivity(new Intent(getActivity(), WriteDataActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        finish();
                                    } else {
                                        PublicHeadersInterceptor.updateToken(SPUtils.getInstance().get("KEY_WORD_TOKEN", ""));
                                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                                        finish();
                                    }
                                } else {
                                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                                    finish();
                                }
//                                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
//                                    finish();
                            }
                        });
            }
        }, 2000);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

}
