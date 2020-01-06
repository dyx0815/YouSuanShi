package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class ChooseCountryActivity extends BaseActivity<ChooseCountryPresenter> implements ChooseCountryView {

    @BindView(R.id.tv_choose_country)
    TextView tvChooseCountry;
    @BindView(R.id.ll_choose_country)
    LinearLayout llChooseCountry;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<CountryBean> countryList = new ArrayList<>();
    private ChooseCountryAdapter adapter;
    private CountryBean countryBean;
    private CountryBean city;
    private CountryBean area;
    private int type;//判断是从哪个页面过来的 0为创建团队页面 1为修改个人信息页面

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_country_activity;
    }

    @Nullable
    @Override
    protected ChooseCountryPresenter initPresenter() {
        return new ChooseCountryPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChooseCountryAdapter(this, countryList);
        adapter.setOnItemClick(new ChooseCountryAdapter.OnItemClick() {
            @Override
            public void chooseCountry(View v, int position) {
                countryBean = countryList.get(position);
                Intent intent = new Intent(ChooseCountryActivity.this,ChooseCityActivity.class);
                intent.putExtra("province", (Parcelable) countryBean);
                intent.putExtra("type",type);
                startActivityForResult(intent,1);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        Map<String,String> map = new HashMap<>();
        map.put("parentid","0");
        presenter.getCountry(map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (countryBean != null) {
            tvChooseCountry.setText(countryBean.getName());
            llChooseCountry.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getCountrySuccess(int code, List<CountryBean> data) {
        if (code == 0) {
            if (data.size() > 0) {
                countryList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getCountryFailed(int code, String message) {
        ToastUtils.showToast(this, "code:" + code + "\t" + message);
        finish();
    }

    @Override
    public void updateUserInfoSuccess(int code, List list) {
        finish();
    }

    @Override
    public void updateUserInfoFailed(int code, String msg) {
        ToastUtils.showToast(this, "code:" + code + "\t" + msg);
        Log.e("ChooseCountryActivity","code:" + code + "\t" + msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            city = data.getParcelableExtra("city");
            area = data.getParcelableExtra("area");
            if(type == 1){
                Map<String,String> map = new HashMap<>();
                map.put("setType","10");
                if(area.getId() == city.getId()){
                    map.put("setValue","1-"+countryBean.getId()+"-"+city.getId()+"-0");
                    map.put("setValueStr",countryBean.getName()+"-"+city.getName());
                }else{
                    map.put("setValue","1-"+countryBean.getId()+"-"+city.getId()+"-"+area.getId());
                    map.put("setValueStr",countryBean.getName()+"-"+city.getName()+"-"+area.getName());
                }
                presenter.updateUserInfo(map);
            }else{
                Intent intent = new Intent(ChooseCountryActivity.this,CreateTeamActivity.class);
                intent.putExtra("country", (Parcelable) countryBean);
                intent.putExtra("city", (Parcelable) city);
                intent.putExtra("area", (Parcelable) area);
                setResult(3,intent);
                finish();
            }
        }
    }
}
