package com.dan.yousuanshi.module.chat.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.chat.presenter.UpdateGroupFileActivityPresenter;
import com.dan.yousuanshi.module.chat.view.UpdateGroupFileActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class UpdateGroupFileActivity extends BaseActivity<UpdateGroupFileActivityPresenter> implements UpdateGroupFileActivityView {

    @BindView(R.id.et_input_text)
    EditText etInputText;
    @BindView(R.id.iv_clear)
    ImageView ivClear;

    private int fileId;
    private int groupId;
    private String fileEnd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_group_file;
    }

    @Nullable
    @Override
    protected UpdateGroupFileActivityPresenter initPresenter() {
        return new UpdateGroupFileActivityPresenter();
    }

    @Override
    protected void initView() {
        fileId = getIntent().getIntExtra("fileId",0);
        groupId = getIntent().getIntExtra("groupId",0);
        fileEnd = getIntent().getStringExtra("fileEnd");
        etInputText.setFocusable(true);
        etInputText.requestFocus();
        showInput(this,etInputText);
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
    protected void onPause() {
        super.onPause();
        hideInput(this,etInputText);
    }

    @Override
    public void renameGroupFileSuccess(int code, BaseBean data) {
        ToastUtils.showToast(this,"修改成功！");
        finish();
    }

    @Override
    public void renameGroupFileFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
        finish();
    }


    @OnClick({R.id.ll_back, R.id.ll_submit,R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(StringUtils.isEmpty(etInputText.getText().toString())){
                    ToastUtils.showToast(this,"请输入文件名称！");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("fileId",fileId);
                map.put("groupId",groupId);
                map.put("fileName",etInputText.getText().toString()+fileEnd);
                presenter.renameGroupFile(map);
                break;
            case R.id.iv_clear:
                etInputText.setText("");
                break;
        }
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
