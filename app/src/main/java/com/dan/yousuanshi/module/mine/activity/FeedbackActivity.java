package com.dan.yousuanshi.module.mine.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.mine.presenter.FeedbackActivityPresenter;
import com.dan.yousuanshi.module.mine.view.FeedbackActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity<FeedbackActivityPresenter> implements FeedbackActivityView {

    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_feedback)
    LinearLayout llFeedback;
    @BindView(R.id.ll_feedback_success)
    LinearLayout llFeedbackSuccess;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Nullable
    @Override
    protected FeedbackActivityPresenter initPresenter() {
        return new FeedbackActivityPresenter();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void feedbackSuccess(int code, List list) {
        llFeedback.setVisibility(View.GONE);
        llFeedbackSuccess.setVisibility(View.VISIBLE);
    }

    @Override
    public void feedbackFailed(int code, String msg) {
        ToastUtils.showToast(this,"意见反馈失败："+code+msg);
        Log.e("Feedback","意见反馈失败："+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_submit:
                if(StringUtils.isEmpty(etFeedback.getText().toString())){
                    ToastUtils.showToast(this,"请输入意见再提交哦~");
                    return;
                }
                Map<String,String> map = new HashMap<>();
                map.put("message",etFeedback.getText().toString());
                presenter.feedback(map);
                break;
        }
    }
}
