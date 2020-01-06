package com.dan.yousuanshi.module.shared.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.shared.bean.AnnouncementLisBean;
import com.dan.yousuanshi.module.shared.presenter.UpdateAnnouncementActivityPresenter;
import com.dan.yousuanshi.module.shared.view.UpdateAnnouncementActivityView;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class UpdateAnnouncementActivity extends BaseActivity<UpdateAnnouncementActivityPresenter> implements UpdateAnnouncementActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;

    private AnnouncementLisBean.DataBean dataBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_announcement;
    }

    @Nullable
    @Override
    protected UpdateAnnouncementActivityPresenter initPresenter() {
        return new UpdateAnnouncementActivityPresenter();
    }

    @Override
    protected void initView() {
        dataBean = getIntent().getParcelableExtra("data");
        if (dataBean != null) {
            etTitle.setText(dataBean.getTitle());
            etContent.setText(dataBean.getContent());
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void addAnnouncementSuccess(int code, BaseBean data) {
        ToastUtils.showCenterToast(this,"添加成功~");
        finish();
    }

    @Override
    public void addAnnouncementFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void updateAnnouncementSuccess(int code, BaseBean data) {
        ToastUtils.showCenterToast(this,"修改成功~");
        finish();
    }

    @Override
    public void updateAnnouncementFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                if(StringUtils.isEmpty(etTitle.getText().toString())){
                    ToastUtils.showToast(this,"请输入标题！");
                    return;
                }
                if(StringUtils.isEmpty(etContent.getText().toString())){
                    ToastUtils.showToast(this,"请输入内容！");
                    return;
                }
                String team = SPUtils.getInstance().get(Constant.SHARED_COMPANY,"");
                int companyId = 0;
                if(StringUtils.isEmpty(team)){
                   companyId = UserUtils.getInstance().getUserBean().getUser_company();
                }else{
                    companyId = new Gson().fromJson(team, MyTeamBean.class).getCompanyId();
                }
                if(dataBean != null){
                    Map<String,Object> map = new HashMap<>();
                    map.put("title",etTitle.getText().toString());
                    map.put("content",etContent.getText().toString());
                    map.put("afficheId",dataBean.getId());
                    map.put("companyId", companyId);
                    presenter.updateAnnouncement(map);
                }else{
                    Map<String,Object> map = new HashMap<>();
                    map.put("companyId", companyId);
                    map.put("title",etTitle.getText().toString());
                    map.put("content",etContent.getText().toString());
                    presenter.addAnnouncement(map);
                }
                break;
        }
    }
}
