package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.chat.adapter.GroupPictureAdapter;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;
import com.dan.yousuanshi.module.chat.presenter.GroupPictureActivityPresenter;
import com.dan.yousuanshi.module.chat.view.GroupPictureActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class GroupPictureActivity extends BaseActivity<GroupPictureActivityPresenter> implements GroupPictureActivityView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_group_picture)
    RelativeLayout rlGroupPicture;
    @BindView(R.id.ll_not_group_picture)
    LinearLayout llNotGroupPicture;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private List<GroupFileBean.DataBean> pictureList = new ArrayList<>();
    private int groupId;
    private boolean isMaster;
    private GroupPictureAdapter adapter;
    private int pageSize = 20;
    private int page = 1;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_picture;
    }

    @Nullable
    @Override
    protected GroupPictureActivityPresenter initPresenter() {
        return new GroupPictureActivityPresenter();
    }

    @Override
    protected void initView() {
        groupId = getIntent().getIntExtra("data",0);
        isMaster = getIntent().getBooleanExtra("isMaster",false);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new GroupPictureAdapter(this,pictureList);
        recyclerView.setAdapter(adapter);
        llDelete.setVisibility(isMaster?View.VISIBLE:View.GONE);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        pictureList.clear();
        presenter.getGroupPicture(groupId,page,pageSize);
    }

    @Override
    public void getGroupPictureSuccess(int code, GroupFileBean data) {
        if(data.getData().size() == 0 && page == 1){
            llNotGroupPicture.setVisibility(View.VISIBLE);
            rlGroupPicture.setVisibility(View.GONE);
        }else{
            llDelete.setVisibility(View.VISIBLE);
            llNotGroupPicture.setVisibility(View.GONE);
            rlGroupPicture.setVisibility(View.VISIBLE);
            pictureList.addAll(data.getData());
            adapter.notifyDataSetChanged();
        }
        if(data.getData().size() < pageSize){
            smartRefreshLayout.setEnableLoadMore(false);
        }else{
            smartRefreshLayout.setEnableLoadMore(true);
        }
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void getGroupPictureFailed(int code, String msg) {
        ToastUtils.showToast(this,"获取群图片失败："+code+msg);
        Log.e("GroupPicture","获取群图片失败："+code+msg);
    }

    @Override
    public void deleteGroupFileSuccess(int code, BaseBean data) {
        tvDelete.setText("选择");
        page = 1;
        adapter.setType(0);
        onResume();
    }

    @Override
    public void deleteGroupFileFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_delete://删除
                if("选择".equals(tvDelete.getText().toString())){
                    tvDelete.setText("删除");
                    adapter.setType(2);
                    adapter.notifyDataSetChanged();
                }else {
                    if(adapter.getChoosePic().size() == 0){
                        ToastUtils.showToast(this,"请选择图片！");
                    }else{
                        showDialog();
                    }
                }
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pictureList.clear();
        page = 1;
        presenter.getGroupPicture(groupId,page,pageSize);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        presenter.getGroupPicture(groupId,page,pageSize);
    }

    private void showDialog(){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);
            TextView tvcancel = dialog.findViewById(R.id.tv_cancel);
            TextView tvSure = dialog.findViewById(R.id.tv_sure);
            tvTitle.setText("确认删除此图片?");
            tvcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    List<Integer> fileArray = new ArrayList<>();
                    for(GroupFileBean.DataBean item : adapter.getChoosePic()){
                        fileArray.add(item.getId());
                    }
                    map.put("fileId",new Gson().toJson(fileArray));
                    map.put("groupId",groupId);
                    presenter.deleteGroupFile(map);
                }
            });
        }
        dialog.show();
    }
}
