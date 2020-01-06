package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.AddTeamPeopleAdapter;
import com.dan.yousuanshi.module.addressbook.bean.CountryBean;
import com.dan.yousuanshi.module.addressbook.bean.IndustryBean;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.bean.PeopleSizeBean;
import com.dan.yousuanshi.module.addressbook.presenter.CreateTeamActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.CreateTeamActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateTeamActivity extends BaseActivity<CreateTeamActivityPresenter> implements CreateTeamActivityView {


    @BindView(R.id.et_team_name)
    EditText etTeamName;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_people_size)
    TextView tvPeopleSize;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_my_position)
    TextView tvMyPosition;
    @BindView(R.id.ll_add_admin)
    LinearLayout llAddAdmin;
    @BindView(R.id.ll_add_people)
    LinearLayout llAddPeople;
    @BindView(R.id.admin_recyclerView)
    RecyclerView adminRecyclerView;
    @BindView(R.id.people_recyclerView)
    RecyclerView peopleRecyclerView;

    private IndustryBean industryBean;//行业实体
    private PeopleSizeBean peopleSizeBean;//人员规模实体
    private CountryBean countryBean;//省
    private CountryBean city;//市
    private CountryBean area;//区
    private List<MyFriendBean> adminList = new ArrayList<>();
    private List<MyFriendBean> peopleList = new ArrayList<>();
    private AddTeamPeopleAdapter adminAdapter;
    private AddTeamPeopleAdapter peopleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_team;
    }

    @Nullable
    @Override
    protected CreateTeamActivityPresenter initPresenter() {
        return new CreateTeamActivityPresenter();
    }

    @Override
    protected void initView() {
        adminAdapter = new AddTeamPeopleAdapter(adminList, this);
        peopleAdapter = new AddTeamPeopleAdapter(peopleList, this);
        adminAdapter.setOnItemClick(new AddTeamPeopleAdapter.OnItemClick() {
            @Override
            public void deletePeople(int position) {
                adminList.remove(position);
                adminAdapter.notifyDataSetChanged();
            }
        });
        peopleAdapter.setOnItemClick(new AddTeamPeopleAdapter.OnItemClick() {
            @Override
            public void deletePeople(int position) {
                peopleList.remove(position);
                peopleAdapter.notifyDataSetChanged();
            }
        });
        adminRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminRecyclerView.setAdapter(adminAdapter);
        peopleRecyclerView.setAdapter(peopleAdapter);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_industry, R.id.ll_people_size, R.id.ll_address, R.id.ll_my_position, R.id.ll_add_admin, R.id.ll_add_people, R.id.tv_create_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back://返回按钮
                finish();
                break;
            case R.id.ll_industry://行业
                startActivityForResult(new Intent(this, ChooseIndustryActivity.class), 1);
                break;
            case R.id.ll_people_size://人员规模
                startActivityForResult(new Intent(this, ChoosePeopleSizeActivity.class), 1);
                break;
            case R.id.ll_address://地区
                startActivityForResult(new Intent(this, ChooseCountryActivity.class), 1);
                break;
            case R.id.ll_my_position:
                break;
            case R.id.ll_add_admin://添加管理员
                Intent intent = new Intent(this, ChooseMyFriendActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_add_people://添加成员
                startActivityForResult(new Intent(this, ChooseMyFriendActivity.class), 2);
                break;
            case R.id.tv_create_now:
                Map<String, String> map = new HashMap<>();
                map.put("teamName", etTeamName.getText().toString());
                map.put("industry", String.valueOf(industryBean.getChild().get(0).getId()));
                map.put("peopleNums", String.valueOf(peopleSizeBean.getId()));
                map.put("country", "1");
                map.put("province", String.valueOf(countryBean.getId()));
                map.put("city", String.valueOf(city.getId()));
                map.put("district", city.getId() == area.getId() ? String.valueOf(area.getId()) : "0");
                Gson gson = new Gson();
                List<Integer> adminIdList = new ArrayList<>();
                List<Integer> peopleIdList = new ArrayList<>();
                for(MyFriendBean item : adminList){
                    adminIdList.add(item.getUser_id());
                }
                for(MyFriendBean item : peopleList){
                    peopleIdList.add(item.getUser_id());
                }
                map.put("myTeam", gson.toJson(peopleIdList));
                map.put("myMaster", gson.toJson(adminIdList));
                map.put("teamType", "1");
                presenter.createTeam(map);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {//行业
            industryBean = data.getParcelableExtra("data");
            tvIndustry.setText(industryBean.getChild().get(0).getIndustry());
        } else if (resultCode == 2) {//人员规模
            peopleSizeBean = data.getParcelableExtra("data");
            tvPeopleSize.setText(peopleSizeBean.getPersonnel_str());
        } else if (resultCode == 3) {//地区
            countryBean = data.getParcelableExtra("country");
            city = data.getParcelableExtra("city");
            area = data.getParcelableExtra("area");
            if (countryBean.getName().equals("北京市") || countryBean.getName().equals("天津市") || countryBean.getName().equals("重庆市") || countryBean.getName().equals("上海市")) {
                tvAddress.setText(countryBean.getName() + "-" + city.getName());
            } else {
                tvAddress.setText(countryBean.getName() + "-" + city.getName() + "-" + area.getName());
            }
        } else if (requestCode == 1 && resultCode == 4) {//添加管理员
            adminList.addAll(data.getParcelableArrayListExtra("friendList"));
            adminAdapter.notifyDataSetChanged();
        } else if (requestCode == 2 && resultCode == 4) {//添加成员
            peopleList.addAll(data.getParcelableArrayListExtra("friendList"));
            peopleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void createTeamSuccess(int code, List data) {
        ToastUtils.showToast(getApplicationContext(), "创建团队成功");
        finish();
    }

    @Override
    public void createTeamFailed(int code, String msg) {
        Log.e("CreateTeamActivity", msg);
    }
}
