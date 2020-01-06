package com.dan.yousuanshi.module.shared.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.shared.adapter.AddWorkBenchAdapter;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;
import com.dan.yousuanshi.module.shared.presenter.SearchModelActivityPresenter;
import com.dan.yousuanshi.module.shared.view.SearchModelActivityView;
import com.dan.yousuanshi.utils.DpToPxUtils;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class SearchModelActivity extends BaseActivity<SearchModelActivityPresenter> implements SearchModelActivityView {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<AddWorkBenchBean> data;
    private List<AddWorkBenchBean> searchData = new ArrayList<>();
    private AddWorkBenchAdapter adapter;
    private int modelId;
    private int parentId;
    private Dialog dialog;
    private int setType;
    private int companyId;
    private boolean isSetting;
    private List<Integer> userList = new ArrayList<>();
    private List<Integer> departmentIdList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_model;
    }

    @Nullable
    @Override
    protected SearchModelActivityPresenter initPresenter() {
        return new SearchModelActivityPresenter();
    }

    @Override
    protected void initView() {
        companyId = getIntent().getIntExtra("companyId",0);
        data = getIntent().getParcelableArrayListExtra("data");
        parentId = getIntent().getIntExtra("parentId",0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddWorkBenchAdapter(this,searchData);
        adapter.setOnItemClick(new AddWorkBenchAdapter.OnItemClick() {
            @Override
            public void onItemClick(int modelId, int parentId) {
                SearchModelActivity.this.modelId = modelId;
                showDialog();
            }

            @Override
            public void addModel2(int modelId, int parentId) {
                Map<String, Object> map = new HashMap<>();
                map.put("companyId", companyId);
                map.put("modelId", modelId);
                map.put("parentId", parentId);
                presenter.onLineModel(map);
                isSetting = false;
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_search:
                if(StringUtils.isEmpty(etSearch.getText().toString())){
                    ToastUtils.showToast(this,"请输入搜索条件！");
                    return;
                }
                searchData.clear();
                for(AddWorkBenchBean item : data){
                    if(item.getModel_name().contains(etSearch.getText().toString())){
                        searchData.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void showDialog(){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_add_work_bench);
            RadioGroup rgChoose = dialog.findViewById(R.id.rg_choose_gender);
            RadioButton rbAllPeople = dialog.findViewById(R.id.rb_all_people);
            RadioButton rbPartPeople = dialog.findViewById(R.id.rb_part_people);
            RadioButton rbAdminPeople = dialog.findViewById(R.id.rb_admin);
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(),15), DpToPxUtils.dip2px(getActivity(),15));//设置大小  ，分别表示x ,y 宽，高
            Drawable drawable2 = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable2.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(),15), DpToPxUtils.dip2px(getActivity(),15));//设置大小  ，分别表示x ,y 宽，高
            Drawable drawable3 = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable3.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(),15), DpToPxUtils.dip2px(getActivity(),15));//设置大小  ，分别表示x ,y 宽，高
            rbAllPeople.setCompoundDrawables(drawable, null, null, null);//选择位置上下左右
            rbPartPeople.setCompoundDrawables(drawable2, null, null, null);//选择位置上下左右
            rbAdminPeople.setCompoundDrawables(drawable3, null, null, null);//选择位置上下左右
            TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            TextView tvSure = dialog.findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    isSetting = true;
                    Map<String, Object> map = new HashMap<>();//上线模块
                    map.put("companyId", companyId);
                    map.put("modelId", modelId);
                    map.put("parentId", parentId);
                    switch (rgChoose.getCheckedRadioButtonId()) {
                        case R.id.rb_all_people:
                            setType = 0;
                            presenter.onLineModel(map);
                            break;
                        case R.id.rb_part_people:
                            startActivityForResult(new Intent(getActivity(), SharedTeamPeopleActivity.class), 1);
                            break;
                        case R.id.rb_admin:
                            setType = 1;
                            presenter.onLineModel(map);
                            break;
                    }
                }
            });
        }
        dialog.show();
    }

    @Override
    public void setCompanyModelSuccess(int code, BaseBean data) {
        finish();
    }

    @Override
    public void setCompanyModelFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @Override
    public void onLineModelSuccess(int code, BaseBean data) {
        if(isSetting){
            Map<String,Object> map = new HashMap<>();//设置模块可见
            map.put("companyId",companyId);
            map.put("modelId",modelId);
            map.put("setType",setType);
            if(userList.size() == 0){
                map.put("userList", "[]");
            }else{
                map.put("userList",new Gson().toJson(userList));
            }
            if(departmentIdList.size() == 0){
                map.put("departmentList", "[]");
            }else{
                map.put("userList",new Gson().toJson(departmentIdList));
            }
            presenter.setCompanyModel(map);
        }else{
            finish();
        }
    }

    @Override
    public void onLineModelFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isSetting = true;
        setType = 2;
        Map<String, Object> map = new HashMap<>();//上线模块
        map.put("companyId", companyId);
        map.put("modelId", modelId);
        map.put("parentId", parentId);
        List<Integer> list = new ArrayList<>();
        if (requestCode == 1 && resultCode == 1) {
            List<TeamPeopleBean> teamPeopleList = data.getParcelableArrayListExtra("data");
            for (TeamPeopleBean item : teamPeopleList) {
                list.add(item.getUser_id());
            }
            userList.addAll(list);
            departmentIdList.clear();
            presenter.onLineModel(map);
        } else if (requestCode == 1 && resultCode == 2) {
            List<DepartmentBean> departmentList = data.getParcelableArrayListExtra("data");
            for (DepartmentBean item : departmentList) {
                list.add(item.getDid());
            }
            departmentIdList.addAll(list);
            userList.clear();
            presenter.onLineModel(map);
        }
    }
}
