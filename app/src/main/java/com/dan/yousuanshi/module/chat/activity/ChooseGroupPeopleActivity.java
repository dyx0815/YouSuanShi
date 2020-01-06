package com.dan.yousuanshi.module.chat.activity;

import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.ui.HintSideBar;
import com.dan.yousuanshi.module.chat.presenter.ChooseGroupPeopleActivityPresenter;
import com.dan.yousuanshi.module.chat.view.ChooseGroupPeopleActivityView;

import java.util.List;

import butterknife.BindView;

public class ChooseGroupPeopleActivity extends BaseActivity<ChooseGroupPeopleActivityPresenter> implements ChooseGroupPeopleActivityView {

    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.hintSideBar)
    HintSideBar hintSideBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_group_people;
    }

    @Nullable
    @Override
    protected ChooseGroupPeopleActivityPresenter initPresenter() {
        return new ChooseGroupPeopleActivityPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void addGroupPeopleSuccess(int code, List list) {

    }

    @Override
    public void addGroupPeopleFailed(int code, String msg) {

    }
}
