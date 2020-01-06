package com.dan.yousuanshi.module.addressbook.activity;

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
import com.dan.yousuanshi.module.addressbook.presenter.UpdateTeamNameActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.UpdateTeamNameActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateTeamNameActivity extends BaseActivity<UpdateTeamNameActivityPresenter> implements UpdateTeamNameActivityView {

    @BindView(R.id.et_input_text)
    EditText etInputText;
    @BindView(R.id.iv_clear)
    ImageView ivClear;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_team_name;
    }

    @Nullable
    @Override
    protected UpdateTeamNameActivityPresenter initPresenter() {
        return new UpdateTeamNameActivityPresenter();
    }

    @Override
    protected void initView() {
        etInputText.setText(UserUtils.getInstance().getUserBean().getC_name());
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

    @Override
    public void updateTeamInfoSuccess(int code, List list) {
        Intent intent = new Intent(this,DiyManagerActivity.class);
        intent.putExtra("name",etInputText.getText().toString());
        setResult(1,intent);
        finish();
    }

    @Override
    public void updateTeamInfoFailed(int code, String msg) {
        ToastUtils.showToast(this,"修改公司名称失败："+code+msg);
        Log.e("UpdateTeamName","修改公司名称失败："+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit,R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(StringUtils.isEmpty(etInputText.getText().toString())){
                    ToastUtils.showToast(this,"公司名称不能为空");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
                map.put("changeType",1);
                map.put("changeValue",etInputText.getText().toString());
                presenter.updateTeamInfo(map);
                break;
            case R.id.iv_clear:
                etInputText.setText("");
                break;
        }
    }
}
