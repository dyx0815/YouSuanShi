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

public class ChooseCityActivity extends BaseActivity<ChooseCountryPresenter> implements ChooseCountryView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<CountryBean> cityList = new ArrayList<>();
    private ChooseCountryAdapter adapter;
    private CountryBean province;
    private CountryBean area = new CountryBean();
    private CountryBean city = new CountryBean();
    private int type;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_city;
    }

    @Nullable
    @Override
    protected ChooseCountryPresenter initPresenter() {
        return new ChooseCountryPresenter();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChooseCountryAdapter(this, cityList);
        adapter.setOnItemClick(new ChooseCountryAdapter.OnItemClick() {
            @Override
            public void chooseCountry(View v, int position) {
                CountryBean city = cityList.get(position);
                if(province.getName().equals("北京市")||province.getName().equals("天津市")||province.getName().equals("重庆市")||province.getName().equals("上海市")){
                    Intent intent = new Intent(ChooseCityActivity.this,ChooseCountryActivity.class);
                    intent.putExtra("city", (Parcelable) city);
                    intent.putExtra("area", (Parcelable) city);
                    intent.putExtra("type",type);
                    setResult(1,intent);
                    finish();
                }else{
                    Intent intent = new Intent(ChooseCityActivity.this,ChooseAreaActivity.class);
                    intent.putExtra("city", (Parcelable) cityList.get(position));
                    intent.putExtra("type",type);
                    startActivityForResult(intent,1);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        Intent intent = getIntent();
        province = intent.getParcelableExtra("province");
        type = intent.getIntExtra("type",0);
        Map<String, String> map = new HashMap<>();
        map.put("parentid", String.valueOf(province.getId()));
        presenter.getCountry(map);
    }

    @Override
    public void getCountrySuccess(int code, List<CountryBean> data) {
        if (code == 0) {
            if (data.size() > 0) {
                cityList.addAll(data);
                adapter.notifyDataSetChanged();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            area = data.getParcelableExtra("area");
            city = data.getParcelableExtra("city");
            Intent intent = new Intent(ChooseCityActivity.this,ChooseCountryActivity.class);
            intent.putExtra("city", (Parcelable) city);
            intent.putExtra("area", (Parcelable) area);
            intent.putExtra("type",type);
            setResult(1,intent);
            finish();
        }
    }
}
