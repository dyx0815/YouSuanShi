package com.dan.yousuanshi.module.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.mine.presenter.UpdatePasswordActivityPresenter;
import com.dan.yousuanshi.module.mine.view.UpdatePasswordActivityView;
import com.dan.yousuanshi.utils.MD5Utils;
import com.dan.yousuanshi.utils.MyHanlder;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePasswordActivity extends BaseActivity<UpdatePasswordActivityPresenter> implements UpdatePasswordActivityView {

    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_unlook)
    ImageView ivUnlook;
    @BindView(R.id.iv_look)
    ImageView ivLook;
    @BindView(R.id.ll_success)
    LinearLayout llSuccess;
    @BindView(R.id.ll_set_pwd)
    LinearLayout llSetPwd;

    private int setPassword;
    private String beforePwd;
    private String newPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @Nullable
    @Override
    protected UpdatePasswordActivityPresenter initPresenter() {
        return new UpdatePasswordActivityPresenter();
    }

    @Override
    protected void initView() {
        setPassword = UserUtils.getInstance().getUserBean().getSetPasswd();
        beforePwd = getIntent().getStringExtra("beforePwd");
        newPwd = getIntent().getStringExtra("newPwd");
        if(StringUtils.isEmpty(beforePwd)){
            if(setPassword == 1){
                etPwd.setHint("请设置密码");
            }else{
                etPwd.setHint("原密码");
            }
        }else if(StringUtils.isEmpty(newPwd)){
            etPwd.setHint("请设置密码");
        }else{
            etPwd.setHint("请再次输入");
        }
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etPwd.getText().toString().length()>0){
                    if(ivLook.getVisibility() == View.GONE){
                        ivLook.setVisibility(View.VISIBLE);
                        ivUnlook.setVisibility(View.GONE);
                    }else{
                        ivLook.setVisibility(View.GONE);
                        ivUnlook.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPwd.setFocusable(true);
        etPwd.setFocusableInTouchMode(true);
        etPwd.requestFocus();//获取焦点 光标出现
        MyHanlder.getInstance().postDelayed(()-> showInput(this,etPwd),50);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void setPasswordSuccess(int code, List list) {
        llSetPwd.setVisibility(View.GONE);
        llSuccess.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPasswordFailed(int code, String msg) {
        ToastUtils.showToast(this,"设置密码失败" + "\tcode:" + code + "\t" + msg);
        Log.e("MineFragment", "设置密码失败" + "\tcode:" + code + "\t" + msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_next,R.id.iv_look,R.id.iv_unlook,R.id.tv_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_next:
                if(StringUtils.isEmpty(etPwd.getText().toString())){
                    ToastUtils.showToast(this,"请输入密码！");
                    return;
                }
                if(StringUtils.isPassword(etPwd.getText().toString())){
                    ToastUtils.showToast(this,"密码为6-18位英文和数字！");
                    return;
                }
                Intent intent = new Intent(this,UpdatePasswordActivity.class);
                if(StringUtils.isEmpty(beforePwd)){
                    if(setPassword == 1){
                        intent.putExtra("newPwd",etPwd.getText().toString());
                        intent.putExtra("beforePwd",beforePwd);
                    }else{
                        intent.putExtra("beforePwd",etPwd.getText().toString());
                    }
                    startActivity(intent);
                }else if(StringUtils.isEmpty(newPwd)){
                    intent.putExtra("newPwd",etPwd.getText().toString());
                    intent.putExtra("beforePwd",beforePwd);
                    startActivity(intent);
                }else{
                    if(!newPwd.equals(etPwd.getText().toString())){
                        ToastUtils.showToast(this,"两次密码输入的不一致！");
                    }
                    Map<String,Object> map = new HashMap<>();
                    if(!StringUtils.isEmpty(beforePwd)){
                        map.put("oldPasswd", MD5Utils.getMd5Code(beforePwd));
                    }
                    map.put("newPasswd",MD5Utils.getMd5Code(newPwd));
                    map.put("confirmPasswd",MD5Utils.getMd5Code(etPwd.getText().toString()));
                    presenter.setPassword(map);
                }
                MyApplication.addActivity(this);
                break;
            case R.id.iv_look:
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ivLook.setVisibility(View.GONE);
                ivUnlook.setVisibility(View.VISIBLE);
                etPwd.setSelection(etPwd.getText().toString().length());
                break;
            case R.id.iv_unlook:
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                ivLook.setVisibility(View.VISIBLE);
                ivUnlook.setVisibility(View.GONE);
                etPwd.setSelection(etPwd.getText().toString().length());
                break;
            case R.id.tv_complete:
                MyApplication.clearActivity();
                break;
        }
    }

    private void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyHanlder.getInstance().postDelayed(()->hideInput(this,etPwd),50);
    }
}
