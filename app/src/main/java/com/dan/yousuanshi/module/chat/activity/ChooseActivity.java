package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
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
import com.dan.yousuanshi.module.addressbook.activity.MyFriendActivity;
import com.dan.yousuanshi.module.addressbook.activity.MyGroupActivity;
import com.dan.yousuanshi.module.chat.adapter.MessageAdapter;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatUserInfoBean;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.module.chat.presenter.ChooseActivityPresenter;
import com.dan.yousuanshi.module.chat.view.ChooseActivityView;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.ListUtil;
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

public class ChooseActivity extends BaseActivity<ChooseActivityPresenter> implements ChooseActivityView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MessageAdapter adapter;
    private List<ChatBean> chatList = new ArrayList<>();
    private int uId;
    private Dialog dialog;
    private ChatBean data;
    private int pid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose;
    }

    @Nullable
    @Override
    protected ChooseActivityPresenter initPresenter() {
        return new ChooseActivityPresenter();
    }

    @Override
    protected void initView() {
        MyApplication.addActivity(this);
        data = (ChatBean) getIntent().getSerializableExtra("chatData");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(this,chatList);
        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                pid = chatList.get(position).getPid();
                showDialog(chatList.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        chatList.clear();
        uId = UserUtils.getInstance().getUserBean().getUser_id();
        if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getChatListTableName(uId))) {
            DataBaseHelper.createChatListTable(getActivity(), uId);
        }
        chatList.addAll(DataBaseHelper.queryChatList(getActivity(), uId));
        ListUtil.sortList(chatList);
        for (int i = 0; i < chatList.size(); i++) {//置顶
            if (chatList.get(i).isTop()) {
                ChatBean chatBean = chatList.get(i);
                chatList.remove(i);
                chatList.add(0, chatBean);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.ll_back, R.id.ll_search, R.id.ll_my_friend, R.id.ll_my_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                break;
            case R.id.ll_my_friend:
                Intent intent = new Intent(this, MyFriendActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("chatData",data);
                startActivity(intent);
                break;
            case R.id.ll_my_group:
                Intent intent1 = new Intent(this, MyGroupActivity.class);
                intent1.putExtra("type",2);
                intent1.putExtra("chatData",data);
                startActivity(intent1);
                break;
        }
    }

    private void showDialog(ChatBean chatBean){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_forward);
        }
        RoundedImageView rivHeadIcon = dialog.findViewById(R.id.riv_head_icon);
        Glide.with(this).load(chatBean.getUserIconUrl()).into(rivHeadIcon);
        TextView tvName = dialog.findViewById(R.id.tv_name);
        tvName.setText(chatBean.getName());
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
                data.setUserinfo(new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(),UserUtils.getInstance().getUserBean().getUser_portrait()));
                data.setpUserInfo(new ChatUserInfoBean(chatBean.getName(),chatBean.getUserIconUrl()));
                data.setPid(chatBean.getPid());
                data.setType(chatBean.getType());
                data.setTime(DateUtil.dateToString(new Date()));
                data.setFlag(1);
                if(chatBean.getType() == Constant.CHAT_GROUP_TYPE){
                    data.setGroupinfo(new ChatUserInfoBean(chatBean.getName(),chatBean.getUserIconUrl(),chatBean.getGroupType()));
                }
                sendMessage(data,addChatMessageToDataBase(data,"0"));
                if(!StringUtils.isEmpty(etText.getText().toString())){
                    ChatBean chat = new ChatBean(pid,etText.getText().toString(),UserUtils.getInstance().getUserBean().getUser_id(),chatBean.getType()
                            , Constant.CHAT_TEXT,1,UserUtils.getInstance().getUserBean().getUser_portrait()
                            ,new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(),UserUtils.getInstance().getUserBean().getUser_portrait())
                            , DateUtil.dateToString(new Date()),new ChatUserInfoBean(chatBean.getName(),chatBean.getUserIconUrl()));
                    if(chatBean.getType() == Constant.CHAT_GROUP_TYPE){
                        chat.setGroupinfo(new ChatUserInfoBean(chatBean.getName(),chatBean.getUserIconUrl(),chatBean.getGroupType()));
                    }
                    sendMessage(chat,addChatMessageToDataBase(chat,"0"));
                }
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void sendMessageSuccess(int code, MessageId data, long id) {
        updateChatRecordMsgId(id,data.getMsgid());
        ToastUtils.showToast(this,"已发送~");
        MyApplication.clearActivity();
    }

    @Override
    public void sendMessageFailed(int code, String msg) {

    }

    /**
     * 给服务器发送消息
     * @param chatBean
     */
    private void sendMessage(ChatBean chatBean,long id){
        SendMessageEvent sendMessageEvent = new SendMessageEvent(chatBean);
        EventBus.getDefault().post(sendMessageEvent);
        Map<String,String> map = new HashMap<>();
        map.put("pid",String.valueOf(chatBean.getPid()));
        map.put("stxt",chatBean.getStxt());
        map.put("mid",String.valueOf(uId));
        map.put("type",String.valueOf(chatBean.getType()));
        if(!StringUtils.isEmpty(chatBean.getTemp())){
            map.put("temp",chatBean.getTemp());
        }
        map.put("fileype",String.valueOf(chatBean.getFileype()));
        map.put("userinfo", chatBean.getUserinfo().toJson());
        if(chatBean.getType() == Constant.CHAT_GROUP_TYPE){
            map.put("groupinfo",chatBean.getGroupinfo().toJson());
        }
        presenter.sendMessage(map,id);
    }

    /**
     * 将聊天记录添加到本地数据库
     */
    private long addChatMessageToDataBase(ChatBean chatBean,String msgId) {
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
        values.put("msgid",msgId);
        if(chatBean.getType() == Constant.CHAT_ONE_TYPE){
            if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getChatRecordTableName(chatBean.getPid(), uId))) {
                DataBaseHelper.createChatTable(getActivity(), chatBean.getPid(), uId);
            }
            return DataBaseHelper.insertChatRecord(getActivity(), chatBean.getPid(), uId, values);
        }else if(chatBean.getType() == Constant.CHAT_GROUP_TYPE){
            if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getGroupChatRecordTableName(chatBean.getPid(), uId))) {
                DataBaseHelper.createGroupChatTable(getActivity(), chatBean.getPid(), uId);
            }
            return DataBaseHelper.insertGroupChatRecord(getActivity(), chatBean.getPid(), uId, values);
        }
        return 0;
    }

    /**
     * 更改聊天记录消息id
     * @param id
     * @param msgId
     */
    private void updateChatRecordMsgId(long id,String msgId){
        ContentValues values = new ContentValues();
        values.put("msgid",msgId);
        DataBaseHelper.updateChatRecord(getActivity(),pid,uId,values,id);
    }
}
