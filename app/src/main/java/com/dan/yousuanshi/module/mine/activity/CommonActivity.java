package com.dan.yousuanshi.module.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class CommonActivity extends BaseActivity {

    @BindView(R.id.switch_voice_to_word)
    Switch switchVoiceToWord;
    @BindView(R.id.switch_enter_send_message)
    Switch switchEnterSendMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common;
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

    @OnClick({R.id.ll_back, R.id.ll_many_language, R.id.ll_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_many_language:
                startActivity(new Intent(this,ManyLanguageActivity.class));
                break;
            case R.id.ll_clear:
                startActivity(new Intent(this,ClearActivity.class));
                break;
        }
    }
}
