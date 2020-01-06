package com.dan.yousuanshi.module.chat.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupMasterActivity extends BaseActivity {

    @BindView(R.id.ll_transfer)
    LinearLayout lltransfer;

    private List<MyFriendBean> friendList;
    private int groupId;
    private boolean isCreate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_master;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        MyApplication.addActivity(this);
        groupId = getIntent().getIntExtra("groupId",0);
        friendList = getIntent().getParcelableArrayListExtra("friendList");
        isCreate = getIntent().getBooleanExtra("isCreate",false);
        if(isCreate){
            lltransfer.setVisibility(View.VISIBLE);
        }else{
            lltransfer.setVisibility(View.GONE);
        }
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.ll_back, R.id.ll_group_admin, R.id.ll_transfer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_group_admin:
                Intent intent2 = new Intent(this,GroupAdminActivity.class);
                intent2.putExtra("groupId",groupId);
                intent2.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent2);
                finish();
                break;
            case R.id.ll_transfer:
                Intent intent = new Intent(this,TransferActivity.class);
                intent.putExtra("groupId",groupId);
                intent.putExtra("type",2);
                for(int i = 0;i<friendList.size();i++){
                    if(friendList.get(i).getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()){
                        friendList.remove(i);
                    }
                }
                intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent);
                finish();
                break;
        }
    }
}
