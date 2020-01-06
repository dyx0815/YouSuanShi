package com.dan.yousuanshi.module.chat.activity;

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
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.chat.adapter.GroupAdminAdapter;
import com.dan.yousuanshi.module.chat.presenter.GroupAdminActivityPresenter;
import com.dan.yousuanshi.module.chat.view.GroupAdminActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupAdminActivity extends BaseActivity<GroupAdminActivityPresenter> implements GroupAdminActivityView {

    @BindView(R.id.tv_group_admin)
    TextView tvGroupAdmin;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_add_group_admin)
    LinearLayout llAddGroupAdmin;

    private int groupId;
    private List<MyFriendBean> friendList;
    private GroupAdminAdapter adapter;
    private List<MyFriendBean> adminList = new ArrayList<>();
    private Dialog dialog;
    private boolean isCreate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_admin;
    }

    @Nullable
    @Override
    protected GroupAdminActivityPresenter initPresenter() {
        return new GroupAdminActivityPresenter();
    }

    @Override
    protected void initView() {
        MyApplication.addActivity(this);
        groupId = getIntent().getIntExtra("groupId", 0);
        friendList = getIntent().getParcelableArrayListExtra("friendList");
        for (int i = 0; i < friendList.size(); i++) {
            if (friendList.get(i).getIsMaster() == 1) {
                adminList.add(friendList.get(i));
            }
            if (friendList.get(i).getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()) {
                if (friendList.get(i).getIsCreate() == 1) {
                    isCreate = true;
                    llAddGroupAdmin.setVisibility(View.VISIBLE);
                } else {
                    isCreate = false;
                    llAddGroupAdmin.setVisibility(View.GONE);
                }
            }
        }
        tvGroupAdmin.setText("群管理员（" + adminList.size() + "/" + friendList.size() + "）");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupAdminAdapter(this, adminList,isCreate);
        adapter.setOnItemClick(new GroupAdminAdapter.OnItemClick() {
            @Override
            public void onItemClick(MyFriendBean myFriendBean) {
                showRemoveDialog(myFriendBean);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_add_group_admin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_add_group_admin:
                Intent intent = new Intent(this, ChoosePeopleActivity.class);
                intent.putExtra("type", 5);
                intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void removeGroupMasterSuccess(int code, List list, MyFriendBean myFriendBean) {
        adapter.notifyItemRemoved(adminList.indexOf(myFriendBean));
        adminList.remove(myFriendBean);
        for (int i = 0; i < friendList.size(); i++) {
            if (friendList.get(i).getUser_id() == myFriendBean.getUser_id()) {
                friendList.get(i).setIsMaster(0);
            }
        }
        tvGroupAdmin.setText("群管理员（" + adminList.size() + "/" + friendList.size() + "）");
    }

    @Override
    public void removeGroupMasterFailed(int code, String msg) {
        ToastUtils.showToast(this, "移除管理员失败：" + code + msg);
        Log.e("ChoosePeople", "移除管理员失败：" + code + msg);
    }

    private void showRemoveDialog(MyFriendBean myFriendBean) {
        if (dialog == null) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);
            tvTitle.setText("是否移除群管理" + myFriendBean.getNick_name() + "?");
            TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
            TextView tvSure = dialog.findViewById(R.id.tv_sure);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Map<String, Object> map = new HashMap<>();
                    List<Integer> userArray = new ArrayList<>();
                    userArray.add(myFriendBean.getUser_id());
                    map.put("userArray", new Gson().toJson(userArray));
                    map.put("groupId", groupId);
                    map.put("changeType", 2);
                    presenter.removeGroupMaster(map, myFriendBean);
                }
            });
        }
        dialog.show();
    }
}
