package com.dan.yousuanshi.module.mine.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ManyLanguageActivity extends BaseActivity {

    @BindView(R.id.tv_follow_system)
    TextView tvFollowSystem;
    @BindView(R.id.tv_jian_chinese)
    TextView tvJianChinese;
    @BindView(R.id.tv_fan_chinese_taiwan)
    TextView tvFanChineseTaiwan;
    @BindView(R.id.tv_fan_chinese_hongkong)
    TextView tvFanChineseHongkong;
    @BindView(R.id.tv_english)
    TextView tvEnglish;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_many_language;
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

    @OnClick({R.id.ll_back, R.id.tv_follow_system, R.id.tv_jian_chinese, R.id.tv_fan_chinese_taiwan, R.id.tv_fan_chinese_hongkong, R.id.tv_english})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_follow_system:
                break;
            case R.id.tv_jian_chinese:
                break;
            case R.id.tv_fan_chinese_taiwan:
                break;
            case R.id.tv_fan_chinese_hongkong:
                break;
            case R.id.tv_english:
                break;
        }
    }
}
