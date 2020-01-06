package com.dan.yousuanshi.module.addressbook.activity;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.adapter.PartPurviewAdapter;
import com.dan.yousuanshi.module.addressbook.bean.PartPurviewBean;
import com.dan.yousuanshi.module.addressbook.presenter.PartPurviewActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.PartPurviewActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class PartPurviewActivity extends BaseActivity<PartPurviewActivityPresenter> implements PartPurviewActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<PartPurviewBean.PowerListBean> purviewList = new ArrayList<>();
    private PartPurviewAdapter adapter;
    private int masterId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_part_purview;
    }

    @Nullable
    @Override
    protected PartPurviewActivityPresenter initPresenter() {
        return new PartPurviewActivityPresenter();
    }

    @Override
    protected void initView() {
        masterId = getIntent().getIntExtra("masterId",0);
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("masterId",masterId);
        presenter.getPartPurview(map);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PartPurviewAdapter(this,purviewList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getPartPurviewSuccess(int code, PartPurviewBean data) {
        purviewList.addAll(data.getPowerList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getPartPurviewFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void addSonAdminSuccess(int code, BaseBean data) {
        MyApplication.addActivity(this);
        MyApplication.clearActivity();
    }

    @Override
    public void addSonAdminFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                Map<String,Object> map = new HashMap<>();
                map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
                map.put("masterId",masterId);
                List<Integer> purview = new ArrayList<>();
                for(PartPurviewBean.PowerListBean item : adapter.getData()){
                    for(PartPurviewBean.PowerListBean.ChildrenBean item1 : item.getChildren()){
                        if(item1.getIs_had() == 1){
                            purview.add(item1.getId());
                            if(!purview.contains(item.getId())){
                                purview.add(item.getId());
                            }
                        }
                    }
                }
                int count = adapter.getData().size();
                for(PartPurviewBean.PowerListBean item : adapter.getData()){
                    count = count+item.getChildren().size();
                }
                if(purview.size() == count){
                    map.put("masterPower","[]");
                    map.put("isAllPower",1);
                }else{
                    map.put("masterPower",new Gson().toJson(purview));
                    map.put("isAllPower",0);
                }
                presenter.addSonAdmin(map);
                break;
        }
    }
}
