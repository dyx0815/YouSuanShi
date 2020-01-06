package com.dan.yousuanshi.module.addressbook.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.presenter.UpdateRemarkActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.UpdateRemarkActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateRemarkActivity extends BaseActivity<UpdateRemarkActivityPresenter> implements UpdateRemarkActivityView {

    @BindView(R.id.et_name)
    EditText etName;

    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_remark;
    }

    @Nullable
    @Override
    protected UpdateRemarkActivityPresenter initPresenter() {
        return new UpdateRemarkActivityPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {
        id = getIntent().getIntExtra("id",0);
    }

    @Override
    public void updateRemarkSuccess(int code, List data) {
        ToastUtils.showToast(this,"修改备注成功");
        finish();
    }

    @Override
    public void updateRemarkFailed(int code, String msg) {
        ToastUtils.showToast(this,"修改备注失败："+code+"\t"+msg);
        Log.e("UpdateRemarkActivity","修改备注失败："+code+"\t"+msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(!StringUtils.isEmpty(etName.getText().toString())){

                }else{
                    ToastUtils.showToast(this,"备注不能为空！！");
                }
                Map<String,String> map =new HashMap<>();
                map.put("friendId",String.valueOf(id));
                map.put("nickName",etName.getText().toString());
                presenter.updateRemark(map);
                break;
        }
    }
}
