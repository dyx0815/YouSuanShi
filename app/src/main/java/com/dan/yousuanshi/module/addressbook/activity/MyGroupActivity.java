package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.event.SendMessageEvent;
import com.dan.yousuanshi.module.addressbook.adapter.MyGroupAdapter;
import com.dan.yousuanshi.module.addressbook.bean.MyGroupBean;
import com.dan.yousuanshi.module.addressbook.presenter.MyGroupPresenter;
import com.dan.yousuanshi.module.addressbook.view.MyGroupView;
import com.dan.yousuanshi.module.chat.activity.ChatActivity;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatUserInfoBean;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyGroupActivity extends BaseActivity<MyGroupPresenter> implements MyGroupView {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_no_group)
    LinearLayout llNoGroup;

    private MyGroupAdapter adapter;
    private List<MyGroupBean> groupList = new ArrayList<>();
    private Dialog dialog;
    private int type = 1;//1为正常入口 2为转发
    private ChatBean data;
    private int pId;
    private int uId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_group;
    }

    @Nullable
    @Override
    protected MyGroupPresenter initPresenter() {
        return new MyGroupPresenter();
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 1);
        uId = UserUtils.getInstance().getUserBean().getUser_id();
        if (type == 2) {
            MyApplication.addActivity(this);
            data = (ChatBean) getIntent().getSerializableExtra("chatData");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyGroupAdapter(this, groupList);
        adapter.setOnItemClick(new MyGroupAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                if (type == 1) {
                    MyGroupBean myGroupBean = groupList.get(position);
                    ChatBean chatBean = new ChatBean();
                    chatBean.setPid(myGroupBean.getId());
                    chatBean.setName(myGroupBean.getGroup_name());
                    chatBean.setUserIconUrl(myGroupBean.getGroup_avatar());
                    chatBean.setType(Constant.CHAT_GROUP_TYPE);
                    chatBean.setGroupType(myGroupBean.getGroup_type());
                    Intent intent = new Intent(MyGroupActivity.this, ChatActivity.class);
                    intent.putExtra("user", chatBean);
                    startActivity(intent);
                    finish();
                } else {
                    pId = groupList.get(position).getId();
                    showDialog(groupList.get(position));
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        presenter.getMyGroup();
    }

    @Override
    public void getMyGroupSuccess(int code, List<MyGroupBean> data) {
        if (code == 0) {
            if (data.size() > 0) {
                llNoGroup.setVisibility(View.GONE);
                groupList.addAll(data);
                adapter.notifyDataSetChanged();
            }else{
                llNoGroup.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void getMyGroupFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }


    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    private void showDialog(MyGroupBean myGroupBean) {
        if (dialog == null) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_forward);
        }
        RoundedImageView rivHeadIcon = dialog.findViewById(R.id.riv_head_icon);
        Glide.with(this).load(myGroupBean.getGroup_avatar()).into(rivHeadIcon);
        TextView tvName = dialog.findViewById(R.id.tv_name);
        tvName.setText(myGroupBean.getGroup_name());
        EditText etText = dialog.findViewById(R.id.et_text);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvSure = dialog.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setUserinfo(new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait()));
                data.setpUserInfo(new ChatUserInfoBean(myGroupBean.getGroup_name(), myGroupBean.getGroup_avatar()));
                data.setPid(pId);
                data.setType(Constant.CHAT_GROUP_TYPE);
                data.setTime(DateUtil.dateToString(new Date()));
                data.setGroupinfo(new ChatUserInfoBean(myGroupBean.getGroup_name(), myGroupBean.getGroup_avatar(), myGroupBean.getGroup_type()));
                sendMessage(data, addChatMessageToDataBase(data, "0"));
                if (!StringUtils.isEmpty(etText.getText().toString())) {
                    ChatBean chat = new ChatBean(pId, etText.getText().toString(), UserUtils.getInstance().getUserBean().getUser_id(), Constant.CHAT_GROUP_TYPE
                            , Constant.CHAT_TEXT, 1, UserUtils.getInstance().getUserBean().getUser_portrait()
                            , new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait())
                            , DateUtil.dateToString(new Date()), new ChatUserInfoBean(myGroupBean.getGroup_name(), myGroupBean.getGroup_avatar()));
                    chat.setGroupinfo(new ChatUserInfoBean(myGroupBean.getGroup_name(), myGroupBean.getGroup_avatar(), myGroupBean.getGroup_type()));
                    sendMessage(chat, addChatMessageToDataBase(chat, "0"));
                }
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void sendMessageSuccess(int code, MessageId data, long id) {
        updateChatRecordMsgId(id, data.getMsgid());
        ToastUtils.showToast(this,"已发送~");
        MyApplication.clearActivity();
    }

    @Override
    public void sendMessageFailed(int code, String msg) {

    }

    /**
     * 给服务器发送消息
     *
     * @param chatBean
     */
    private void sendMessage(ChatBean chatBean, long id) {
        SendMessageEvent sendMessageEvent = new SendMessageEvent(chatBean);
        EventBus.getDefault().post(sendMessageEvent);
        Map<String, String> map = new HashMap<>();
        map.put("pid", String.valueOf(chatBean.getPid()));
        map.put("stxt", chatBean.getStxt());
        map.put("mid", String.valueOf(uId));
        map.put("type", String.valueOf(chatBean.getType()));
        if (!StringUtils.isEmpty(chatBean.getTemp())) {
            map.put("temp", chatBean.getTemp());
        }
        map.put("fileype", String.valueOf(chatBean.getFileype()));
        map.put("userinfo", chatBean.getUserinfo().toJson());
        if (chatBean.getType() == Constant.CHAT_GROUP_TYPE) {
            map.put("groupinfo", chatBean.getGroupinfo().toJson());
        }
        presenter.sendMessage(map, id);
    }

    /**
     * 将聊天记录添加到本地数据库
     */
    private long addChatMessageToDataBase(ChatBean chatBean, String msgId) {
        if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getChatRecordTableName(chatBean.getPid(), UserUtils.getInstance().getUserBean().getUser_id()))) {
            DataBaseHelper.createChatTable(getActivity(), chatBean.getPid(), UserUtils.getInstance().getUserBean().getUser_id());
        }
        ContentValues values = new ContentValues();
        values.put("message_type", chatBean.getFileype());
        values.put("message_txt", chatBean.getStxt());
        values.put("send_time", chatBean.getTime());
        values.put("is_read", chatBean.getIsRead());
        values.put("send_flag", chatBean.getFlag());
        if (chatBean.getPath() != null) {
            values.put("path", chatBean.getPath());
        }
        values.put("msgid", msgId);
        if (chatBean.getType() == Constant.CHAT_ONE_TYPE) {
            if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getChatRecordTableName(chatBean.getPid(), uId))) {
                DataBaseHelper.createChatTable(getActivity(), chatBean.getPid(), uId);
            }
            return DataBaseHelper.insertChatRecord(getActivity(), chatBean.getPid(), uId, values);
        } else if (chatBean.getType() == Constant.CHAT_GROUP_TYPE) {
            if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getGroupChatRecordTableName(chatBean.getPid(), uId))) {
                DataBaseHelper.createGroupChatTable(getActivity(), chatBean.getPid(), uId);
            }
            return DataBaseHelper.insertGroupChatRecord(getActivity(), chatBean.getPid(), uId, values);
        }
        return 0;
    }

    /**
     * 更改聊天记录消息id
     *
     * @param id
     * @param msgId
     */
    private void updateChatRecordMsgId(long id, String msgId) {
        ContentValues values = new ContentValues();
        values.put("msgid", msgId);
        DataBaseHelper.updateChatRecord(getActivity(), pId, uId, values, id);
    }
}
