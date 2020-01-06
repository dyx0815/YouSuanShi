package com.dan.yousuanshi.module.chat.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.ui.HintSideBar;
import com.dan.yousuanshi.module.addressbook.ui.SideBar;
import com.dan.yousuanshi.module.chat.adapter.GroupPeopleAdapter;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.utils.PinyinUtil;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupPeopleActivity extends BaseActivity implements SideBar.OnChooseLetterChangedListener {

    @BindView(R.id.ll_add_group_people)
    LinearLayout llAddGroupPeople;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.hintSideBar)
    HintSideBar hintSideBar;

    private List<MyFriendBean> friendList = new ArrayList<>();
    private List<MyFriendBean> util = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private int groupId;
    private GroupPeopleAdapter adapter;
    private MyTeamBean team;
    private int groupType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_people;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        groupId = getIntent().getIntExtra("groupId", 0);
        groupType = getIntent().getIntExtra("groupType", 0);
        team = getIntent().getParcelableExtra("team");
        List<MyFriendBean> data = getIntent().getParcelableArrayListExtra("friendList");
        List<MyFriendBean> groupAdmin = new ArrayList<>();
        Iterator<MyFriendBean> iterator = data.iterator();
        while (iterator.hasNext()){//将群主和管理员排除出来
            MyFriendBean myFriendBean = iterator.next();
            if(myFriendBean.getIsCreate() == 1||myFriendBean.getIsMaster() == 1){
                groupAdmin.add(myFriendBean);
                iterator.remove();
            }
        }
        if (data != null) {//添加首字母排序
            for (MyFriendBean item : data) {
                item.setHeadLetter(PinyinUtil.getHeadChar(item.getNick_name()));
            }
            friendList.addAll(data);
            Collections.sort(friendList);
            showTab();
            for (MyFriendBean myFriend : friendList) {
                for (MyFriendBean myFriend2 : util) {
                    if (myFriend.equals(myFriend2)) {
                        myFriend.setShowLetter(true);
                    }
                }
            }
        }
        for(MyFriendBean item : groupAdmin){
            if(item.getIsCreate() == 1){
                friendList.add(0,item);
            }
        }
        for(MyFriendBean item : groupAdmin){
            if(item.getIsMaster() == 1){
                friendList.add(1,item);
            }
        }
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupPeopleAdapter(this, friendList);
        adapter.setOnItemClick(new GroupPeopleAdapter.OnItemClick() {
            @Override
            public void onItemClick(MyFriendBean myFriendBean) {

            }
        });
        recyclerView.setAdapter(adapter);
        hintSideBar.setOnChooseLetterChangedListener(this);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_add_group_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_add_group_people:
                Intent intent3 = new Intent(this, SharedBusinessCardActivity.class);
                intent3.putExtra("type", 4);
                intent3.putExtra("groupId", groupId);
                intent3.putExtra("groupType", groupType);
                for (int i = 0; i < friendList.size(); i++) {
                    if (friendList.get(i).getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()) {
                        friendList.remove(i);
                    }
                }
                intent3.putExtra("team", team);
                intent3.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent3);
                finish();
                break;
        }
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
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'A' || item.getHeadLetter() == 'a') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'B' || item.getHeadLetter() == 'b') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'C' || item.getHeadLetter() == 'c') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'D' || item.getHeadLetter() == 'd') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'E' || item.getHeadLetter() == 'e') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'F' || item.getHeadLetter() == 'f') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'G' || item.getHeadLetter() == 'g') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'H' || item.getHeadLetter() == 'h') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'I' || item.getHeadLetter() == 'i') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'J' || item.getHeadLetter() == 'j') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'K' || item.getHeadLetter() == 'k') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'L' || item.getHeadLetter() == 'l') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'M' || item.getHeadLetter() == 'm') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'N' || item.getHeadLetter() == 'n') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'O' || item.getHeadLetter() == 'o') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'P' || item.getHeadLetter() == 'p') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'Q' || item.getHeadLetter() == 'q') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'R' || item.getHeadLetter() == 'r') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'S' || item.getHeadLetter() == 's') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'T' || item.getHeadLetter() == 't') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'U' || item.getHeadLetter() == 'u') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'V' || item.getHeadLetter() == 'v') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'W' || item.getHeadLetter() == 'w') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'X' || item.getHeadLetter() == 'x') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'Y' || item.getHeadLetter() == 'y') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == 'Z' || item.getHeadLetter() == 'z') {
                util.add(item);
                break;
            }
        }
        for (MyFriendBean item : friendList) {
            if (item.getHeadLetter() == '#') {
                util.add(item);
                break;
            }
        }
    }
}
