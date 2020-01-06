package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.IndustryOneAdapter;
import com.dan.yousuanshi.module.addressbook.adapter.IndustryTwoAdapter;
import com.dan.yousuanshi.module.addressbook.bean.IndustryBean;
import com.dan.yousuanshi.module.addressbook.presenter.ChooseIndustryPresenter;
import com.dan.yousuanshi.module.addressbook.view.ChooseIndustryView;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseIndustryActivity extends BaseActivity<ChooseIndustryPresenter> implements ChooseIndustryView {

    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.recyclerView_one)
    RecyclerView recyclerViewOne;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.recyclerView_two)
    RecyclerView recyclerViewTwo;

    private List<IndustryBean> industryList = new ArrayList<>();
    private List<IndustryBean.ChildBean> industryChildList = new ArrayList<>();
    private IndustryOneAdapter adapter;
    private IndustryTwoAdapter adapterTwo;
    private int position = 0;//一级菜单下标

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_industry;
    }

    @Nullable
    @Override
    protected ChooseIndustryPresenter initPresenter() {
        return new ChooseIndustryPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {
        presenter.getIndustry();
    }

    @Override
    public void getIndustrySuccess(int code, final List<IndustryBean> industryBean) {
        industryBean.get(0).setSelect(true);
        industryList.addAll(industryBean);
        industryChildList.addAll(industryBean.get(0).getChild());
        recyclerViewOne.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTwo.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new IndustryOneAdapter(this,industryList);
        adapterTwo = new IndustryTwoAdapter(this,industryChildList);
        recyclerViewOne.setAdapter(adapter);
        recyclerViewTwo.setAdapter(adapterTwo);
        adapter.notifyDataSetChanged();
        adapterTwo.notifyDataSetChanged();
        adapter.setOnItemClick(new IndustryOneAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                ChooseIndustryActivity.this.position = position;
                industryList.get(position).setSelect(true);
                industryChildList.clear();
                industryChildList.addAll(industryList.get(position).getChild());
                tvIndustry.setText(industryList.get(position).getIndustry());
                for(IndustryBean item : industryList){
                    if(!item.equals(industryBean.get(position))){
                        item.setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                adapterTwo.notifyDataSetChanged();
            }
        });
        adapterTwo.setOnItemClick(new IndustryTwoAdapter.OnItemClick() {
            @Override
            public void chooseIndustry(View view, int position) {
                Intent intent = new Intent(ChooseIndustryActivity.this,CreateTeamActivity.class);
                IndustryBean industryBean1 = new IndustryBean();
                industryBean1 = industryList.get(ChooseIndustryActivity.this.position);
                List<IndustryBean.ChildBean> list = new ArrayList<>();
                list.add(industryChildList.get(position));
                industryBean1.setChild(list);
                intent.putExtra("data", (Parcelable) industryBean1);
                setResult(1, intent);
                finish();
            }
        });
    }

    @Override
    public void getIndustryFailed(int code, String message) {
        ToastUtils.showToast(this, "code:" + code + "\t" + message);
    }


    @OnClick({R.id.ll_back, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                Intent intent = new Intent(this,SearchIndustryActivity.class);
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) industryList);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            Intent intent = new Intent(ChooseIndustryActivity.this,CreateTeamActivity.class);
            intent.putExtra("data", (Parcelable) data.getParcelableExtra("data"));
            setResult(1, intent);
            finish();
        }
    }
}
