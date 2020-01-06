package com.dan.yousuanshi.module.shared.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.shared.adapter.WorkBenchManagerAdapter;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;
import com.dan.yousuanshi.module.shared.presenter.WorkBenchManagerActivityPresenter;
import com.dan.yousuanshi.module.shared.view.WorkBenchManagerActivityView;
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

public class WorkBenchManagerActivity extends BaseActivity<WorkBenchManagerActivityPresenter> implements WorkBenchManagerActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.ll_add_group)
    LinearLayout llAddGroup;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int companyId;
    private WorkBenchManagerAdapter adapter;
    private List<AddWorkBenchBean> workBenchList = new ArrayList<>();
    private Dialog removeDialog;
    private Dialog removeDialog2;
    private AddWorkBenchBean.ChildrenBean childrenBean;//要删除的
    private AddWorkBenchBean addWorkBenchBean;//要删除的一级菜单

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_bench_manager;
    }

    @Nullable
    @Override
    protected WorkBenchManagerActivityPresenter initPresenter() {
        return new WorkBenchManagerActivityPresenter();
    }

    @Override
    protected void initView() {
        String team = SPUtils.getInstance().get(Constant.SHARED_COMPANY,"");
        if(StringUtils.isEmpty(team)){
            companyId = UserUtils.getInstance().getUserBean().getUser_company();
        }else{
            companyId = new Gson().fromJson(team, MyTeamBean.class).getCompanyId();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkBenchManagerAdapter(this, workBenchList);
        adapter.setOnItemClick(new WorkBenchManagerAdapter.OnItemClick() {
            @Override
            public void removeModel(AddWorkBenchBean.ChildrenBean childrenBean) {
                WorkBenchManagerActivity.this.childrenBean = childrenBean;
                showRemoveDialog();
            }

            @Override
            public void removeWorkBench(AddWorkBenchBean addWorkBenchBean) {
                WorkBenchManagerActivity.this.addWorkBenchBean = addWorkBenchBean;
                showRemoveDialog2();
            }

            @Override
            public void updateModelName(AddWorkBenchBean addWorkBenchBean) {
                Intent intent = new Intent(getContext(), UpdateModelNameActivity.class);
                intent.putExtra("companyId",companyId);
                intent.putExtra("modelId",addWorkBenchBean.getId());
                intent.putExtra("modelName",addWorkBenchBean.getModel_name());
                startActivity(intent);
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
        workBenchList.clear();
        presenter.getWorkbenchModel(companyId);
    }

    @Override
    public void getWorkBenchModelSuccess(int code, List<AddWorkBenchBean> data) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).getChildren().add(new AddWorkBenchBean.ChildrenBean(999999, "添加", 1, "https://dzcdn.zixuezhilu.com/icon_shared_add.png"));
        }
        workBenchList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getWorkBenchModelFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void offLineModelSuccess(int code, BaseBean data) {
        onResume();
    }

    @Override
    public void offLineModelFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @Override
    public void deleteWorkBenchSuccess(int code, BaseBean data) {
        onResume();
    }

    @Override
    public void deleteWorkBenchFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.ll_add_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit://排序
                Intent intent1 = new Intent(this,SortModelActivity.class);
                intent1.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) workBenchList);
                intent1.putExtra("companyId",companyId);
                startActivity(intent1);
                break;
            case R.id.ll_add_group://新增分组
                Intent intent = new Intent(this,AddModelActivity.class);
                intent.putExtra("companyId",companyId);
                startActivity(intent);
                break;
        }
    }

    private void showRemoveDialog(){
        if(removeDialog == null){
            removeDialog = new Dialog(this);
            removeDialog.setContentView(R.layout.dialog_clear_chat_list);
            TextView tvTitle = removeDialog.findViewById(R.id.tv_title);
            TextView tvCancel = removeDialog.findViewById(R.id.tv_cancel);
            TextView tvSure = removeDialog.findViewById(R.id.tv_sure);
            tvTitle.setText("删除后，您可点击添加，重新添 加所需组件");
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeDialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeDialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("companyId",companyId);
                    map.put("modelId",childrenBean.getId());
                    presenter.offLineModel(map);
                }
            });
        }
        removeDialog.show();
    }

    private void showRemoveDialog2(){
        if(removeDialog2 == null){
            removeDialog2 = new Dialog(this);
            removeDialog2.setContentView(R.layout.dialog_clear_chat_list);
            TextView tvTitle = removeDialog2.findViewById(R.id.tv_title);
            TextView tvCancel = removeDialog2.findViewById(R.id.tv_cancel);
            TextView tvSure = removeDialog2.findViewById(R.id.tv_sure);
            tvTitle.setText("删除后，该分组下面包括的组件 将一并移除，可点击添加重新进 行添加组件");
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeDialog2.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeDialog2.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("companyId",companyId);
                    map.put("modelId",addWorkBenchBean.getId());
                    List<Integer> modelChildList = new ArrayList<>();
                    for(AddWorkBenchBean.ChildrenBean item : addWorkBenchBean.getChildren()){
                        if(!"添加".equals(item.getModel_name())){
                            modelChildList.add(item.getId());
                        }
                    }
                    map.put("modelChildList",new Gson().toJson(modelChildList));
                    presenter.deleteWorkBench(map);
                }
            });
        }
        removeDialog2.show();
    }
}
