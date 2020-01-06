package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
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
import com.dan.yousuanshi.module.addressbook.adapter.TeamPeopleManagerAdapter;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.TeamPeopleManagerActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.TeamPeopleManagerActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamPeopleManagerActivity extends BaseActivity<TeamPeopleManagerActivityPresenter> implements TeamPeopleManagerActivityView {

    @BindView(R.id.tv_operate)
    TextView tvOperate;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<TeamPeopleBean> teamPeopleList = new ArrayList<>();
    private TeamPeopleManagerAdapter adapter;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_people_manager;
    }

    @Nullable
    @Override
    protected TeamPeopleManagerActivityPresenter initPresenter() {
        return new TeamPeopleManagerActivityPresenter();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamPeopleManagerAdapter(this, teamPeopleList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        presenter.getTeamPeople(map);
    }

    @Override
    public void getTeamPeopleSuccess(int code, List<TeamPeopleBean> data) {
        teamPeopleList.clear();
        teamPeopleList.addAll(data);
        for (int i = 0; i < teamPeopleList.size(); i++) {
            if(teamPeopleList.get(i).getIs_create() == 1){
                TeamPeopleBean teamPeopleBean = teamPeopleList.get(i);
                teamPeopleList.remove(i);
                teamPeopleList.add(0,teamPeopleBean);
            }else if(teamPeopleList.get(i).getIs_master() == 1){
                TeamPeopleBean teamPeopleBean = teamPeopleList.get(i);
                teamPeopleList.remove(i);
                if(teamPeopleList.get(0).getIs_create() == 1){
                    teamPeopleList.add(1,teamPeopleBean);
                }else{
                    teamPeopleList.add(0,teamPeopleBean);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getTeamPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取公司成员失败：" + code + msg);
        Log.e("TeamPeople", "获取公司成员失败：" + code + msg);
    }

    @Override
    public void addTeamPeopleSuccess(int code, List list) {
        onResume();
    }

    @Override
    public void addTeamPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this, "添加成员失败：" + code + msg);
        Log.e("TeamPeople", "添加成员失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_operate, R.id.ll_search,R.id.rl_add_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_operate:
                showDialog();
                break;
            case R.id.ll_search:
                Intent intent = new Intent(this,SearchTeamPeopleActivity.class);
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) teamPeopleList);
                startActivity(intent);
                break;
            case R.id.rl_add_people://添加成员
                List<MyFriendBean> friendList = new ArrayList<>();
                for(TeamPeopleBean item : teamPeopleList){
                    friendList.add(new MyFriendBean(item.getUser_id(),item.getReal_name(),item.getUser_portrait()));
                }
                Intent intent1 = new Intent(this,ChooseMyFriendActivity.class);
                intent1.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivityForResult(intent1,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 4){
            List<MyFriendBean> friendList = data.getParcelableArrayListExtra("friendList");
            List<Integer> userArray = new ArrayList<>();
            for(MyFriendBean item : friendList){
                userArray.add(item.getUser_id());
            }
            Map<String,Object> map = new HashMap<>();
            map.put("userArray",userArray);
            map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
            presenter.addTeamPeople(map);
        }
    }

    private void showDialog(){
        if (dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_team_people);
            TextView tvDeletePeople = dialog.findViewById(R.id.tv_delete_people);
            tvDeletePeople.setOnClickListener(new View.OnClickListener() {//批量删除
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(),OperateTeamPeopleActivity.class);
                    intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) teamPeopleList);
                    startActivity(intent);
                }
            });
        }
        dialog.show();
    }
}
