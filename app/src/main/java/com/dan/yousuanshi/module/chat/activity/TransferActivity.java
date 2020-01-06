package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.ui.HintSideBar;
import com.dan.yousuanshi.module.addressbook.ui.SideBar;
import com.dan.yousuanshi.module.chat.adapter.TransferAdapter;
import com.dan.yousuanshi.module.chat.presenter.TransferActivityPresenter;
import com.dan.yousuanshi.module.chat.view.TransferActivityView;
import com.dan.yousuanshi.module.main.activity.MainActivity;
import com.dan.yousuanshi.utils.PinyinUtil;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TransferActivity extends BaseActivity<TransferActivityPresenter> implements TransferActivityView, SideBar.OnChooseLetterChangedListener {

    @BindView(R.id.tv_sure)
    TextView tvSure;
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
    private TransferAdapter adapter;
    private MyFriendBean myFriendBean;
    private Dialog dialog;
    private int type;//2 只转让不退群

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer;
    }

    @Nullable
    @Override
    protected TransferActivityPresenter initPresenter() {
        return new TransferActivityPresenter();
    }

    @Override
    protected void initView() {
        groupId = getIntent().getIntExtra("groupId",0);
        type = getIntent().getIntExtra("type",0);
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
        adapter = new TransferAdapter(this,friendList);
        adapter.setOnItemClick(new TransferAdapter.OnItemClick() {
            @Override
            public void onItemClick(MyFriendBean myFriendBean) {
                TransferActivity.this.myFriendBean = myFriendBean;
                if(TransferActivity.this.myFriendBean!=null){
                    tvSure.setEnabled(true);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        hintSideBar.setOnChooseLetterChangedListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void transferGroupSuccess(int code, List list) {
        if(type == 2){
            ToastUtils.showToast(this,"转让成功！");
            finish();
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("groupId",groupId);
            presenter.exitGroup(map);
        }
    }

    @Override
    public void transferGroupFailed(int code, String msg) {
        ToastUtils.showToast(this,"移交群主失败："+code+msg);
        Log.e("TransferActivity","移交群主失败："+code+msg);
    }

    @Override
    public void exitGroupSuccess(int code, List list) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void exitGroupFailed(int code, String msg) {
        ToastUtils.showToast(this,"退出群聊失败："+code+msg);
        Log.e("TransferActivity","退出群聊失败："+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_sure:
                showDialog();
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

    private void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);
            tvTitle.setText("是否移交群主给"+myFriendBean.getNick_name()+"？");
            TextView tvSure = dialog.findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("transferUid",myFriendBean.getUser_id());
                    map.put("groupId",groupId);
                    presenter.transferGroup(map);
                }
            });
            TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }
}
