package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Parcelable;
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
import com.dan.yousuanshi.module.addressbook.adapter.MyFriendAdapter;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.presenter.MyFriendPresenter;
import com.dan.yousuanshi.module.addressbook.ui.HintSideBar;
import com.dan.yousuanshi.module.addressbook.ui.SideBar;
import com.dan.yousuanshi.module.addressbook.view.MyFriendView;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatUserInfoBean;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.PinyinUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFriendActivity extends BaseActivity<MyFriendPresenter> implements MyFriendView, SideBar.OnChooseLetterChangedListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.hintSideBar)
    HintSideBar hintSideBar;

    private List<MyFriendBean> friendList = new ArrayList<>();
    private MyFriendAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<MyFriendBean> util = new ArrayList<>();
    private int type = 1;//1为正常入口  2为选择好友入口
    private int uId;
    private int pId;
    private ChatBean data;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_friend;
    }

    @Nullable
    @Override
    protected MyFriendPresenter initPresenter() {
        return new MyFriendPresenter();
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type",1);
        if(type == 2){
            MyApplication.addActivity(this);
            data = (ChatBean) getIntent().getSerializableExtra("chatData");
        }
        uId = UserUtils.getInstance().getUserBean().getUser_id();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyFriendAdapter(this, friendList);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);//禁止滑动
        hintSideBar.setOnChooseLetterChangedListener(this);
        MyApplication.addActivity(this);
    }

    @Override
    protected void loadData() {
        if(type == 1){
            MyFriendBean addressBook = new MyFriendBean();
            addressBook.setNick_name("手机通讯录");
            addressBook.setUser_id(R.mipmap.icon_my_friend_address_book);
            addressBook.setType(1);
            addressBook.setHeadLetter('&');
            friendList.add(0,addressBook);
        }
        adapter.setOnItemClick(new MyFriendAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                if(type == 1){
                    Intent intent = new Intent(MyFriendActivity.this,FriendInfoActivity.class);
                    intent.putExtra("data",friendList.get(position).getUser_id());
                    startActivity(intent);
                }else{
                    pId = friendList.get(position).getUser_id();
                    showDialog(friendList.get(position));
                }
            }

            @Override
            public void phoneAddressBook() {
                ToastUtils.showToast(MyFriendActivity.this,"手机通讯录");
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMyFriend();
    }

    @Override
    public void getMyFriendSuccess(int code, List<MyFriendBean> data) {
        if(code == 0){
            if(data.size()>0){
                friendList.clear();
                for(MyFriendBean item :data){
                    item.setHeadLetter(PinyinUtil.getHeadChar(item.getNick_name()));
                }
                friendList.addAll(data);
                Collections.sort(friendList);
                MyFriendBean addressBook = new MyFriendBean();
                addressBook.setNick_name("手机通讯录");
                addressBook.setUser_id(R.mipmap.icon_my_friend_address_book);
                addressBook.setType(1);
                addressBook.setHeadLetter('&');
                friendList.add(0,addressBook);
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
    }

    @Override
    public void getMyFriendFailed(int code, String msg) {

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

    @OnClick({R.id.ll_back, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                Intent intent = new Intent(this,SearchMyFriendActivity.class);
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent);
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

    /**
     * 给服务器发送消息
     * @param chatBean
     */
    private void sendMessage(ChatBean chatBean, long id){
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
        if(chatBean.getFileype() == Constant.INVITE_JOIN_TEAM){
            values.put("message_txt", chatBean.getTemp());
        }else{
            values.put("message_txt", chatBean.getStxt());
        }
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
        DataBaseHelper.updateChatRecord(getActivity(),pId,uId,values,id);
    }

    private void showDialog(MyFriendBean myFriendBean){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_forward);
        }
        RoundedImageView rivHeadIcon = dialog.findViewById(R.id.riv_head_icon);
        Glide.with(this).load(myFriendBean.getUser_portrait()).into(rivHeadIcon);
        TextView tvName = dialog.findViewById(R.id.tv_name);
        tvName.setText(myFriendBean.getNick_name());
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
                data.setpUserInfo(new ChatUserInfoBean(myFriendBean.getNick_name(),myFriendBean.getUser_portrait()));
                data.setPid(pId);
                data.setType(Constant.CHAT_ONE_TYPE);
                data.setTime(DateUtil.dateToString(new Date()));
                sendMessage(data,addChatMessageToDataBase(data,"0"));
                if(!StringUtils.isEmpty(etText.getText().toString())){
                    ChatBean chat = new ChatBean(pId,etText.getText().toString(),UserUtils.getInstance().getUserBean().getUser_id(),Constant.CHAT_ONE_TYPE
                            , Constant.CHAT_TEXT,1,UserUtils.getInstance().getUserBean().getUser_portrait()
                            ,new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(),UserUtils.getInstance().getUserBean().getUser_portrait())
                            , DateUtil.dateToString(new Date()),new ChatUserInfoBean(myFriendBean.getNick_name(),myFriendBean.getUser_portrait()));
                    sendMessage(chat,addChatMessageToDataBase(chat,"0"));
                }
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

}
