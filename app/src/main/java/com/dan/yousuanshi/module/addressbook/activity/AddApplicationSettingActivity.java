package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.addressbook.bean.DiyApplicationSettingBean;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class AddApplicationSettingActivity extends BaseActivity {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.switch_setting)
    Switch switchSetting;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_application_setting;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        etInput.setFocusable(true);
        etInput.requestFocus();
        showInput(this,etInput);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(StringUtils.isEmpty(etInput.getText().toString())){
                    ToastUtils.showToast(this,"请输入自定义问题！");
                    return;
                }
                Intent intent = new Intent(this,SettingApplicationActivity.class);
                intent.putExtra("data",new DiyApplicationSettingBean(etInput.getText().toString(),"TextView",switchSetting.isChecked()?1:0,0));
                setResult(1,intent);
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideInput(this,etInput);
    }

    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
}
