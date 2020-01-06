package com.dan.yousuanshi.module.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.mine.presenter.UpdateUserInfoActivityPresenter;
import com.dan.yousuanshi.module.mine.view.UpdateUserInfoActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateUserInfoActivity extends BaseActivity<UpdateUserInfoActivityPresenter> implements UpdateUserInfoActivityView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_input_text)
    EditText etInputText;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.ll_you_number)
    LinearLayout llYouNumber;

    private String type;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_info;
    }

    @Nullable
    @Override
    protected UpdateUserInfoActivityPresenter initPresenter() {
        return new UpdateUserInfoActivityPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        String text = intent.getStringExtra("text");
        if(!StringUtils.isEmpty(text)){
            etInputText.setText(text);
            ivClear.setVisibility(View.VISIBLE);
        }
        if ("2".equals(type)) {
            tvTitle.setText("修改昵称");
            etInputText.setHint("请输入昵称");
        } else if ("3".equals(type)) {
            tvTitle.setText("设置姓名");
            etInputText.setHint("请输入姓名");
        } else if ("6".equals(type)) {
            tvTitle.setText("优号");
            etInputText.setHint("请输入6-14位数字或字母");
            llYouNumber.setVisibility(View.VISIBLE);
        }
        etInputText.setFocusable(true);
        etInputText.setFocusableInTouchMode(true);
        etInputText.requestFocus();//获取焦点 光标出现
        etInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etInputText.getText().toString().length()>0){
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

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(StringUtils.isEmpty(etInputText.getText().toString())){
                    ToastUtils.showToast(this,"请输入内容~");
                    return;
                }
                if("6".equals(type)){
                    showDialog();
                }else{
                    Map<String,String> map = new HashMap<>();
                    map.put("setType",type);
                    map.put("setValue",etInputText.getText().toString());
                    presenter.updateUserInfo(map);
                }
                break;
            case R.id.iv_clear:
                etInputText.setText("");
                break;
        }
    }

    @Override
    public void updateUserInfoSuccess(int code, List list) {
        Intent intent = new Intent();
        intent.putExtra("text",etInputText.getText().toString());
        setResult(10,intent);
        finish();
    }

    @Override
    public void updateUserInfoFailed(int code, String msg) {
        ToastUtils.showToast(this,"修改用户信息失败："+code+"\t"+msg);
        finish();
    }

    private void showDialog(){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_update_you_number);
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
                    Map<String,String> map = new HashMap<>();
                    map.put("setType",type);
                    map.put("setValue",etInputText.getText().toString());
                    presenter.updateUserInfo(map);
                }
            });
        }
        dialog.show();
    }
}
