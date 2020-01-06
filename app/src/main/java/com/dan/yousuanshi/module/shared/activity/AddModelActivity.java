package com.dan.yousuanshi.module.shared.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.shared.presenter.AddModelActivityPresenter;
import com.dan.yousuanshi.module.shared.view.AddModelActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class AddModelActivity extends BaseActivity<AddModelActivityPresenter> implements AddModelActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.et_input)
    EditText etInput;

    private int companyId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_model;
    }

    @Nullable
    @Override
    protected AddModelActivityPresenter initPresenter() {
        return new AddModelActivityPresenter();
    }

    @Override
    protected void initView() {
        companyId = getIntent().getIntExtra("companyId",companyId);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void addModelSuccess(int code, BaseBean data) {
        ToastUtils.showCenterToast(this, "新增成功~");
        finish();
    }

    @Override
    public void addModelFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }


    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(StringUtils.isEmpty(etInput.getText().toString())){
                    ToastUtils.showToast(this,"请输入分组名称！");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("companyId",companyId);
                map.put("modelName",etInput.getText().toString());
                presenter.addModel(map);
                break;
        }
    }
}
