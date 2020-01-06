package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.module.addressbook.adapter.NewFriendAdapter;
import com.dan.yousuanshi.module.addressbook.bean.NewFriendBean;
import com.dan.yousuanshi.module.addressbook.presenter.NewFriendPresenter;
import com.dan.yousuanshi.module.addressbook.view.NewFriendView;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NewFriendActivity extends BaseActivity<NewFriendPresenter> implements NewFriendView {

    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_new_friend)
    TextView tvNoNewFriend;

    private NewFriendBean newFriendBean = new NewFriendBean();
    private NewFriendAdapter adapter;
    private List<NewFriendBean.DataBean> data = new ArrayList<>();
    private int position = 0;
    private Dialog clearDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friend;
    }

    @Nullable
    @Override
    protected NewFriendPresenter initPresenter() {
        return new NewFriendPresenter();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewFriendAdapter(this, data);
        adapter.setOnItemClick(new NewFriendAdapter.OnItemClick() {
            @Override
            public void yes(View v, int position) {
                Map<String, String> map = new HashMap<>();
                map.put("sendFriendId", String.valueOf(data.get(position).getId()));
                NewFriendActivity.this.position = position;
                presenter.agreeFriend(map);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        presenter.getNewFriend();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(getActivity(), getActivity().getColor(R.color.white), 0);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public void getNewFriendSuccess(int code, NewFriendBean data) {
        if (data.getData().size() > 0) {
            this.data.addAll(data.getData());
            adapter.notifyDataSetChanged();
            llClear.setVisibility(View.VISIBLE);
        } else {
            tvNoNewFriend.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getNewFriendFailed(int code, String msg) {
        ToastUtils.showToast(this, "code:" + code + "\t" + msg);
    }

    @Override
    public void agreeFriendSuccess(int code, List data) {
        this.data.get(position).setIs_pass(1);
        adapter.notifyItemChanged(position);
        NewFriendBean.DataBean dataBean = this.data.get(position);
        ContentValues values = new ContentValues();
        values.put("pid",dataBean.getSid());
        values.put("p_name",dataBean.getNick_name());
        values.put("last_message","你已添加了"+dataBean.getNick_name()+"，现在可以开始聊天了~");
        values.put("last_time", DateUtil.dateToString(new Date()));
        values.put("is_top",0);
        values.put("p_icon",dataBean.getUser_portrait());
        values.put("message_count",0);
        values.put("type", Constant.CHAT_ONE_TYPE);
        values.put("file_type",1);
        DataBaseHelper.insertChatList(this, UserUtils.getInstance().getUserBean().getUser_id(),values);
    }

    @Override
    public void agreeFriendFailed(int code, String msg) {
        ToastUtils.showToast(this, "code:" + code + "\t" + msg);
    }

    @Override
    public void clearNewFriendSuccess(int code, List data, int[] ids) {
        if (code == 0) {
            for (int id : ids) {
                Iterator<NewFriendBean.DataBean> iterator = this.data.iterator();
                while (iterator.hasNext()){
                    NewFriendBean.DataBean dataBean = iterator.next();
                    if (dataBean.getId() == Integer.valueOf(id)) {
                        iterator.remove();
                    }
                }
            }
            if (this.data.size() == 0) {
                tvNoNewFriend.setVisibility(View.VISIBLE);
                llClear.setVisibility(View.GONE);
            } else {
                tvNoNewFriend.setVisibility(View.GONE);
                llClear.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void clearNewFriendFailed(int code, String msg) {
        ToastUtils.showToast(this, "code:" + code + "\t" + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_clear:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        if (clearDialog == null) {
            clearDialog = new Dialog(this);
            clearDialog.setContentView(R.layout.dialog_clear_new_friend);
            clearDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearDialog.dismiss();
                }
            });
            clearDialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] ids = new int[NewFriendActivity.this.data.size()];
                    for (int i = 0; i < NewFriendActivity.this.data.size(); i++) {
                        ids[i] = NewFriendActivity.this.data.get(i).getId();
                    }
                    Map<String, int[]> map = new HashMap<>();
                    map.put("sendFriendIdArray", ids);
                    presenter.clearNewFriend(map);
                    clearDialog.dismiss();
                }
            });
        }
        clearDialog.show();
    }
}
