package com.dan.yousuanshi.module.chat.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.chat.fragment.SearchListFragment;
import com.dan.yousuanshi.utils.ToastUtils;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.tab)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.et_search)
    EditText etSearch;

    private ArrayList<Fragment> fragmentList;
    private String[] titles = {"全部", "联系人", "群组", "部门", "功能", "聊天记录"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initFragment();
        initSearch();
        tabLayout.setViewPager(viewPager, titles, this, fragmentList);
    }

    @Override
    protected void loadData() {

    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new SearchListFragment());
        fragmentList.add(new SearchListFragment());
        fragmentList.add(new SearchListFragment());
        fragmentList.add(new SearchListFragment());
        fragmentList.add(new SearchListFragment());
        fragmentList.add(new SearchListFragment());
    }

    private void initSearch() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                ToastUtils.showToast(SearchActivity.this, "搜索");
                return true;
            }
        });
    }
}
