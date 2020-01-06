package com.dan.yousuanshi.module.chat.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.chat.bean.GroupAnnouncementBean;
import com.dan.yousuanshi.module.chat.presenter.InputGroupAmActivityPresenter;
import com.dan.yousuanshi.module.chat.view.InputGroupAmActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class InputGroupAmActivity extends BaseActivity<InputGroupAmActivityPresenter> implements InputGroupAmActivityView {

    @BindView(R.id.et_input)
    EditText etInput;

    private int groupId;
    private GroupAnnouncementBean.DataBean data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_group_am;
    }

    @Nullable
    @Override
    protected InputGroupAmActivityPresenter initPresenter() {
        return new InputGroupAmActivityPresenter();
    }

    @Override
    protected void initView() {
        groupId = getIntent().getIntExtra("groupId",0);
        data = getIntent().getParcelableExtra("data");
        if(data!=null){
            etInput.setText(data.getContent());
        }
        etInput.setFocusable(true);
        etInput.requestFocus();
        etInput.setSelection(etInput.getText().toString().length());
        showInput(this,etInput);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void submitGroupAnnouncementSuccess(int code, List list) {
        ToastUtils.showToast(this,"发布成功！");
        finish();
    }

    @Override
    public void submitGroupAnnouncementFailed(int code, String msg) {
        ToastUtils.showToast(this,"发布公告失败："+code+msg);
        Log.e("InputGroupAm","发布公告失败:"+code+msg);
    }

    @Override
    public void updateGroupAnnouncementSuccess(int code, List list) {
        ToastUtils.showToast(this,"修改公告成功！");
        finish();
    }

    @Override
    public void updateGroupAnnouncementFailed(int code, String msg) {
        ToastUtils.showToast(this,"修改公告失败："+code+msg);
        Log.e("InputGroupAm","修改公告失败:"+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(StringUtils.isEmpty(etInput.getText().toString())){
                    ToastUtils.showToast(this,"请输入发布的公告内容！");
                    return;
                }
                if(data!=null){
                    Map<String ,Object> map = new HashMap<>();
                    map.put("groupId",groupId);
                    map.put("content",etInput.getText().toString());
                    map.put("titleId",data.getId());
                    presenter.updateGroupAnnouncement(map);
                }else{
                    Map<String ,Object> map = new HashMap<>();
                    map.put("groupId",groupId);
                    map.put("content",etInput.getText().toString());
                    presenter.submitGroupAnnouncement(map);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideInput(this,etInput);
    }

    private void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
