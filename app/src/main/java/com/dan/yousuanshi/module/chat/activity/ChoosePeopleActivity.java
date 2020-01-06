package com.dan.yousuanshi.module.chat.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.ui.HintSideBar;
import com.dan.yousuanshi.module.addressbook.ui.SideBar;
import com.dan.yousuanshi.module.chat.adapter.ChoosePeopleAdapter;
import com.dan.yousuanshi.module.chat.presenter.ChoosePeopleActivityPresenter;
import com.dan.yousuanshi.module.chat.view.ChoosePeopleActivityView;
import com.dan.yousuanshi.utils.PinyinUtil;
import com.dan.yousuanshi.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChoosePeopleActivity extends BaseActivity<ChoosePeopleActivityPresenter> implements SideBar.OnChooseLetterChangedListener, ChoosePeopleActivityView {

    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.hintSideBar)
    HintSideBar hintSideBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private List<MyFriendBean> friendList = new ArrayList<>();
    private List<MyFriendBean> util = new ArrayList<>();
    private ChoosePeopleAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int type;
    private int groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_people;
    }

    @Nullable
    @Override
    protected ChoosePeopleActivityPresenter initPresenter() {
        return new ChoosePeopleActivityPresenter();
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type",0);
        groupId = getIntent().getIntExtra("groupId",0);
        List<MyFriendBean> data = getIntent().getParcelableArrayListExtra("friendList");
        if (data != null) {//添加首字母排序
            for(MyFriendBean item :data){
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
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChoosePeopleAdapter(this, friendList);
        adapter.setOnItemClick(new ChoosePeopleAdapter.OnItemClick() {
            @Override
            public void onItemClick(int count) {
                tvSure.setText("确定（" + count + "人）");
                if (count > 0) {
                    tvSure.setEnabled(true);
                } else {
                    tvSure.setEnabled(false);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        hintSideBar.setOnChooseLetterChangedListener(this);
        if(type == 5){
            tvTitle.setText("添加群管理员");
        }
    }

    @Override
    protected void loadData() {

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


    @OnClick({R.id.ll_back, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_sure:
                if(type == 4){//移除群成员
                    List<Integer> userArray = new ArrayList<>();
                    for(MyFriendBean item : adapter.getCheckedFriend()){
                        userArray.add(item.getUser_id());
                    }
                    Map<String,Object> map = new HashMap<>();
                    map.put("userArray",new Gson().toJson(userArray));
                    map.put("groupId",groupId);
                    presenter.removeGroupPeople(map);
                }else if(type == 5){//添加管理员
                    MyApplication.addActivity(this);
                    Map<String,Object> map = new HashMap<>();
                    List<Integer> userArray = new ArrayList<>();
                    for(MyFriendBean item : adapter.getCheckedFriend()){
                        userArray.add(item.getUser_id());
                    }
                    map.put("userArray",new Gson().toJson(userArray));
                    map.put("groupId",groupId);
                    map.put("changeType",1);
                    presenter.addGroupMaster(map);
                }else{
                    Intent intent = new Intent(this,SetGroupInfoActivity.class);
                    intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) adapter.getCheckedFriend());
                    setResult(2,intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void removeGroupPeopleSuccess(int code, List list) {
        ToastUtils.showToast(this,"移除成功！");
        finish();
    }

    @Override
    public void removeGroupPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this,"移除群成员失败："+code+msg);
        Log.e("ChoosePeople","移除群成员失败："+code+msg);
    }

    @Override
    public void addGroupMasterSuccess(int code, List list) {
        ToastUtils.showToast(this,"添加管理员成功！");
        MyApplication.clearActivity();
    }

    @Override
    public void addGroupMasterFailed(int code, String msg) {
        ToastUtils.showToast(this,"添加管理员失败："+code+msg);
        Log.e("ChoosePeople","添加管理员失败："+code+msg);
    }
}
