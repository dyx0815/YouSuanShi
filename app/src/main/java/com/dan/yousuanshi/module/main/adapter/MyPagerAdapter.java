package com.dan.yousuanshi.module.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


public class MyPagerAdapter extends FragmentPagerAdapter{

    String[] titles = {"消息", "共享", "通讯录", "我的"};

    List<Fragment> fragments;

    public MyPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        onItemClick.onItemClick(position);
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int position);
    }

}
