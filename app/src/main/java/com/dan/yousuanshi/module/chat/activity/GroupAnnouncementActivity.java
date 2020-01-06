package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.chat.adapter.GroupAnnouncementAdapter;
import com.dan.yousuanshi.module.chat.bean.GroupAnnouncementBean;
import com.dan.yousuanshi.module.chat.presenter.GroupAnnouncementActivityPresenter;
import com.dan.yousuanshi.module.chat.view.GroupAnnouncementActivityView;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupAnnouncementActivity extends BaseActivity<GroupAnnouncementActivityPresenter> implements GroupAnnouncementActivityView {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_group_announcement)
    RelativeLayout rlGroupAnnouncement;
    @BindView(R.id.iv_add_group_announcement)
    ImageView ivAddGroupAnnoucement;
    @BindView(R.id.tv_add_announcement)
    TextView tvAddAnnouncement;
    @BindView(R.id.ll_not_group_announcement)
    LinearLayout llNotGroupAnnouncement;

    private GroupAnnouncementAdapter adapter;
    private int groupId;
    private boolean isMaster;
    private List<GroupAnnouncementBean.DataBean> annoucenmentList = new ArrayList<>();
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_announcement;
    }

    @Nullable
    @Override
    protected GroupAnnouncementActivityPresenter initPresenter() {
        return new GroupAnnouncementActivityPresenter();
    }

    @Override
    protected void initView() {
        groupId = getIntent().getIntExtra("data",0);
        isMaster = getIntent().getBooleanExtra("isMaster",false);
        if(isMaster){//有管理权限,可以添加公告
            tvAddAnnouncement.setVisibility(View.VISIBLE);
            ivAddGroupAnnoucement.setVisibility(View.VISIBLE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupAnnouncementAdapter(this,annoucenmentList,isMaster);
        adapter.setOnItemClick(new GroupAnnouncementAdapter.OnItemClick() {
            @Override
            public void updateAnnouncement(GroupAnnouncementBean.DataBean dataBean) {
                Intent intent1 = new Intent(getActivity(),InputGroupAmActivity.class);
                intent1.putExtra("groupId",groupId);
                intent1.putExtra("data",dataBean);
                startActivity(intent1);
            }

            @Override
            public void deleteAnnouncement(int id,int position) {
                showDeleteDialog(id,position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        annoucenmentList.clear();
        Map<String,Object> map = new HashMap<>();
        map.put("groupId",groupId);
        presenter.getGroupAnnouncement(map);
    }

    @Override
    public void getGroupAnnouncementSuccess(int code, GroupAnnouncementBean data) {
        if(data.getData().size()==0){
            rlGroupAnnouncement.setVisibility(View.GONE);
            llNotGroupAnnouncement.setVisibility(View.VISIBLE);
        }else{
            rlGroupAnnouncement.setVisibility(View.VISIBLE);
            llNotGroupAnnouncement.setVisibility(View.GONE);
            annoucenmentList.addAll(data.getData());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getGroupAnnouncementFailed(int code, String msg) {
        rlGroupAnnouncement.setVisibility(View.GONE);
        llNotGroupAnnouncement.setVisibility(View.VISIBLE);
        ToastUtils.showToast(this,"获取群公告失败："+code+msg);
        Log.e("GroupAnnouncement","获取群公告失败："+code+msg);
    }

    @Override
    public void deleteGroupAnnouncementSuccess(int code, List list,int position) {
        ToastUtils.showToast(this,"删除成功！");
        adapter.notifyItemRemoved(position);
        annoucenmentList.remove(position);
    }

    @Override
    public void deleteGroupAnnouncementFailed(int code, String msg) {
        ToastUtils.showToast(this,"删除群公告失败："+code+msg);
        Log.e("GroupAnnouncement","删除群公告失败："+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.iv_add_group_announcement, R.id.tv_add_announcement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_add_group_announcement:
                Intent intent = new Intent(this,InputGroupAmActivity.class);
                intent.putExtra("groupId",groupId);
                startActivity(intent);
                break;
            case R.id.tv_add_announcement:
                Intent intent1 = new Intent(this,InputGroupAmActivity.class);
                intent1.putExtra("groupId",groupId);
                startActivity(intent1);
                break;
        }
    }

    private void showDeleteDialog(int id,int position){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);
            tvTitle.setText("是否确定删除该公告?");
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
                    Map<String,Object> map = new HashMap<>();
                    map.put("groupId",groupId);
                    map.put("titleId",id);
                    presenter.deleteAnnouncement(map,position);
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }
}
