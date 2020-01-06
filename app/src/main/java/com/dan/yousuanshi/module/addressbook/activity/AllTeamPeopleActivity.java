package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.os.Parcelable;
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
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.adapter.AllTeamPeopleAdapter;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.AllTeamPeopleActivityPresenter;
import com.dan.yousuanshi.module.addressbook.ui.HintSideBar;
import com.dan.yousuanshi.module.addressbook.ui.SideBar;
import com.dan.yousuanshi.module.addressbook.view.AllTeamPeopleActivityView;
import com.dan.yousuanshi.module.shared.activity.SharedTeamPeopleActivity;
import com.dan.yousuanshi.utils.PinyinUtil;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AllTeamPeopleActivity extends BaseActivity<AllTeamPeopleActivityPresenter> implements AllTeamPeopleActivityView, SideBar.OnChooseLetterChangedListener {

    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.hintSideBar)
    HintSideBar hintSideBar;
    @BindView(R.id.ll_sure)
    LinearLayout llSure;
    @BindView(R.id.tv_choose_count)
    TextView tvChooseCount;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    private List<TeamPeopleBean> teamPeopleList = new ArrayList<>();
    private List<TeamPeopleBean> util = new ArrayList<>();
    private AllTeamPeopleAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int type = 0;
    private String sign;
    private List<Integer> userList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_team_people;
    }

    @Nullable
    @Override
    protected AllTeamPeopleActivityPresenter initPresenter() {
        return new AllTeamPeopleActivityPresenter();
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);
        userList = getIntent().getIntegerArrayListExtra("userList");
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        presenter.getTeamPeople(map);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AllTeamPeopleAdapter(this, teamPeopleList, type);
        adapter.setOnItemClick(new AllTeamPeopleAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                if (type == 7) {
                    MyApplication.addActivity(getActivity());
                    Intent intent = new Intent(getActivity(), SettingSonAdmin2Activity.class);
                    intent.putExtra("data", teamPeopleList.get(position).getUser_id());
                    startActivity(intent);
                    MyApplication.clearActivity();
                } else {
                    MyApplication.addActivity(getActivity());
                    Intent intent = new Intent(getActivity(), UpdateAdmin2Activity.class);
                    intent.putExtra("data", teamPeopleList.get(position));
                    intent.putExtra("sign", sign);
                    startActivity(intent);
                    MyApplication.clearActivity();
                }
            }

            @Override
            public void choosePeople(int count) {
                if (count <= 0) {
                    tvChooseCount.setText("已选择（0）人");
                    llSure.setVisibility(View.GONE);
                } else {
                    tvChooseCount.setText("已选择（" + count + "）人");
                    llSure.setVisibility(View.VISIBLE);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);//禁止滑动
        hintSideBar.setOnChooseLetterChangedListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getTeamPeopleSuccess(int code, List<TeamPeopleBean> data) {
        teamPeopleList.clear();
        for (TeamPeopleBean item : data) {
            item.setHeadLetter(PinyinUtil.getHeadChar(item.getReal_name()));
        }
        teamPeopleList.addAll(data);
        Collections.sort(teamPeopleList);
        showTab();
        for (TeamPeopleBean myFriend : teamPeopleList) {
            for (TeamPeopleBean myFriend2 : util) {
                if (myFriend.equals(myFriend2)) {
                    myFriend.setShowLetter(true);
                }
            }
        }
        if (userList != null) {
            for (Integer item : userList) {
                for (TeamPeopleBean people : teamPeopleList) {
                    if (item == people.getUser_id()) {
                        people.setChecked(true);
                    }
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
    public void onChooseLetter(String s) {
        int i = adapter.getFirstPositionByChar(s.charAt(0));
        if (i == -1) {
            return;
        }
        layoutManager.scrollToPositionWithOffset(i, 0);
    }

    @Override
    public void onNoChooseLetter() {

    }

    private void showTab() {
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'A' || item.getHeadLetter() == 'a') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'B' || item.getHeadLetter() == 'b') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'C' || item.getHeadLetter() == 'c') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'D' || item.getHeadLetter() == 'd') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'E' || item.getHeadLetter() == 'e') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'F' || item.getHeadLetter() == 'f') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'G' || item.getHeadLetter() == 'g') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'H' || item.getHeadLetter() == 'h') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'I' || item.getHeadLetter() == 'i') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'J' || item.getHeadLetter() == 'j') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'K' || item.getHeadLetter() == 'k') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'L' || item.getHeadLetter() == 'l') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'M' || item.getHeadLetter() == 'm') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'N' || item.getHeadLetter() == 'n') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'O' || item.getHeadLetter() == 'o') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'P' || item.getHeadLetter() == 'p') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'Q' || item.getHeadLetter() == 'q') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'R' || item.getHeadLetter() == 'r') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'S' || item.getHeadLetter() == 's') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'T' || item.getHeadLetter() == 't') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'U' || item.getHeadLetter() == 'u') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'V' || item.getHeadLetter() == 'v') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'W' || item.getHeadLetter() == 'w') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'X' || item.getHeadLetter() == 'x') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'Y' || item.getHeadLetter() == 'y') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == 'Z' || item.getHeadLetter() == 'z') {
                util.add(item);
                break;
            }
        }
        for (TeamPeopleBean item : teamPeopleList) {
            if (item.getHeadLetter() == '#') {
                util.add(item);
                break;
            }
        }
    }


    @OnClick({R.id.ll_back, R.id.ll_search, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                break;
            case R.id.tv_sure:
                Intent intent = new Intent(this, SharedTeamPeopleActivity.class);
                if (adapter.getCheckedFriend().size() == teamPeopleList.size()) {//全选 就是全部人可见
                    adapter.getCheckedFriend().clear();
                }
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) adapter.getCheckedFriend());
                setResult(1, intent);
                finish();
                break;
        }
    }
}
