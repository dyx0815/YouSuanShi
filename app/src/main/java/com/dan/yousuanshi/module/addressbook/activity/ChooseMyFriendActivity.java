package com.dan.yousuanshi.module.addressbook.activity;

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
import com.dan.yousuanshi.module.addressbook.adapter.ChooseMyFriendAdapter;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.ui.HintSideBar;
import com.dan.yousuanshi.module.addressbook.ui.SideBar;
import com.dan.yousuanshi.module.chat.activity.SetGroupInfoActivity;
import com.dan.yousuanshi.module.chat.activity.SharedBusinessCardActivity;
import com.dan.yousuanshi.module.chat.presenter.ChooseMyFriendActivityPresenter;
import com.dan.yousuanshi.module.chat.view.ChooseMyFriendActivityView;
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

public class ChooseMyFriendActivity extends BaseActivity<ChooseMyFriendActivityPresenter> implements ChooseMyFriendActivityView, SideBar.OnChooseLetterChangedListener {

    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.hintSideBar)
    HintSideBar hintSideBar;

    private List<MyFriendBean> friendList = new ArrayList<>();
    private ChooseMyFriendAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<MyFriendBean> util = new ArrayList<>();
    private List<MyFriendBean> data;
    private int type; //type=4 添加群成员
    private int groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_my_friend;
    }

    @Nullable
    @Override
    protected ChooseMyFriendActivityPresenter initPresenter() {
        return new ChooseMyFriendActivityPresenter();
    }

    @Override
    protected void initView() {
        data = getIntent().getParcelableArrayListExtra("friendList");
        type = getIntent().getIntExtra("type", 0);
        groupId = getIntent().getIntExtra("groupId",0);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChooseMyFriendAdapter(this, friendList, type);
        adapter.setOnItemClick(new ChooseMyFriendAdapter.OnItemClick() {
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
        if (type != 4) {
            if (data != null && data.size() > 0) {
                tvSure.setText("确定（" + data.size() + "人）");
            }
        }
    }

    @Override
    protected void loadData() {
        presenter.getMyFriend();
    }

    @Override
    public void getMyFriendSuccess(int code, List<MyFriendBean> data) {
        if (code == 0) {
            if (data.size() > 0) {
                friendList.clear();
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
                if (this.data != null) {
                    for (int i = 0; i < this.data.size(); i++) {
                        for (MyFriendBean item : friendList) {
                            if (this.data.get(i).getUser_id() == item.getUser_id()) {
                                item.setChecked(true);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getMyFriendFailed(int code, String msg) {
        ToastUtils.showToast(this, "请求好友列表失败：" + code + msg);
        Log.e("ChooseMyFriend", "请求好友列表失败：" + code + msg);
    }

    @Override
    public void addGroupPeopleSuccess(int code, List list) {
        Intent intent = new Intent(this, SharedBusinessCardActivity.class);
        intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) adapter.getCheckedFriend());
        setResult(5, intent);
        finish();
    }

    @Override
    public void addGroupPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this, "添加群员失败：" + code + msg);
        Log.e("ChooseMyFriend", "添加群员失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_sure:
                if(type == 4){//添加群成员
                    Map<String,Object> map = new HashMap<>();
                    List<Integer> userArray = new ArrayList<>();
                    for(MyFriendBean item:adapter.getCheckedFriend()){
                        userArray.add(item.getUser_id());
                    }
                    map.put("userArray",new Gson().toJson(userArray));
                    map.put("groupId",groupId);
                    presenter.addGroupPeople(map);
                }else{
                    Intent intent = new Intent(this, SetGroupInfoActivity.class);
                    intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) adapter.getCheckedFriend());
                    setResult(4, intent);
                    finish();
                }
                break;
        }
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
}
