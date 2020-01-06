package com.dan.yousuanshi.module.chat.fragment;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseFragment;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.event.AddMessageCountEvent;
import com.dan.yousuanshi.event.ReceiveMessageEvent;
import com.dan.yousuanshi.event.SendMessageEvent;
import com.dan.yousuanshi.module.addressbook.activity.AddFriendActivity;
import com.dan.yousuanshi.module.addressbook.activity.ApplyJoinTeamActivity;
import com.dan.yousuanshi.module.addressbook.activity.FriendInfoActivity;
import com.dan.yousuanshi.module.chat.activity.ChatActivity;
import com.dan.yousuanshi.module.chat.activity.MyQRCodeActivity;
import com.dan.yousuanshi.module.chat.activity.ReserveMeetingActivity;
import com.dan.yousuanshi.module.chat.activity.SearchActivity;
import com.dan.yousuanshi.module.chat.activity.SharedBusinessCardActivity;
import com.dan.yousuanshi.module.chat.adapter.MessageAdapter;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatUserInfoBean;
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;
import com.dan.yousuanshi.module.chat.bean.RemoveChatMessageCount;
import com.dan.yousuanshi.module.chat.presenter.ChatPresenter;
import com.dan.yousuanshi.module.chat.utils.NotificationUtil;
import com.dan.yousuanshi.module.chat.view.ChatFragmentView;
import com.dan.yousuanshi.utils.FileItem;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.ListUtil;
import com.dan.yousuanshi.utils.MyHanlder;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.dan.yousuanshi.utils.Utils;
import com.dan.yousuanshi.utils.popupwindow.PopUpMenuBean;
import com.dan.yousuanshi.utils.popupwindow.PopupWindowMenuUtil;
import com.google.gson.Gson;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.download.DownloadInfo;
import per.goweii.rxhttp.download.RxDownload;

public class ChatFragment extends BaseFragment<ChatPresenter> implements ChatFragmentView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.iv_search)
    ImageView ivSearch;

    private ArrayList<PopUpMenuBean> popUpMenuBeans;
    private MessageAdapter messageAdapter;
    private List<ChatBean> chatList = new ArrayList<>();
    private int uId = 0;
    private ChatBean chatBean;
    private Gson gson = new Gson();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Nullable
    @Override
    protected ChatPresenter initPresenter() {
        return new ChatPresenter();
    }

    @Override
    protected void initView() {
        messageAdapter = new MessageAdapter(getActivity(), chatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("user", chatList.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    protected void loadData() {
        uId = UserUtils.getInstance().getUserBean().getUser_id();
    }

    @Override
    public void onResume() {
        super.onResume();
        chatList.clear();
        if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getChatListTableName(uId))) {
            DataBaseHelper.createChatListTable(getActivity(), uId);
        }
        chatList.addAll(DataBaseHelper.queryChatList(getActivity(), uId));
        ListUtil.sortList(chatList);
        AddMessageCountEvent addMessageCountEvent = new AddMessageCountEvent();
        for (int i = 0; i < chatList.size(); i++) {//置顶
            addMessageCountEvent.setMessageCount(addMessageCountEvent.getMessageCount()+chatList.get(i).getMessageCount());
            if (chatList.get(i).isTop()) {
                ChatBean chatBean = chatList.get(i);
                chatList.remove(i);
                chatList.add(0, chatBean);
            }
        }
        EventBus.getDefault().post(addMessageCountEvent);
        messageAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_more, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                showMenu();
                break;
            case R.id.iv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    /**
     * 右上角加号菜单
     */
    public void showMenu() {
        popUpMenuBeans = new ArrayList<>();
        PopUpMenuBean popUpMenuBean = new PopUpMenuBean();
        popUpMenuBean.setImgResId(R.mipmap.icon_scan);
        popUpMenuBean.setItemStr("扫一扫");
        PopUpMenuBean popUpMenuBean2 = new PopUpMenuBean();
        popUpMenuBean2.setImgResId(R.mipmap.icon_menu_chat);
        popUpMenuBean2.setItemStr("发起群聊");
        PopUpMenuBean popUpMenuBean3 = new PopUpMenuBean();
        popUpMenuBean3.setImgResId(R.mipmap.icon_add_friend);
        popUpMenuBean3.setItemStr("添加好友");
        PopUpMenuBean popUpMenuBean4 = new PopUpMenuBean();
        popUpMenuBean4.setImgResId(R.mipmap.icon_reserve);
        popUpMenuBean4.setItemStr("预约会议");
        PopUpMenuBean popUpMenuBean5 = new PopUpMenuBean();
        popUpMenuBean5.setImgResId(R.mipmap.icon_mine_info);
        popUpMenuBean5.setItemStr("名片二维码");
        popUpMenuBeans.add(popUpMenuBean);
        popUpMenuBeans.add(popUpMenuBean2);
        popUpMenuBeans.add(popUpMenuBean3);
        popUpMenuBeans.add(popUpMenuBean4);
        popUpMenuBeans.add(popUpMenuBean5);
        PopupWindowMenuUtil.showPopupWindows(getActivity(), ivMore, popUpMenuBeans, new PopupWindowMenuUtil.OnListItemClickLitener() {
            @Override
            public void onListItemClick(int position) {
                PopupWindowMenuUtil.closePopupWindows();
                if (position == 0) {
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    ZxingConfig config = new ZxingConfig();
                    config.setPlayBeep(true);//是否播放扫描声音 默认为true
                    config.setShake(true);//是否震动  默认为true
                    config.setDecodeBarCode(true);//是否扫描条形码 默认为true
                    config.setReactColor(R.color.color_F99E05);//设置扫描框四个角的颜色 默认为白色
                    config.setFrameLineColor(R.color.transparent);//设置扫描框边框颜色 默认无色
                    config.setScanLineColor(R.color.color_F99E05);//设置扫描线的颜色 默认白色
                    config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                    intent.putExtra(com.yzq.zxinglibrary.common.Constant.INTENT_ZXING_CONFIG, config);
                    startActivityForResult(intent, 1);
                } else if (position == 1) {//发起群聊
                    Intent intent = new Intent(getActivity(), SharedBusinessCardActivity.class);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                } else if (position == 2) {//添加好友
                    Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                } else if (position == 3) {//预约会议
                    startActivity(new Intent(getActivity(), ReserveMeetingActivity.class));
                } else if (position == 4) {//名片二维码
                    startActivity(new Intent(getActivity(), MyQRCodeActivity.class));
                }
            }
        });
    }

    /**
     * 收到消息把消息存到本地 并更新消息列表
     *
     * @param chatBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addChatList(ChatBean chatBean) {
        Log.e("Socket", "ChatFragment收到消息：" + chatBean.toString());
        if (chatBean.getFileype() == Constant.CHAT_TEXT) {//文本消息
            chatBean.setpUserInfo(chatBean.getUserinfo());
            saveMessageListToDataBase(chatBean);
            chatBean.setUserIconUrl(chatBean.getpUserInfo().getAvatar());
            receiveMessageToChatActivity(chatBean);
        } else if (chatBean.getFileype() == Constant.CHAT_VOICE) {//语音消息
            downLoadVoice(chatBean).start();
        } else if (chatBean.getFileype() == Constant.CHAT_PIC) {//图片消息
            downLoadPic(chatBean).start();
        } else if (chatBean.getFileype() == Constant.CHAT_VIDEO) {//视频消息
            downLoadVideo(chatBean).start();
        } else if (chatBean.getFileype() == Constant.CHAT_FILE) {//文件消息
            downLoadFile(chatBean).start();
        } else if (chatBean.getFileype() == Constant.CHAT_LOCATION) {//位置消息
            downLoadLocation(chatBean).start();
        } else if (chatBean.getFileype() == Constant.CHAT_CARD) {//名片消息
            chatBean.setpUserInfo(chatBean.getUserinfo());
            saveMessageListToDataBase(chatBean);
            chatBean.setUserIconUrl(chatBean.getpUserInfo().getAvatar());
            receiveMessageToChatActivity(chatBean);
        }else if(chatBean.getFileype() == Constant.CHAT_GROUP_NOTIFICATION || chatBean.getFileype() == Constant.CHAT_EXIT_GROUP || chatBean.getFileype() == Constant.CHAT_ADD_GROUP){
            //8.群通知,9.有人退群,10有人加群
            chatBean.setpUserInfo(chatBean.getUserinfo());
            saveMessageListToDataBase(chatBean);
            receiveMessageToChatActivity(chatBean);
        } else if (chatBean.getFileype() == Constant.INVITE_JOIN_TEAM) {//邀请人加入公司消息
            chatBean.setpUserInfo(chatBean.getUserinfo());
            saveMessageListToDataBase(chatBean);
            chatBean.setUserIconUrl(chatBean.getpUserInfo().getAvatar());
            chatBean.setStxt(chatBean.getTemp());
            receiveMessageToChatActivity(chatBean);
        }else if(chatBean.getFileype() == Constant.CHAT_FRIEND_AGREE_YOU){
            chatBean.setpUserInfo(chatBean.getUserinfo());
            saveMessageListToDataBase(chatBean);
            chatBean.setUserIconUrl(chatBean.getpUserInfo().getAvatar());
        }
    }

    /**
     * 收到消息并将消息转发到ChatActivity
     */
    private void receiveMessageToChatActivity(ChatBean chatBean) {
        ReceiveMessageEvent receiveMessageEvent = new ReceiveMessageEvent(chatBean);
        EventBus.getDefault().post(receiveMessageEvent);
        addChatMessageToDataBase(chatBean, chatBean.getMsgid());
    }

    /**
     * 发送消息时将最后一条消息保存在消息列表上
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendMessageSave(SendMessageEvent sendMessageEvent) {
        this.chatBean = sendMessageEvent.getChatBean();
        saveMessageListToDataBase(sendMessageEvent.getChatBean());
    }

    /**
     * 删除消息数量
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void removeMessageCountByUser(RemoveChatMessageCount messageCount) {
        for (ChatBean item : chatList) {
            if (item.getType() == messageCount.getChatBean().getType()) {
                if (item.getPid() == messageCount.getChatBean().getPid()) {
                    Integer count = item.getMessageCount();
                    EventBus.getDefault().post(count);
                    item.setMessageCount(0);
                    DataBaseHelper.removeMessageCount(getActivity(), uId, item.getPid());
                }
            }
        }
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getUserInfoSuccess(int code, QueryUserBean user) {
        for (int i = 0; i < chatList.size(); i++) {
            if (chatList.get(i).getPid() == user.getUser_id()) {
                if (chatList.get(i).getUserIconUrl().equals(user.getUser_portrait()) && chatList.get(i).getName().equals(user.getNick_name())) {
                    return;
                } else {
                    Log.e("ChatFragment", "更新用户：" + chatList.get(i).getName());
                    chatList.get(i).setUserIconUrl(user.getUser_portrait());
                    chatList.get(i).setName(user.getNick_name());
                    messageAdapter.notifyItemChanged(i);
                    updateChatToDataBase(chatList.get(i));
                    return;
                }
            }
        }
        chatBean.setUserIconUrl(user.getUser_portrait());
        chatBean.setName(user.getNick_name());
        chatList.add(chatBean);
        ListUtil.sortList(chatList);
        messageAdapter.notifyDataSetChanged();
        sendNotification(chatBean);
    }

    @Override
    public void getUserInfoFailed(int code, String message) {
        Log.e("Chat", "请求用户信息失败 code:" + code + "\t" + message);
    }

    /**
     * 将聊天记录添加到本地数据库
     */
    private long addChatMessageToDataBase(ChatBean chatBean, String msgId) {
        ContentValues values = new ContentValues();
        values.put("message_type", chatBean.getFileype());
        values.put("message_txt", chatBean.getStxt());
        values.put("send_time", chatBean.getTime());
        values.put("is_read", chatBean.getIsRead());
        values.put("send_flag", chatBean.getFlag());
        values.put("msgid", msgId);
        if(chatBean.getUserinfo()!=null){
            values.put("head_icon", chatBean.getUserinfo().getAvatar());
        }
        values.put("message_temp", chatBean.getTemp());
        if (chatBean.getType() == Constant.CHAT_ONE_TYPE) {
            if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getChatRecordTableName(chatBean.getPid(), uId))) {
                DataBaseHelper.createChatTable(getActivity(), chatBean.getPid(), uId);
            }
            if (!StringUtils.isEmpty(chatBean.getPath())) {
                values.put("path", chatBean.getPath());
            }
            return DataBaseHelper.insertChatRecord(getActivity(), chatBean.getPid(), uId, values);
        } else if (chatBean.getType() == Constant.CHAT_GROUP_TYPE) {
            if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getGroupChatRecordTableName(chatBean.getPid(), uId))) {
                DataBaseHelper.createGroupChatTable(getActivity(), chatBean.getPid(), uId);
            }
            if(chatBean.getUserinfo()!=null){
                values.put("nick_name", chatBean.getUserinfo().getNickName());
            }
            values.put("s_id", chatBean.getsId());
            return DataBaseHelper.insertGroupChatRecord(getActivity(), chatBean.getPid(), uId, values);
        }
        return 0;
    }

    private RxDownload downLoadVoice(final ChatBean chatBean) {
        FileUtils.isFolderExists(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/voice/");
        DownloadInfo downloadInfo = DownloadInfo.create(Constant.DOWNLOAD_URL + chatBean.getTemp()
                , getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/voice/"
                , chatBean.getTemp());
        RxDownload rxDownload = RxDownload.create(downloadInfo).setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                Log.e("DownLoad", "下载开始：" + info.url);
            }

            @Override
            public void onDownloading(DownloadInfo info) {
                Log.e("DownLoad", "正在下载");
            }

            @Override
            public void onStopped(DownloadInfo info) {
                Log.e("DownLoad", "下载停止");
            }

            @Override
            public void onCanceled(DownloadInfo info) {
                Log.e("DownLoad", "下载取消");
            }

            @Override
            public void onCompletion(DownloadInfo info) {
                Log.e("DownLoad", "下载完成");
                chatBean.setPath(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/voice/" + chatBean.getTemp());
                //消息数量+1
                AddMessageCountEvent addMessageCountEvent = new AddMessageCountEvent();
                EventBus.getDefault().post(addMessageCountEvent);
                chatBean.setpUserInfo(chatBean.getUserinfo());
                saveMessageListToDataBase(chatBean);
                chatBean.setUserIconUrl(chatBean.getpUserInfo().getAvatar());
                receiveMessageToChatActivity(chatBean);
                sendNotification(chatBean);
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                Log.e("DownLoad", "下载错误：" + e.toString());
            }
        });
        return rxDownload;
    }

    private RxDownload downLoadPic(final ChatBean chatBean) {
        DownloadInfo downloadInfo = DownloadInfo.create(Constant.DOWNLOAD_URL + chatBean.getTemp()
                , getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/pic/"
                , chatBean.getTemp());
        RxDownload rxDownload = RxDownload.create(downloadInfo).setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                Log.e("DownLoad", "下载开始：" + info.url);
            }

            @Override
            public void onDownloading(DownloadInfo info) {
                Log.e("DownLoad", "正在下载");
            }

            @Override
            public void onStopped(DownloadInfo info) {
                Log.e("DownLoad", "下载停止");
            }

            @Override
            public void onCanceled(DownloadInfo info) {
                Log.e("DownLoad", "下载取消");
            }

            @Override
            public void onCompletion(DownloadInfo info) {
                Log.e("DownLoad", "下载完成");
                chatBean.setPath(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/pic/" + chatBean.getTemp());
                //消息数量+1
                AddMessageCountEvent addMessageCountEvent = new AddMessageCountEvent();
                EventBus.getDefault().post(addMessageCountEvent);
                chatBean.setpUserInfo(chatBean.getUserinfo());
                saveMessageListToDataBase(chatBean);
                chatBean.setUserIconUrl(chatBean.getpUserInfo().getAvatar());
                receiveMessageToChatActivity(chatBean);
                sendNotification(chatBean);
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                Log.e("DownLoad", "下载错误：" + e.toString());
            }
        });
        return rxDownload;
    }

    private RxDownload downLoadVideo(final ChatBean chatBean) {
        DownloadInfo downloadInfo = DownloadInfo.create(Constant.DOWNLOAD_URL + chatBean.getTemp()
                , getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/video/"
                , chatBean.getTemp());
        RxDownload rxDownload = RxDownload.create(downloadInfo).setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                Log.e("DownLoad", "下载开始：" + info.url);
            }

            @Override
            public void onDownloading(DownloadInfo info) {
                Log.e("DownLoad", "正在下载");
            }

            @Override
            public void onStopped(DownloadInfo info) {
                Log.e("DownLoad", "下载停止");
            }

            @Override
            public void onCanceled(DownloadInfo info) {
                Log.e("DownLoad", "下载取消");
            }

            @Override
            public void onCompletion(DownloadInfo info) {
                Log.e("DownLoad", "下载完成");
                chatBean.setPath(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/video/" + chatBean.getTemp());
                //消息数量+1
                AddMessageCountEvent addMessageCountEvent = new AddMessageCountEvent();
                EventBus.getDefault().post(addMessageCountEvent);
                chatBean.setpUserInfo(chatBean.getUserinfo());
                saveMessageListToDataBase(chatBean);
                chatBean.setUserIconUrl(chatBean.getpUserInfo().getAvatar());
                receiveMessageToChatActivity(chatBean);
                sendNotification(chatBean);
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                Log.e("DownLoad", "下载错误：" + e.toString());
            }
        });
        return rxDownload;
    }

    private RxDownload downLoadFile(final ChatBean chatBean) {
        FileItem fileItem = new Gson().fromJson(chatBean.getStxt(), FileItem.class);
        DownloadInfo downloadInfo = DownloadInfo.create(Constant.DOWNLOAD_URL + chatBean.getTemp()
                , getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/file/"
                , fileItem.getFileName());
        RxDownload rxDownload = RxDownload.create(downloadInfo).setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                Log.e("DownLoad", "下载开始：" + info.url);
            }

            @Override
            public void onDownloading(DownloadInfo info) {
                Log.e("DownLoad", "正在下载");
            }

            @Override
            public void onStopped(DownloadInfo info) {
                Log.e("DownLoad", "下载停止");
            }

            @Override
            public void onCanceled(DownloadInfo info) {
                Log.e("DownLoad", "下载取消");
            }

            @Override
            public void onCompletion(DownloadInfo info) {
                Log.e("DownLoad", "下载完成");
                chatBean.setPath(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/file/" + fileItem.getFileName());
                //消息数量+1
                AddMessageCountEvent addMessageCountEvent = new AddMessageCountEvent();
                EventBus.getDefault().post(addMessageCountEvent);
                chatBean.setpUserInfo(chatBean.getUserinfo());
                saveMessageListToDataBase(chatBean);
                chatBean.setUserIconUrl(chatBean.getpUserInfo().getAvatar());
                receiveMessageToChatActivity(chatBean);
                sendNotification(chatBean);
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                Log.e("DownLoad", "下载错误：" + e.toString());
            }
        });
        return rxDownload;
    }

    private RxDownload downLoadLocation(final ChatBean chatBean) {
        DownloadInfo downloadInfo = DownloadInfo.create(Constant.DOWNLOAD_URL + chatBean.getTemp()
                , getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/location/"
                , chatBean.getTemp() + ".png");
        RxDownload rxDownload = RxDownload.create(downloadInfo).setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                Log.e("DownLoad", "下载开始：" + info.url);
            }

            @Override
            public void onDownloading(DownloadInfo info) {
                Log.e("DownLoad", "正在下载");
            }

            @Override
            public void onStopped(DownloadInfo info) {
                Log.e("DownLoad", "下载停止");
            }

            @Override
            public void onCanceled(DownloadInfo info) {
                Log.e("DownLoad", "下载取消");
            }

            @Override
            public void onCompletion(DownloadInfo info) {
                Log.e("DownLoad", "下载完成");
                //消息数量+1
                chatBean.setPath(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/location/" + chatBean.getTemp() + ".png");
                //消息数量+1
                AddMessageCountEvent addMessageCountEvent = new AddMessageCountEvent();
                EventBus.getDefault().post(addMessageCountEvent);
                chatBean.setpUserInfo(chatBean.getUserinfo());
                saveMessageListToDataBase(chatBean);
                chatBean.setUserIconUrl(chatBean.getpUserInfo().getAvatar());
                receiveMessageToChatActivity(chatBean);
                sendNotification(chatBean);
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                Log.e("DownLoad", "下载错误：" + e.toString());
            }
        });
        return rxDownload;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {//扫描二维码
            if (data != null) {
                String content = data.getStringExtra(com.yzq.zxinglibrary.common.Constant.CODED_CONTENT);
                String qr = getValueByName(content, "qr");
                String type = getValueByName(content, "type");
                if ("1".equals(type)) {//添加好友
                    Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
                    intent.putExtra("data", Integer.valueOf(new String(Base64.decode(qr, Base64.DEFAULT))));
                    startActivity(intent);
                } else if ("2".equals(type)) {//添加公司
                    Intent intent = new Intent(getActivity(), ApplyJoinTeamActivity.class);
                    intent.putExtra("data", Integer.valueOf(new String(Base64.decode(qr, Base64.DEFAULT))));
                    startActivity(intent);
                }
                ToastUtils.showToast(getActivity(), content);
            }
        }
    }

    private String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

    /**
     * 判断程序是否在后台
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    private Bitmap bitmap;

    public Bitmap sendNotification(final ChatBean chatBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(chatBean.getUserIconUrl());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    if (isBackground(Utils.getAppContext())) {//处于后台
                        if (SPUtils.getInstance().get(Constant.IS_SEND_NOTIFICATION + chatBean.getType() + chatBean.getPid(), 0) == 0) {//0为打扰
                            NotificationUtil.sendMessage(getActivity(), bitmap, chatBean);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MyHanlder.getInstance().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StatusBarUtil.setColor(getActivity(), getActivity().getColor(R.color.white), 0);
//                    refreshChatList();
                }
            }, 1);
        }
    }

//    /**
//     * 刷新用户信息
//     */
//    private void refreshChatList() {
//        for (ChatBean chatBean : chatList) {
//            Map<String, String> map = new HashMap<>();
//            map.put("uid", String.valueOf(chatBean.getPid()));
////            presenter.getUserInfoById(map);
//        }
//    }

    private void updateChatToDataBase(ChatBean chatBean) {
        ContentValues values = new ContentValues();
        values.put("p_name", chatBean.getName());
        values.put("p_icon", chatBean.getUserIconUrl());
        DataBaseHelper.updateChatList(getActivity(), uId, chatBean.getPid(), values);
    }

    private void saveMessageListToDataBase(ChatBean chatBean) {
        ContentValues values = new ContentValues();
        switch (chatBean.getFileype()) {
            case Constant.CHAT_VOICE://语音
                values.put("last_message", "[语音]");
                break;
            case Constant.CHAT_PIC://图片或视频
                values.put("last_message", "[图片]");
                break;
            case Constant.CHAT_VIDEO:
                values.put("last_message", "[视频]");
                break;
            case Constant.CHAT_FILE://文件
                values.put("last_message", "[文件]");
                break;
            case Constant.CHAT_LOCATION://位置
                values.put("last_message", "[位置]");
                break;
            case Constant.CHAT_CARD://名片
                values.put("last_message", "[名片]");
                break;
            default:
                values.put("last_message", chatBean.getStxt());
                break;
        }
        ChatUserInfoBean chatUserInfoBean = null;
        if (chatBean.getType() == Constant.CHAT_ONE_TYPE) {
            chatUserInfoBean = chatBean.getpUserInfo();
            chatBean.setUserIconUrl(chatUserInfoBean.getAvatar());
        } else if (chatBean.getType() == Constant.CHAT_GROUP_TYPE) {
            chatUserInfoBean = chatBean.getGroupinfo();
            chatBean.setGroupIcon(chatUserInfoBean.getAvatar());
            chatBean.setUserIconUrl(chatBean.getGroupIcon());
        }
        chatBean.setName(chatUserInfoBean.getNickName());
        values.put("last_time", chatBean.getTime());
        values.put("p_name", chatUserInfoBean.getNickName());
        values.put("p_icon", chatUserInfoBean.getAvatar());
        for (int i = 0; i < chatList.size(); i++) {//判断是否存在消息列表里
            if (chatList.get(i).getFileype() < 100) { //12前都是消息通知
                if (chatList.get(i).getType() == chatBean.getType()) {//由于群id会和人id重复 所以需要判断类型一致
                    if (chatList.get(i).getPid() == chatBean.getPid()) {
                        switch (chatBean.getFileype()) {
                            case Constant.CHAT_VOICE://语音
                                chatList.get(i).setStxt("[语音]");
                                break;
                            case Constant.CHAT_PIC://图片
                                chatList.get(i).setStxt("[图片]");
                                break;
                            case Constant.CHAT_VIDEO://视频
                                chatList.get(i).setStxt("[视频]");
                                break;
                            case Constant.CHAT_FILE://文件
                                chatList.get(i).setStxt("[文件]");
                                break;
                            case Constant.CHAT_LOCATION://位置
                                chatList.get(i).setStxt("[位置]");
                                break;
                            case Constant.CHAT_CARD://名片
                                chatList.get(i).setStxt("[名片]");
                                break;
                            default:
                                chatList.get(i).setStxt(chatBean.getStxt());
                                break;
                        }
                        chatList.get(i).setTime(chatBean.getTime());
                        chatList.get(i).setGroupType(chatUserInfoBean.getGroupType());
                        if (chatBean.getFlag() != 1) {
                            chatList.get(i).setMessageCount(chatList.get(i).getMessageCount() + 1);
                        }
                        values.put("message_count", chatList.get(i).getMessageCount());
                        AddMessageCountEvent addMessageCountEvent = new AddMessageCountEvent();
                        List<ChatBean> chatBeanList = new ArrayList<>();//
                        Iterator<ChatBean> iterator = chatList.iterator();
                        while (iterator.hasNext()) {//取出置顶得人 让置顶得人不按时间排序
                            ChatBean chatBean1 = iterator.next();
                            addMessageCountEvent.setMessageCount(addMessageCountEvent.getMessageCount()+chatBean1.getMessageCount());
                            if (chatBean1.isTop()) {
                                chatBeanList.add(chatBean1);
                                iterator.remove();
                            }
                        }
                        if (DataBaseHelper.updateChatListById(getActivity(), uId, chatList.get(i).getChatListId(), values) > 0 && chatBean.getFlag()==0) {//判断是否更改消息列表成功
                            EventBus.getDefault().post(addMessageCountEvent);
                        }
                        ListUtil.sortList(chatList);//按时间排序
                        for (ChatBean chatBean1 : chatBeanList) {
                            chatList.add(0, chatBean1);
                        }
                        messageAdapter.notifyDataSetChanged();
                        sendNotification(chatBean);
                        return;
                    }
                }
            } else {//100后都是工作通知

            }
        }
        values.put("message_count", 1);
        chatBean.setMessageCount(1);
        values.put("pid", chatBean.getPid());//后台在转发消息时将pid和mid反过来了
        if (chatBean.isTop()) {
            values.put("is_top", 1);
        } else {
            values.put("is_top", 0);
        }
        values.put("type", chatBean.getType());
        values.put("group_type", chatUserInfoBean.getGroupType());
        values.put("file_type", chatBean.getFileype());
        chatBean.setGroupType(chatUserInfoBean.getGroupType());
        if (chatList.size() > 0) {
            for (int i = 0; i < chatList.size(); i++) {
                if (!chatList.get(i).isTop()) {
                    chatList.add(i, chatBean);
                    messageAdapter.notifyItemInserted(i);
                    break;
                }
            }
        } else {
            chatList.add(0, chatBean);
            messageAdapter.notifyItemInserted(0);
        }
        chatBean.setChatListId(DataBaseHelper.insertChatList(getActivity(), uId, values));
        if (chatBean.getChatListId() > 0 && chatBean.getFlag() == 0) {
            //消息数量+1
            AddMessageCountEvent addMessageCountEvent = new AddMessageCountEvent(1);
            EventBus.getDefault().post(addMessageCountEvent);
        }
        sendNotification(chatBean);
    }
}
