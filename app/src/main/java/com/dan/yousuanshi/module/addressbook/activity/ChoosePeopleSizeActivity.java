package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.ChoosePeopleSizeAdapter;
import com.dan.yousuanshi.module.addressbook.bean.PeopleSizeBean;
import com.dan.yousuanshi.module.addressbook.presenter.ChoosePeopleSizePresenter;
import com.dan.yousuanshi.module.addressbook.view.ChoosePeopleSizeView;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChoosePeopleSizeActivity extends BaseActivity<ChoosePeopleSizePresenter> implements ChoosePeopleSizeView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ChoosePeopleSizeAdapter adapter;
    private List<PeopleSizeBean> peopleSizeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_people_size;
    }

    @Nullable
    @Override
    protected ChoosePeopleSizePresenter initPresenter() {
        return new ChoosePeopleSizePresenter();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChoosePeopleSizeAdapter(this, peopleSizeList);
        adapter.setOnItemClick(new ChoosePeopleSizeAdapter.OnItemClick() {
            @Override
            public void choosePeopleSize(View v, int position) {
                Intent intent = new Intent(ChoosePeopleSizeActivity.this,CreateTeamActivity.class);
                intent.putExtra("data", (Parcelable) peopleSizeList.get(position));
                setResult(2,intent);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        presenter.getPeopleSize();
    }


    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getPeopleSizeSuccess(int code, List<PeopleSizeBean> data) {
        if (code == 0) {
            if (data.size() > 0) {
                peopleSizeList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getPeopleSizeFailed(int code, String msg) {
        ToastUtils.showToast(this, "code:" + code + "\t" + msg);
    }
}
