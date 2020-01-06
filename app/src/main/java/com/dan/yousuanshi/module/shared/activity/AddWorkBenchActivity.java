package com.dan.yousuanshi.module.shared.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.shared.adapter.AddWorkBenchAdapter;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;
import com.dan.yousuanshi.module.shared.presenter.AddWorkBenchActivityPresenter;
import com.dan.yousuanshi.module.shared.view.AddWorkBenchActivityView;
import com.dan.yousuanshi.utils.DpToPxUtils;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.StringUtils;
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

public class AddWorkBenchActivity extends BaseActivity<AddWorkBenchActivityPresenter> implements AddWorkBenchActivityView {

    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_new_model)
    LinearLayout llNewModel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private AddWorkBenchAdapter adapter;
    private List<AddWorkBenchBean> modelList = new ArrayList<>();
    private Dialog dialog;
    private int modelId;
    private int parentId;
    private int setType;
    private List<Integer> userList = new ArrayList<>();
    private List<Integer> departmentIdList = new ArrayList<>();
    private int companyId;
    private boolean isSetting = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_work_bench;
    }

    @Nullable
    @Override
    protected AddWorkBenchActivityPresenter initPresenter() {
        return new AddWorkBenchActivityPresenter();
    }

    @Override
    protected void initView() {
        String team = SPUtils.getInstance().get(Constant.SHARED_COMPANY, "");
        if (StringUtils.isEmpty(team)) {
            companyId = UserUtils.getInstance().getUserBean().getUser_company();
        } else {
            companyId = new Gson().fromJson(team, MyTeamBean.class).getCompanyId();
        }
        parentId = getIntent().getIntExtra("parentId", 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddWorkBenchAdapter(this, modelList);
        adapter.setOnItemClick(new AddWorkBenchAdapter.OnItemClick() {
            @Override
            public void onItemClick(int modelId, int parentId) {
                AddWorkBenchActivity.this.modelId = modelId;
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

    @Override
    protected void onResume() {
        super.onResume();
        modelList.clear();
        presenter.getModelList(companyId);
    }

    @Override
    public void getModelListSuccess(int code, List<AddWorkBenchBean> data) {
        modelList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getModelListFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void setCompanyModelSuccess(int code, BaseBean data) {
        onResume();
    }

    @Override
    public void setCompanyModelFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void onLineModelSuccess(int code, BaseBean data) {
        if (isSetting) {
            Map<String, Object> map = new HashMap<>();//设置模块可见
            map.put("companyId", companyId);
            map.put("modelId", modelId);
            map.put("setType", setType);
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
        } else {
            onResume();
        }
    }

    @Override
    public void onLineModelFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_search, R.id.ll_new_model})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                Intent intent = new Intent(this, SearchModelActivity.class);
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) modelList);
                intent.putExtra("parentId", parentId);
                intent.putExtra("companyId",companyId);
                startActivity(intent);
                break;
            case R.id.ll_new_model:
                startActivity(new Intent(this, NewModelActivity.class));
                break;
        }
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_add_work_bench);
            RadioGroup rgChoose = dialog.findViewById(R.id.rg_choose_gender);
            RadioButton rbAllPeople = dialog.findViewById(R.id.rb_all_people);
            RadioButton rbPartPeople = dialog.findViewById(R.id.rb_part_people);
            RadioButton rbAdminPeople = dialog.findViewById(R.id.rb_admin);
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(), 15), DpToPxUtils.dip2px(getActivity(), 15));//设置大小  ，分别表示x ,y 宽，高
            Drawable drawable2 = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable2.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(), 15), DpToPxUtils.dip2px(getActivity(), 15));//设置大小  ，分别表示x ,y 宽，高
            Drawable drawable3 = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable3.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(), 15), DpToPxUtils.dip2px(getActivity(), 15));//设置大小  ，分别表示x ,y 宽，高
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
