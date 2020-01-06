package com.dan.yousuanshi.module.shared.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.shared.presenter.UpdateModelNameActivityPresenter;
import com.dan.yousuanshi.module.shared.view.UpdateModelNameActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class UpdateModelNameActivity extends BaseActivity<UpdateModelNameActivityPresenter> implements UpdateModelNameActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.et_input)
    EditText etInput;

    private int companyId;
    private int modelId;
    private String modelName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_model_name;
    }

    @Nullable
    @Override
    protected UpdateModelNameActivityPresenter initPresenter() {
        return new UpdateModelNameActivityPresenter();
    }

    @Override
    protected void initView() {
        companyId = getIntent().getIntExtra("companyId",0);
        modelId = getIntent().getIntExtra("modelId",0);
        modelName = getIntent().getStringExtra("modelName");
        etInput.setText(modelName);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void updateModelNameSuccess(int code, BaseBean data) {
        ToastUtils.showCenterToast(this,"修改成功~");
        finish();
    }

    @Override
    public void updateModelNameFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(StringUtils.isEmpty(etInput.getText().toString())){
                    ToastUtils.showToast(this,"请输入标题！");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("companyId",companyId);
                map.put("modelId",modelId);
                map.put("modelName",etInput.getText().toString());
                presenter.updateModelName(map);
                break;
        }
    }
}
