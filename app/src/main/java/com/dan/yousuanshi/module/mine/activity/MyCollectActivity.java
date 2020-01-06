package com.dan.yousuanshi.module.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.chat.activity.ChooseActivity;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.mine.adapter.MyCollectAdapter;
import com.dan.yousuanshi.module.mine.bean.MyCollectBean;
import com.dan.yousuanshi.module.mine.presenter.MyCollectActivityPresenter;
import com.dan.yousuanshi.module.mine.view.MyCollectActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
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

public class MyCollectActivity extends BaseActivity<MyCollectActivityPresenter> implements MyCollectActivityView, OnLoadMoreListener, OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private int page = 1;
    private int pageSize = 10;
    private List<MyCollectBean.DataBean> collectList = new ArrayList<>();
    private MyCollectAdapter adapter;
    private Dialog dialog;
    private Dialog deleteDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Nullable
    @Override
    protected MyCollectActivityPresenter initPresenter() {
        return new MyCollectActivityPresenter();
    }

    @Override
    protected void initView() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyCollectAdapter(this,collectList);
        adapter.setOnItemClick(new MyCollectAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                showDialog1(position);
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
        page = 1;
        collectList.clear();
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("page",page);
        presenter.getMyCollect(map);
    }

    @Override
    public void getMyCollectSuccess(int code, MyCollectBean data) {
        collectList.addAll(data.getData());
        adapter.notifyDataSetChanged();
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void getMyCollectFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取我的收藏失败：" + code + msg);
        Log.e("MyCollect", "获取我的收藏失败：" + code + msg);
    }

    @Override
    public void deleteCollectSuccess(int code, List list,int position) {
        ToastUtils.showToast(this,"删除成功！");
        collectList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void deleteCollectFailed(int code, String msg) {
        ToastUtils.showToast(this,"删除收藏失败！"+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:

                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("page",page);
        presenter.getMyCollect(map);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        collectList.clear();
        page = 1;
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("page",page);
        presenter.getMyCollect(map);
    }

    private void showDialog1(int position){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_collect);
            TextView tvForWard = dialog.findViewById(R.id.tv_forward);
            TextView tvDelete = dialog.findViewById(R.id.tv_delete);
            tvForWard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    MyCollectBean.DataBean dataBean = collectList.get(position);
                    Intent intent = new Intent(getActivity(), ChooseActivity.class);
                    ChatBean chatBean = new ChatBean();
                    if(dataBean.getFile_type() == Constant.CHAT_VIDEO || dataBean.getFile_type() == Constant.CHAT_VOICE){
                        chatBean.setStxt(dataBean.getFile_size()+"");
                    }else{
                        chatBean.setStxt(dataBean.getContent());
                    }
                    chatBean.setMid(UserUtils.getInstance().getUserBean().getUser_id());
                    chatBean.setFileype(dataBean.getFile_type());
                    chatBean.setTemp(dataBean.getContent().replace(Constant.DOWNLOAD_URL,""));
                    intent.putExtra("chatData",chatBean);
                    startActivity(intent);
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    showDeleteDialog(position);
                }
            });
        }
        dialog.show();
    }

    private void showDeleteDialog(int position){
        if(deleteDialog == null){
            deleteDialog = new Dialog(this);
            deleteDialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = deleteDialog.findViewById(R.id.tv_title);
            TextView tvSure = deleteDialog.findViewById(R.id.tv_sure);
            TextView tvCancel = deleteDialog.findViewById(R.id.tv_cancel);
            tvTitle.setText("确认删除此收藏？");
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("collectionId",collectList.get(position).getId());
                    presenter.deleteCollect(map,position);
                }
            });
        }
        deleteDialog.show();
    }
}
