package com.dan.yousuanshi.module.shared.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.shared.presenter.SettingWorkBenchIsLookActivityPresenter;
import com.dan.yousuanshi.module.shared.view.SettingWorkBenchIsLookActivityView;
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

public class SettingWorkBenchIsLookActivity extends BaseActivity<SettingWorkBenchIsLookActivityPresenter> implements SettingWorkBenchIsLookActivityView {

    @BindView(R.id.ll_all_look)
    LinearLayout llAllLook;
    @BindView(R.id.ll_part_look)
    LinearLayout llPartLook;
    @BindView(R.id.ll_admin_look)
    LinearLayout llAdminLook;

    private int modelId;
    private List<Integer> userList;
    private List<Integer> departmentList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_work_bench_is_look;
    }

    @Nullable
    @Override
    protected SettingWorkBenchIsLookActivityPresenter initPresenter() {
        return new SettingWorkBenchIsLookActivityPresenter();
    }

    @Override
    protected void initView() {
        modelId = getIntent().getIntExtra("modelId",0);
        userList = getIntent().getIntegerArrayListExtra("userList");
        departmentList = getIntent().getIntegerArrayListExtra("departmentList");
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void setCompanyModelSuccess(int code, BaseBean data) {
        finish();
    }

    @Override
    public void setCompanyModelFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_all_look, R.id.ll_part_look, R.id.ll_admin_look})
    public void onViewClicked(View view) {
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("modelId",modelId);
        map.put("userList","[]");
        map.put("departmentList","[]");
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_all_look:
                map.put("setType",0);
                presenter.setCompanyModel(map);
                break;
            case R.id.ll_part_look:
                Intent intent = new Intent(getActivity(),SharedTeamPeopleActivity.class);
                intent.putIntegerArrayListExtra("userList", (ArrayList<Integer>) userList);
                intent.putIntegerArrayListExtra("departmentList", (ArrayList<Integer>) departmentList);
                startActivityForResult(intent,1);
                break;
            case R.id.ll_admin_look:
                map.put("setType",1);
                presenter.setCompanyModel(map);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("modelId",modelId);
        map.put("setType",2);
        List<Integer> list = new ArrayList<>();
        if (requestCode == 1 && resultCode == 1) {
            List<TeamPeopleBean> teamPeopleList = data.getParcelableArrayListExtra("data");
            for(TeamPeopleBean item : teamPeopleList){
                list.add(item.getUser_id());
            }
            map.put("userList",new Gson().toJson(list));
            map.put("departmentList","[]");
            presenter.setCompanyModel(map);
        }else if(requestCode == 1 && resultCode == 2){
            List<DepartmentBean> departmentList = data.getParcelableArrayListExtra("data");
            for(DepartmentBean item : departmentList){
                list.add(item.getDid());
            }
            map.put("userList","[]");
            map.put("departmentList",new Gson().toJson(list));
            presenter.setCompanyModel(map);
        }
    }
}
