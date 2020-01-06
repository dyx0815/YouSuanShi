package com.dan.yousuanshi.module.shared.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.shared.bean.AnnouncementBean;
import com.dan.yousuanshi.module.shared.bean.AnnouncementLisBean;

import butterknife.BindView;
import butterknife.OnClick;

public class AnnouncementInfoActivity extends BaseActivity {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private int type;
    private AnnouncementBean announcementBean;
    private AnnouncementLisBean.DataBean dataBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_announcement_info;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type",0);
        if(type == 2){
            dataBean = getIntent().getParcelableExtra("data");
            tvTitle.setText(dataBean.getTitle());
            tvContent.setText(dataBean.getContent());
            tvDate.setText("发布时间： "+dataBean.getCreate_time()+" | 作者："+dataBean.getReal_name()+" ");
            llSubmit.setVisibility(View.GONE);
        }else{
            announcementBean = getIntent().getParcelableExtra("data");
            tvTitle.setText(announcementBean.getTitle());
            tvContent.setText(announcementBean.getContent());
            tvDate.setText("发布时间： "+announcementBean.getCreate_time()+" | 作者："+announcementBean.getReal_name()+" ");
        }
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                startActivity(new Intent(this,AnnouncementActivity.class));
                finish();
                break;
        }
    }
}
