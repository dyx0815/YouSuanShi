package com.dan.yousuanshi.module.shared.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.shared.bean.TemplateBean;
import com.dan.yousuanshi.module.shared.fragment.TemplateManagerFragment;
import com.dan.yousuanshi.module.shared.presenter.TemplateManagerActivityPresenter;
import com.dan.yousuanshi.module.shared.view.TemplateManagerActivityView;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TemplateManagerActivity extends BaseActivity<TemplateManagerActivityPresenter> implements TemplateManagerActivityView {

    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<TemplateBean> templateList = new ArrayList<>();
    private ArrayList<Fragment> fragmentList;
    String[] titles = new String[]{};
    private int companyId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_template_manager;
    }

    @Nullable
    @Override
    protected TemplateManagerActivityPresenter initPresenter() {
        return new TemplateManagerActivityPresenter();
    }

    @Override
    protected void initView() {
        String team = SPUtils.getInstance().get(Constant.SHARED_COMPANY,"");
        if(StringUtils.isEmpty(team)){
            companyId = UserUtils.getInstance().getUserBean().getUser_company();
        }else{
            companyId = new Gson().fromJson(team, MyTeamBean.class).getCompanyId();
        }
        presenter.getTemplate(companyId);
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void getTemplateSuccess(int code, List<TemplateBean> data) {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            titles[i] = data.get(i).getModel_name();
            fragmentList.add(new TemplateManagerFragment(data.get(i).getChildren()));
        }
        tab.setViewPager(viewPager,titles,this,fragmentList);
    }

    @Override
    public void getTemplateFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }
}
