package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.presenter.AddFriendActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.AddFriendActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFriendVerificationActivity extends BaseActivity<AddFriendActivityPresenter> implements AddFriendActivityView {


    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;


    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friend_verification;
    }

    @Nullable
    @Override
    protected AddFriendActivityPresenter initPresenter() {
        MyApplication.addActivity(this);
        return new AddFriendActivityPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!StringUtils.isEmpty(etInput.getText().toString())){
                    llClear.setVisibility(View.VISIBLE);
                }else{
                    llClear.setVisibility(View.GONE);
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

    @Override
    public void sendAddFriendSuccess(int code, List list) {
        ToastUtils.showToast(this,"已发送好友请求");
        MyApplication.clearActivity();
    }

    @Override
    public void sendAddFriendFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_clear, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_clear:
                etInput.setText("");
                break;
            case R.id.tv_send:
                Map<String,String> map = new HashMap<>();
                map.put("addid",id);
                map.put("message",etInput.getText().toString());
                presenter.sendAddFriend(map);
                break;
        }
    }
}
