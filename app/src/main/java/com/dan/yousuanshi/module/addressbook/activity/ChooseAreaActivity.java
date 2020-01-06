package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.ChooseCountryAdapter;
import com.dan.yousuanshi.module.addressbook.bean.CountryBean;
import com.dan.yousuanshi.module.addressbook.presenter.ChooseCountryPresenter;
import com.dan.yousuanshi.module.addressbook.view.ChooseCountryView;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseAreaActivity extends BaseActivity<ChooseCountryPresenter> implements ChooseCountryView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<CountryBean> areaList = new ArrayList<>();
    private ChooseCountryAdapter adapter;
    private CountryBean city;
    private int type;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_area;
    }

    @Nullable
    @Override
    protected ChooseCountryPresenter initPresenter() {
        return new ChooseCountryPresenter();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChooseCountryAdapter(this, areaList);
        adapter.setOnItemClick(new ChooseCountryAdapter.OnItemClick() {
            @Override
            public void chooseCountry(View v, int position) {
                Intent intent = new Intent(ChooseAreaActivity.this,ChooseCityActivity.class);
                intent.putExtra("area", (Parcelable) areaList.get(position));
                intent.putExtra("city", (Parcelable) city);
                setResult(1,intent);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        Intent intent = getIntent();
        city = intent.getParcelableExtra("city");
        type = intent.getIntExtra("type",0);
        Map<String, String> map = new HashMap<>();
        map.put("parentid", String.valueOf(city.getId()));
        presenter.getCountry(map);
    }

    @Override
    public void getCountrySuccess(int code, List<CountryBean> data) {
        if (code == 0) {
            if (data.size() > 0) {
                areaList.addAll(data);
                adapter.notifyDataSetChanged();
            }else{
                Intent intent = new Intent(ChooseAreaActivity.this,ChooseCityActivity.class);
                intent.putExtra("area", (Parcelable) city);
                intent.putExtra("city", (Parcelable) city);
                intent.putExtra("type",type);
                setResult(1,intent);
                finish();
            }
        }
    }

    @Override
    public void getCountryFailed(int code, String message) {
        ToastUtils.showToast(this, "code:" + code + "\t" + message);
    }

    @Override
    public void updateUserInfoSuccess(int code, List list) {

    }

    @Override
    public void updateUserInfoFailed(int code, String msg) {

    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }
}
