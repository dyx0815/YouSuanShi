package com.dan.yousuanshi.module.addressbook.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.event.SendMessageEvent;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.presenter.FriendInfoActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.FriendInfoActivityView;
import com.dan.yousuanshi.module.chat.activity.ChatActivity;
import com.dan.yousuanshi.module.chat.activity.SharedBusinessCardActivity;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatUserInfoBean;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.dan.yousuanshi.utils.popupwindow.PopUpMenuBean;
import com.dan.yousuanshi.utils.popupwindow.PopupWindowMenuUtil;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class FriendInfoActivity extends BaseActivity<FriendInfoActivityPresenter> implements FriendInfoActivityView {

    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.ll_friend_info_null)
    LinearLayout llFriendInfoNull;
    @BindView(R.id.ll_send_message)
    LinearLayout llSendMessage;
    @BindView(R.id.ll_call)
    LinearLayout llCall;
    @BindView(R.id.ll_video)
    LinearLayout llVideo;
    @BindView(R.id.ll_add_friend)
    LinearLayout llAddFriend;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.ll_team)
    LinearLayout llTeam;
    @BindView(R.id.tv_department_name)
    TextView tvDepartmentName;
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    @BindView(R.id.tv_job_name)
    TextView tvJobName;
    @BindView(R.id.ll_job)
    LinearLayout llJob;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.ll_info_info)
    LinearLayout llInfoInfo;
    @BindView(R.id.iv_send_message)
    ImageView ivSendMessage;
    @BindView(R.id.tv_send_message)
    TextView tvSendMessage;
    @BindView(R.id.iv_call)
    ImageView ivCall;
    @BindView(R.id.tv_call)
    TextView tvCall;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.ll_more)
    LinearLayout llMore;

    private int uId;
    private QueryUserBean userBean;
    private Dialog dialog;
    private Dialog deleteDialog;
    private Dialog addBlackListDialog;
    private Dialog removeBlackListDialog;
    private String phoneHide;
    private String phone;
    private ArrayList<PopUpMenuBean> popUpMenuBeans;
    private int pid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_info;
    }

    @Nullable
    @Override
    protected FriendInfoActivityPresenter initPresenter() {
        return new FriendInfoActivityPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        uId = intent.getIntExtra("data", 0);
        Map<String, String> map = new HashMap<>();
        map.put("uid", String.valueOf(uId));
        presenter.getUserInfoById(map);
        MyApplication.addActivity(this);
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this, getColor(R.color.color_F6D365), 0);
    }

    @Override
    public void getUserByIdSuccess(int code, QueryUserBean queryUserBean) {
        userBean = queryUserBean;
        Glide.with(this).load(queryUserBean.getUser_portrait()).into(rivHeadIcon);
        tvNickName.setText(queryUserBean.getNick_name());
        tvTeamName.setText(UserUtils.getInstance().getUserBean().getC_name());
        tvDepartmentName.setText(queryUserBean.getUser_department());
        tvJobName.setText(queryUserBean.getUser_position());
        tvName.setText(queryUserBean.getReal_name());
        phone = queryUserBean.getUser_tel();
        if (!TextUtils.isEmpty(queryUserBean.getUser_tel())) {
            phoneHide = queryUserBean.getUser_tel().replaceFirst(queryUserBean.getUser_tel().substring(3, 8), "*****");
        }
        tvPhone.setText("+86 " + phoneHide);
        if (queryUserBean.getIs_friend() == 0 && queryUserBean.getSame_company() == 0) {
            Log.e("FriendInfoActivity", "非同一家公司非好友");//非同一家公司,非好友
            llFriendInfoNull.setVisibility(View.VISIBLE);
            llSendMessage.setEnabled(false);
            llCall.setEnabled(false);
            llVideo.setEnabled(false);
            llAddFriend.setEnabled(true);
            isSendMessage(llSendMessage.isEnabled());
            isCall(llCall.isEnabled());
            isVideo(llVideo.isEnabled());
        } else if (queryUserBean.getIs_friend() == 1 && queryUserBean.getSame_company() == 0) { //非同一家公司,好友
            llFriendInfoNull.setVisibility(View.VISIBLE);
            llSendMessage.setEnabled(true);
            llCall.setEnabled(true);
            llVideo.setEnabled(true);
            llAddFriend.setVisibility(View.GONE);
            isSendMessage(llSendMessage.isEnabled());
            isCall(llCall.isEnabled());
            isVideo(llVideo.isEnabled());
            llMore.setVisibility(View.VISIBLE);
        } else if (queryUserBean.getIs_friend() == 1 && queryUserBean.getSame_company() == 1) {//同一家公司,好友
            llInfoInfo.setVisibility(View.VISIBLE);
            llSendMessage.setEnabled(true);
            llCall.setEnabled(true);
            llVideo.setEnabled(true);
            llAddFriend.setVisibility(View.GONE);
            isSendMessage(llSendMessage.isEnabled());
            isCall(llCall.isEnabled());
            isVideo(llVideo.isEnabled());
            llMore.setVisibility(View.VISIBLE);
        } else {//同一家公司 非好友
            llInfoInfo.setVisibility(View.VISIBLE);
            llSendMessage.setEnabled(true);
            llCall.setEnabled(true);
            llVideo.setEnabled(true);
            llAddFriend.setEnabled(true);
            isSendMessage(llSendMessage.isEnabled());
            isCall(llCall.isEnabled());
            isVideo(llVideo.isEnabled());
        }
    }

    @Override
    public void getUserByIdFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取用户信息失败：" + code + "\t" + msg);
        Log.e("FriendInfoActivity", "获取用户信息失败：" + code + "\t" + msg);
    }

    @Override
    public void deleteFriendSuccess(int code, List list) {
        ToastUtils.showToast(this, "删除好友");
        finish();
    }

    @Override
    public void deleteFriendFailed(int code, String msg) {
        ToastUtils.showToast(this, "删除好友失败：" + code + "\t" + msg);
        Log.e("FriendInfoActivity", "删除好友失败：" + code + "\t" + msg);
    }

    @Override
    public void sendMessageSuccess(int code, MessageId messageId, long id) {
        updateChatRecordMsgId(id, messageId.getMsgid());
    }

    @Override
    public void sendMessageFailed(int code, String msg) {
        ToastUtils.showToast(this, "发送消息失败：" + code + "\t" + msg);
        Log.e("FriendInfoActivity", "发送消息失败：" + code + "\t" + msg);
    }

    @Override
    public void addBlackListSuccess(int code, List list) {
        ToastUtils.showToast(this,"加入黑名单成功！");
        finish();
    }

    @Override
    public void addBlackListFailed(int code, String msg) {
        ToastUtils.showToast(this, "加入黑名单失败：" + code + "\t" + msg);
        Log.e("FriendInfoActivity", "加入黑名单失败：" + code + "\t" + msg);
    }

    @Override
    public void removeBlackListSuccess(int code, List list) {
        ToastUtils.showToast(this,"解除黑名单成功！");
        finish();
    }

    @Override
    public void removeBlackListFailed(int code, String msg) {
        ToastUtils.showToast(this, "解除黑名单失败：" + code + "\t" + msg);
        Log.e("FriendInfoActivity", "解除黑名单失败：" + code + "\t" + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_send_message, R.id.ll_call, R.id.ll_video, R.id.ll_add_friend, R.id.ll_phone, R.id.tv_show, R.id.ll_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_send_message:
                Intent chatIntent = new Intent(this, ChatActivity.class);
                ChatBean pUser = new ChatBean(userBean.getUser_id(), userBean.getNick_name(), userBean.getUser_portrait(), Constant.CHAT_ONE_TYPE);
                chatIntent.putExtra("user", pUser);
                startActivity(chatIntent);
                MyApplication.clearActivity();
                break;
            case R.id.ll_call:
                break;
            case R.id.ll_video:
                break;
            case R.id.ll_add_friend:
                MyApplication.addActivity(this);
                Intent addFriendIntent = new Intent(this, AddFriendVerificationActivity.class);
                addFriendIntent.putExtra("id", String.valueOf(userBean.getUser_id()));
                startActivity(addFriendIntent);
                break;
            case R.id.ll_phone:
                showDialog();
                break;
            case R.id.tv_show:
                if (tvPhone.getText().toString().equals("+86 " + phoneHide)) {
                    tvPhone.setText("+86 " + phone);
                    tvShow.setText("隐藏");
                } else {
                    tvPhone.setText("+86 " + phoneHide);
                    tvShow.setText("显示");
                }
                break;
            case R.id.ll_more:
                showMenu();
                break;
        }
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.friend_info_Dialog);
            dialog.setContentView(R.layout.dialog_friend_info);
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            dialog.getWindow().setAttributes(layoutParams);
            LinearLayout llCall = dialog.findViewById(R.id.ll_phone);
            LinearLayout llCopy = dialog.findViewById(R.id.ll_copy);
            LinearLayout llSave = dialog.findViewById(R.id.ll_save);
            TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
            llCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + userBean.getUser_tel());
                    intent.setData(data);
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                    dialog.dismiss();
                    startActivity(intent);
                }
            });
            llCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(userBean.getUser_tel());
                    ToastUtils.showToast(FriendInfoActivity.this, "已复制");
                    dialog.dismiss();
                }
            });
            llSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePhone(userBean.getReal_name(), userBean.getUser_tel());
                    dialog.dismiss();
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void showDeleteDialog() {
        if (deleteDialog == null) {
            deleteDialog = new Dialog(this);
            deleteDialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = deleteDialog.findViewById(R.id.tv_title);
            tvTitle.setText("是否删除此好友?");
            TextView tvSure = deleteDialog.findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<>();
                    map.put("friendId", String.valueOf(uId));
                    presenter.deleteFriend(map);
                }
            });
            TextView tvCancel = deleteDialog.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();
                }
            });
        }
        deleteDialog.show();
    }

    /*保存电话*/
    //根据电话号码查询姓名（在一个电话打过来时，如果此电话在通讯录中，则显示姓名）
    public void savePhone(String name, String phone) {

        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + phone);
        ContentResolver resolver = this.getContext().getContentResolver();
//        ContactsContract.Data.DISPLAY_NAME 查询 该电话的客户姓名

        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data.HAS_PHONE_NUMBER}, null, null, null); //从raw_contact表中返回display_name
        int count = cursor.getCount();

        if (count > 0) {
            if (cursor.moveToFirst()) {
                String hasPhone = cursor.getString(0);//查询该电话有没有人
                if (TextUtils.isEmpty(hasPhone)) {//没有该电话
                    insertPhone(name, phone);
                } else if ("0".equals(hasPhone)) {//没有该电话
                    insertPhone(name, phone);
                } else {
                    ToastUtils.showToast(this, "该电话号码已存在!");
                }

            }
        } else {
            insertPhone(name, phone);
        }

        cursor.close();
    }

    /**
     * 添加到通讯录
     *
     * @param name
     * @param phone
     */
    private void insertPhone(String name, String phone) {
        if (TextUtils.isEmpty(name)) {
            name = phone;
        }
        ContentValues values = new ContentValues(); //首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        Uri rawContactUri = this.getContext().getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);//获取id
        long rawContactId = ContentUris.parseId(rawContactUri); //往data表入姓名数据
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId); //添加id
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);//添加内容类型（MIMETYPE）
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);//添加名字，添加到first name位置
        this.getContext().getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values); //往data表入电话数据
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        this.getContext().getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        ToastUtils.showToast(this, "已保存到通讯录！");
    }

    private void isSendMessage(boolean enable) {
        if (enable) {
            ivSendMessage.setImageResource(R.mipmap.icon_friend_info__send_message_enabled);
            tvSendMessage.setTextColor(getColor(R.color.color_F99E05));
        } else {
            ivSendMessage.setImageResource(R.mipmap.icon_friend_info__send_message);
            tvSendMessage.setTextColor(getColor(R.color.color_999999));
        }
    }

    private void isCall(boolean enable) {
        if (enable) {
            ivCall.setImageResource(R.mipmap.icon_friend_info_call_enabled);
            tvCall.setTextColor(getColor(R.color.color_F99E05));
        } else {
            ivCall.setImageResource(R.mipmap.icon_friend_info_call);
            tvCall.setTextColor(getColor(R.color.color_999999));
        }
    }

    private void isVideo(boolean enable) {
        if (enable) {
            ivVideo.setImageResource(R.mipmap.icon_friend_info_video_enabled);
            tvVideo.setTextColor(getColor(R.color.color_F99E05));
        } else {
            ivVideo.setImageResource(R.mipmap.icon_friend_info_video);
            tvVideo.setTextColor(getColor(R.color.color_999999));
        }
    }

    /**
     * 右上角加号菜单
     */
    public void showMenu() {
        popUpMenuBeans = new ArrayList<>();
        PopUpMenuBean popUpMenuBean = new PopUpMenuBean();
        popUpMenuBean.setImgResId(R.mipmap.icon_update_remarks);
        popUpMenuBean.setItemStr("修改备注");
        PopUpMenuBean popUpMenuBean2 = new PopUpMenuBean();
        popUpMenuBean2.setImgResId(R.mipmap.icon_send_card);
        popUpMenuBean2.setItemStr("发送名片");
        PopUpMenuBean popUpMenuBean3 = new PopUpMenuBean();
        popUpMenuBean3.setImgResId(R.mipmap.icon_delete_friend);
        popUpMenuBean3.setItemStr("删除好友");
        PopUpMenuBean popUpMenuBean4 = new PopUpMenuBean();
        if (userBean.getBlack_id() == 0) {//非黑名单
            popUpMenuBean4.setImgResId(R.mipmap.icon_add_black_list);
            popUpMenuBean4.setItemStr("加入黑名单");
        } else {
            popUpMenuBean4.setImgResId(R.mipmap.icon_remove_black_list);
            popUpMenuBean4.setItemStr("解除黑名单");
        }
        popUpMenuBeans.add(popUpMenuBean);
        popUpMenuBeans.add(popUpMenuBean2);
        popUpMenuBeans.add(popUpMenuBean3);
        popUpMenuBeans.add(popUpMenuBean4);
        PopupWindowMenuUtil.showPopupWindows(getActivity(), llMore, popUpMenuBeans, new PopupWindowMenuUtil.OnListItemClickLitener() {
            @Override
            public void onListItemClick(int position) {
                PopupWindowMenuUtil.closePopupWindows();
                if (position == 0) {//修改备注
                    Intent intent = new Intent(FriendInfoActivity.this, UpdateRemarkActivity.class);
                    intent.putExtra("id", uId);
                    startActivity(intent);
                    finish();
                } else if (position == 1) {//发送名片
                    startActivityForResult(new Intent(FriendInfoActivity.this, SharedBusinessCardActivity.class), 2);
                } else if (position == 2) {//删除好友
                    showDeleteDialog();
                } else if (position == 3) {//加入黑名单
                    if (userBean.getBlack_id() == 0) {//非黑名单，加入
                        showAddBlackListDialog();
                    }else{//在黑名单，解除
                        showRemoveBlackListDialog();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 6) {
            int type = 2;
            MyFriendBean myFriendBean = data.getParcelableExtra("data");
            pid = myFriendBean.getUser_id();
            ChatBean cardBean = new ChatBean(myFriendBean.getUser_id(), new Gson().toJson(userBean), UserUtils.getInstance().getUserBean().getUser_id(), type
                    , Constant.CHAT_CARD, 1, UserUtils.getInstance().getUserBean().getUser_portrait()
                    , new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait())
                    , DateUtil.dateToString(new Date()), new ChatUserInfoBean(myFriendBean.getNick_name(), myFriendBean.getUser_portrait()));
            sendMessage(cardBean, addChatMessageToDataBase(cardBean, "0"));
        }
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
        return DataBaseHelper.insertChatRecord(getActivity(), chatBean.getPid(), UserUtils.getInstance().getUserBean().getUser_id(), values);
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
        DataBaseHelper.updateChatRecord(getActivity(), pid, uId, values, id);
    }

    private void showAddBlackListDialog(){
        if(addBlackListDialog == null){
            addBlackListDialog = new Dialog(this);
            addBlackListDialog.setContentView(R.layout.dialog_add_black_list);
            addBlackListDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addBlackListDialog.dismiss();
                }
            });
            addBlackListDialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addBlackListDialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("userid",uId);
                    presenter.addBlackList(map);
                }
            });
        }
        addBlackListDialog.show();
    }

    private void showRemoveBlackListDialog(){
        if(removeBlackListDialog == null){
            removeBlackListDialog = new Dialog(this);
            removeBlackListDialog.setContentView(R.layout.dialog_remove_black_list);
            removeBlackListDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeBlackListDialog.dismiss();
                }
            });
            removeBlackListDialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeBlackListDialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("blackid",userBean.getBlack_id());
                    presenter.removeBlackList(map);
                }
            });
        }
        removeBlackListDialog.show();
    }
}
