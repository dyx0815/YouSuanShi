package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.activity.SearchMyFriendActivity;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.presenter.MyFriendPresenter;
import com.dan.yousuanshi.module.addressbook.ui.HintSideBar;
import com.dan.yousuanshi.module.addressbook.ui.SideBar;
import com.dan.yousuanshi.module.addressbook.view.MyFriendView;
import com.dan.yousuanshi.module.chat.adapter.SharedMyFriendAdapter;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.utils.PinyinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SharedMyFriendActivity extends BaseActivity<MyFriendPresenter> implements MyFriendView, SideBar.OnChooseLetterChangedListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.hintSideBar)
    HintSideBar hintSideBar;

    private List<MyFriendBean> friendList = new ArrayList<>();
    private SharedMyFriendAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<MyFriendBean> util = new ArrayList<>();
    private Dialog dialog;
    private MyFriendBean myFriendBean = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shared_my_friend;
    }

    @Nullable
    @Override
    protected MyFriendPresenter initPresenter() {
        return new MyFriendPresenter();
    }

    @Override
    protected void initView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SharedMyFriendAdapter(this, friendList);
        adapter.setOnItemClick(new SharedMyFriendAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                myFriendBean = friendList.get(position);
                showDialog();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);//禁止滑动
        hintSideBar.setOnChooseLetterChangedListener(this);
    }

    @Override
    protected void loadData() {
        presenter.getMyFriend();
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

    @Override
    public void getMyFriendSuccess(int code, List<MyFriendBean> data) {
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
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void getMyFriendFailed(int code, String msg) {

    }

    @Override
    public void sendMessageSuccess(int code, MessageId data, long id) {

    }

    @Override
    public void sendMessageFailed(int code, String msg) {

    }

    @OnClick({R.id.ll_back, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                Intent intent = new Intent(this, SearchMyFriendActivity.class);
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent);
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

    private void showDialog(){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_shared_my_friend);
            dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(SharedMyFriendActivity.this,SharedBusinessCardActivity.class);
                    intent.putExtra("data", (Parcelable) myFriendBean);
                    setResult(1,intent);
                    finish();
                }
            });
        }
        dialog.show();
    }
}
