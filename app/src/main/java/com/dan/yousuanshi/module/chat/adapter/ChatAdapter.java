package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.chat.activity.InviteJoinTeamActivity;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatEmoji;
import com.dan.yousuanshi.module.chat.bean.InviteJoinTeamBean;
import com.dan.yousuanshi.module.chat.bean.LocationAddress;
import com.dan.yousuanshi.module.chat.utils.MediaFile;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.DpToPxUtils;
import com.dan.yousuanshi.utils.FileItem;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.dan.yousuanshi.utils.popupwindow.ChatPopupWindow;
import com.dan.yousuanshi.utils.popupwindow.PopUpMenuBean;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class ChatAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ChatBean> data;
    private final int TEXT_FLAG = 0;
    private final int TEXT_FLAG_MINE = 1;
    private OnItemClick onItemClick;
    private MediaPlayer mediaPlayer;
    private Map<GifImageView, ImageView> gifMap = new HashMap<>();
    private ArrayList<PopUpMenuBean> popUpMenuBeans;
    private ChatPopupWindow.OnListItemClickLitener onListItemClickLitener;

    public ChatAdapter(Context context, List<ChatBean> data, ArrayList<PopUpMenuBean> popUpMenuBeans, ChatPopupWindow.OnListItemClickLitener onListItemClickLitener) {
        this.context = context;
        this.data = data;
        this.popUpMenuBeans = popUpMenuBeans;
        this.onListItemClickLitener = onListItemClickLitener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constant.CHAT_TEXT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_text, null);
            return new ChatTextHolder(view);
        } else if (viewType == Constant.CHAT_VOICE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_voice, null);
            return new ChatVoiceHolder(view);
        } else if (viewType == Constant.CHAT_PIC) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_pic, null);
            return new ChatPicHolder(view);
        }else if (viewType == Constant.CHAT_VIDEO) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_pic, null);
            return new ChatPicHolder(view);
        } else if (viewType == Constant.CHAT_FILE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_file, null);
            return new ChatFileHolder(view);
        } else if (viewType == Constant.CHAT_LOCATION) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_address, null);
            return new ChatLocationHolder(view);
        } else if (viewType == Constant.CHAT_CARD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_card, null);
            return new ChatCardHolder(view);
        }else if(viewType == Constant.INVITE_JOIN_TEAM){
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_invite_join_team, null);
            return new ChatInviteJoinTeamHolder(view);
        }else if(viewType == Constant.CHAT_GROUP_NOTIFICATION || viewType == Constant.CHAT_EXIT_GROUP || viewType == Constant.CHAT_ADD_GROUP){
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_notification,null);
            return new ChatNotificationHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final ChatBean chatBean = data.get(position);
        if (holder instanceof ChatTextHolder) {
            if (chatBean.getFlag() == TEXT_FLAG_MINE) {
                if(Constant.SEND_ERROR_MSG_ID.equals(chatBean.getMsgid())){
                    ((ChatTextHolder) holder).ivSendError.setVisibility(View.VISIBLE);
                }else{
                    ((ChatTextHolder) holder).ivSendError.setVisibility(View.GONE);
                }
                ((ChatTextHolder) holder).tvChatTextMine.setText(ChatEmoji.getEmotionContent(context, chatBean.getStxt()));
                ((ChatTextHolder) holder).tvChatTextMine.setVisibility(View.VISIBLE);
                ((ChatTextHolder) holder).tvReadFlag.setVisibility(View.VISIBLE);
                Glide.with(context).load(UserUtils.getInstance().getUserBean().getUser_portrait()).into(((ChatTextHolder) holder).rivHeadIconMine);
                ((ChatTextHolder) holder).rivHeadIconMine.setVisibility(View.VISIBLE);
                ((ChatTextHolder) holder).ivChatTextMine.setVisibility(View.VISIBLE);
                ((ChatTextHolder) holder).ivTextChat.setVisibility(View.GONE);
                ((ChatTextHolder) holder).tvChatText.setVisibility(View.GONE);
                ((ChatTextHolder) holder).rivHeadIcon.setVisibility(View.GONE);
                ((ChatTextHolder) holder).tvChatTextMine.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showPopupWindow(v,position,chatBean,Constant.CHAT_TEXT,((ChatTextHolder) holder).tvChatTextMine);
                        return false;
                    }
                });
            } else {
                ((ChatTextHolder) holder).tvChatText.setText(ChatEmoji.getEmotionContent(context, chatBean.getStxt()));
                ((ChatTextHolder) holder).tvChatTextMine.setVisibility(View.GONE);
                ((ChatTextHolder) holder).tvReadFlag.setVisibility(View.GONE);
                ((ChatTextHolder) holder).rivHeadIconMine.setVisibility(View.GONE);
                ((ChatTextHolder) holder).ivChatTextMine.setVisibility(View.GONE);
                ((ChatTextHolder) holder).ivTextChat.setVisibility(View.VISIBLE);
                ((ChatTextHolder) holder).tvChatText.setVisibility(View.VISIBLE);
                Glide.with(context).load(chatBean.getUserIconUrl()).into(((ChatTextHolder) holder).rivHeadIcon);
                ((ChatTextHolder) holder).rivHeadIcon.setVisibility(View.VISIBLE);
            }
            if (chatBean.isTime()) {
                ((ChatTextHolder) holder).tvDateTime.setText(DateUtil.showTime2(DateUtil.StringToDate(chatBean.getTime()), new Date()));
                ((ChatTextHolder) holder).tvDateTime.setVisibility(View.VISIBLE);
            } else {
                ((ChatTextHolder) holder).tvDateTime.setVisibility(View.GONE);
            }
            ((ChatTextHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.OnItemClick();
                }
            });
            ((ChatTextHolder) holder).tvChatText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.CHAT_TEXT,((ChatTextHolder) holder).tvChatText);
                    return false;
                }
            });
            ((ChatTextHolder) holder).tvChatTextMine.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    menu.clear();
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    menu.clear();
                    return true;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
            ((ChatTextHolder) holder).tvChatText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    menu.clear();
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    menu.clear();
                    return true;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }

            });
        } else if (holder instanceof ChatVoiceHolder) {
            if (chatBean.getFlag() == TEXT_FLAG_MINE) {
                if(Constant.SEND_ERROR_MSG_ID.equals(chatBean.getMsgid())){
                    ((ChatVoiceHolder) holder).ivSendError.setVisibility(View.VISIBLE);
                }else{
                    ((ChatVoiceHolder) holder).ivSendError.setVisibility(View.GONE);
                }
                ((ChatVoiceHolder) holder).tvVoiceLongMine.setText(chatBean.getStxt());
                ((ChatVoiceHolder) holder).tvVoiceLongMine.setVisibility(View.VISIBLE);
                ((ChatVoiceHolder) holder).ivChatVoiceMineIcon.setVisibility(View.VISIBLE);
                chatBean.setSize(Long.valueOf(chatBean.getStxt()));
                ViewGroup.LayoutParams layoutParams = ((ChatVoiceHolder) holder).llChatVoiceMine.getLayoutParams();
                int size = DpToPxUtils.dip2px(context, 65) + DpToPxUtils.dip2px(context, 3) * (int) chatBean.getSize();
                if (size > DpToPxUtils.dip2px(context, 140)) {
                    size = DpToPxUtils.dip2px(context, 140);
                }
                layoutParams.width = size;
                ((ChatVoiceHolder) holder).llChatVoiceMine.setLayoutParams(layoutParams);
                ((ChatVoiceHolder) holder).llChatVoiceMine.setVisibility(View.VISIBLE);
                ((ChatVoiceHolder) holder).tvReadFlag.setVisibility(View.VISIBLE);
                Glide.with(context).load(UserUtils.getInstance().getUserBean().getUser_portrait()).into(((ChatVoiceHolder) holder).rivHeadIconMine);
                ((ChatVoiceHolder) holder).rivHeadIconMine.setVisibility(View.VISIBLE);
                ((ChatVoiceHolder) holder).ivChatMine.setVisibility(View.VISIBLE);
                ((ChatVoiceHolder) holder).ivChat.setVisibility(View.GONE);
                ((ChatVoiceHolder) holder).llChatVoice.setVisibility(View.GONE);
                ((ChatVoiceHolder) holder).rivHeadIcon.setVisibility(View.GONE);
                ((ChatVoiceHolder) holder).tvVoiceLong.setVisibility(View.GONE);
                ((ChatVoiceHolder) holder).ivChatVoiceIcon.setVisibility(View.GONE);
            } else {
                if (chatBean.getSize() >= 1) {
                    ((ChatVoiceHolder) holder).tvVoiceLong.setText(chatBean.getSize() + "");
                } else {
                    ((ChatVoiceHolder) holder).tvVoiceLong.setText(chatBean.getStxt());
                    chatBean.setSize(Long.valueOf(chatBean.getStxt()));
                }
                ViewGroup.LayoutParams layoutParams = ((ChatVoiceHolder) holder).llChatVoice.getLayoutParams();
                int size = DpToPxUtils.dip2px(context, 65) + DpToPxUtils.dip2px(context, 3) * (int) chatBean.getSize();
                if (size > DpToPxUtils.dip2px(context, 140)) {
                    size = DpToPxUtils.dip2px(context, 140);
                }
                layoutParams.width = size;
                ((ChatVoiceHolder) holder).tvVoiceLongMine.setVisibility(View.GONE);
                ((ChatVoiceHolder) holder).llChatVoiceMine.setVisibility(View.GONE);
                ((ChatVoiceHolder) holder).tvReadFlag.setVisibility(View.GONE);
                ((ChatVoiceHolder) holder).ivChatVoiceMineIcon.setVisibility(View.GONE);
                Glide.with(context).load(chatBean.getUserIconUrl()).into(((ChatVoiceHolder) holder).rivHeadIcon);
                ((ChatVoiceHolder) holder).rivHeadIconMine.setVisibility(View.GONE);
                ((ChatVoiceHolder) holder).ivChatMine.setVisibility(View.GONE);
                ((ChatVoiceHolder) holder).ivChat.setVisibility(View.VISIBLE);
                ((ChatVoiceHolder) holder).llChatVoice.setVisibility(View.VISIBLE);
                ((ChatVoiceHolder) holder).rivHeadIcon.setVisibility(View.VISIBLE);
                ((ChatVoiceHolder) holder).tvVoiceLong.setVisibility(View.VISIBLE);
                ((ChatVoiceHolder) holder).ivChatVoiceIcon.setVisibility(View.VISIBLE);
            }
            if (chatBean.isTime()) {
                ((ChatVoiceHolder) holder).tvDateTime.setText(DateUtil.showTime2(DateUtil.StringToDate(chatBean.getTime()), new Date()));
                ((ChatVoiceHolder) holder).tvDateTime.setVisibility(View.VISIBLE);
            } else {
                ((ChatVoiceHolder) holder).tvDateTime.setVisibility(View.GONE);
            }
            //收到的语音消息
            ((ChatVoiceHolder) holder).llChatVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVoice(((ChatVoiceHolder) holder).gifChatVoice, ((ChatVoiceHolder) holder).ivChatVoiceIcon, chatBean.getPath());
                    gifMap.put(((ChatVoiceHolder) holder).gifChatVoice, ((ChatVoiceHolder) holder).ivChatVoiceIcon);
                }
            });
            //发送的语音消息
            ((ChatVoiceHolder) holder).llChatVoiceMine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVoice(((ChatVoiceHolder) holder).gifChatVoiceMine, ((ChatVoiceHolder) holder).ivChatVoiceMineIcon, chatBean.getPath());
                    gifMap.put(((ChatVoiceHolder) holder).gifChatVoiceMine, ((ChatVoiceHolder) holder).ivChatVoiceMineIcon);
                }
            });
            ((ChatVoiceHolder) holder).llChatVoice.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v, position, chatBean, Constant.CHAT_VOICE,null);
                    return false;
                }
            });
            ((ChatVoiceHolder) holder).llChatVoiceMine.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v, position, chatBean, Constant.CHAT_VOICE,null);
                    return false;
                }
            });
        } else if (holder instanceof ChatPicHolder) {
            if (StringUtils.isEmpty(chatBean.getPath())) {
                chatBean.setPath(chatBean.getStxt());
            }
            Log.e("Chat", "收到的图片：" + chatBean.getPath());
            if (chatBean.getFlag() == TEXT_FLAG_MINE) {
                if(Constant.SEND_ERROR_MSG_ID.equals(chatBean.getMsgid())){
                    ((ChatPicHolder)holder).ivSendErrorMine.setVisibility(View.VISIBLE);
                }else{
                    ((ChatPicHolder)holder).ivSendErrorMine.setVisibility(View.GONE);
                }
                Glide.with(context).load(UserUtils.getInstance().getUserBean().getUser_portrait()).into(((ChatPicHolder) holder).rivHeadIconMine);
                ((ChatPicHolder) holder).rivHeadIconMine.setVisibility(View.VISIBLE);
                ((ChatPicHolder) holder).tvReadFlag.setVisibility(View.VISIBLE);
                Glide.with(context).load(chatBean.getPath()).into(((ChatPicHolder) holder).ivChatPicMine);
                ((ChatPicHolder) holder).rlChatImgMine.setVisibility(View.VISIBLE);
                ((ChatPicHolder) holder).rivHeadIcon.setVisibility(View.GONE);
                ((ChatPicHolder) holder).rlChatImg.setVisibility(View.GONE);
                if (MediaFile.isVideoFileType(chatBean.getPath())) {//判断是否是视频
                    ((ChatPicHolder) holder).ivStartMine.setVisibility(View.VISIBLE);
                    Integer m = Integer.valueOf(chatBean.getStxt());
                    ((ChatPicHolder) holder).tvVideoTimeMine.setText(m<10?"0:0"+m:"0:"+m);
                    ((ChatPicHolder) holder).tvVideoTimeMine.setVisibility(View.VISIBLE);
                } else {
                    ((ChatPicHolder) holder).ivStartMine.setVisibility(View.GONE);
                    ((ChatPicHolder) holder).tvVideoTimeMine.setVisibility(View.GONE);
                }
            } else {
                ((ChatPicHolder) holder).rivHeadIconMine.setVisibility(View.GONE);
                ((ChatPicHolder) holder).rlChatImgMine.setVisibility(View.GONE);
                ((ChatPicHolder) holder).tvReadFlag.setVisibility(View.GONE);
                Glide.with(context).load(chatBean.getUserIconUrl()).into(((ChatPicHolder) holder).rivHeadIcon);
                File file = new File(chatBean.getPath());
                Glide.with(context).load(file).into(((ChatPicHolder) holder).ivChatPic);
                ((ChatPicHolder) holder).ivChatPic.setVisibility(View.VISIBLE);
                ((ChatPicHolder) holder).rivHeadIcon.setVisibility(View.VISIBLE);
                ((ChatPicHolder) holder).rlChatImg.setVisibility(View.VISIBLE);
                if (MediaFile.isVideoFileType(chatBean.getPath())) {//判断是否是视频
                    ((ChatPicHolder) holder).ivStart.setVisibility(View.VISIBLE);
                    Integer m = Integer.valueOf(chatBean.getStxt());
                    ((ChatPicHolder) holder).tvVideoTime.setText(m<10?"0:0"+m:"0:"+m);
                    ((ChatPicHolder) holder).tvVideoTime.setVisibility(View.VISIBLE);
                } else {
                    ((ChatPicHolder) holder).ivStart.setVisibility(View.GONE);
                    ((ChatPicHolder) holder).tvVideoTime.setVisibility(View.GONE);
                }
            }
            if (chatBean.isTime()) {
                ((ChatPicHolder) holder).tvDateTime.setText(DateUtil.showTime2(DateUtil.StringToDate(chatBean.getTime()), new Date()));
                ((ChatPicHolder) holder).tvDateTime.setVisibility(View.VISIBLE);
            } else {
                ((ChatPicHolder) holder).tvDateTime.setVisibility(View.GONE);
            }
            ((ChatPicHolder) holder).ivChatPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chatPicClick.onPicClick(chatBean.getPath());
                }
            });
            ((ChatPicHolder) holder).ivChatPicMine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chatPicClick.onPicClick(chatBean.getPath());
                }
            });
            ((ChatPicHolder) holder).ivChatPic.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.CHAT_PIC,null);
                    return false;
                }
            });
            ((ChatPicHolder) holder).ivChatPicMine.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.CHAT_PIC,null);
                    return false;
                }
            });
        } else if (holder instanceof ChatFileHolder) {
            FileItem fileItem;
            if (chatBean.getFlag() == TEXT_FLAG_MINE) {
                if(Constant.SEND_ERROR_MSG_ID.equals(chatBean.getMsgid())){
                    ((ChatFileHolder)holder).ivSendError.setVisibility(View.VISIBLE);
                }else{
                    ((ChatFileHolder)holder).ivSendError.setVisibility(View.GONE);
                }
                ((ChatFileHolder) holder).rivHeadIconMine.setVisibility(View.VISIBLE);
                ((ChatFileHolder) holder).ivChatTextMine.setVisibility(View.VISIBLE);
                ((ChatFileHolder) holder).rlChatFileMine.setVisibility(View.VISIBLE);
                ((ChatFileHolder) holder).tvReadFlag.setVisibility(View.VISIBLE);
                Glide.with(context).load(UserUtils.getInstance().getUserBean().getUser_portrait()).into(((ChatFileHolder) holder).rivHeadIconMine);
                fileItem = new Gson().fromJson(chatBean.getStxt(), FileItem.class);
                ((ChatFileHolder) holder).ivChatFileMine.setImageResource(FileUtils.getFileIconByPath(fileItem.getFileName()));
                ((ChatFileHolder) holder).tvChatFileNameMine.setText(fileItem.getFileName());
                ((ChatFileHolder) holder).tvChatFileSizeMine.setText(FileUtils.formatFileSize(fileItem.getSize()));
                ((ChatFileHolder) holder).rivHeadIcon.setVisibility(View.GONE);
                ((ChatFileHolder) holder).rlChatFile.setVisibility(View.GONE);
                ((ChatFileHolder) holder).ivTextChat.setVisibility(View.GONE);
            } else {
                ((ChatFileHolder) holder).rivHeadIconMine.setVisibility(View.GONE);
                ((ChatFileHolder) holder).rlChatFileMine.setVisibility(View.GONE);
                ((ChatFileHolder) holder).tvReadFlag.setVisibility(View.GONE);
                ((ChatFileHolder) holder).ivChatTextMine.setVisibility(View.GONE);
                Glide.with(context).load(chatBean.getUserIconUrl()).into(((ChatFileHolder) holder).rivHeadIcon);
                fileItem = new Gson().fromJson(chatBean.getStxt(), FileItem.class);
                ((ChatFileHolder) holder).ivChatFile.setImageResource(FileUtils.getFileIconByPath(fileItem.getFileName()));
                ((ChatFileHolder) holder).tvChatFileName.setText(fileItem.getFileName());
                ((ChatFileHolder) holder).tvChatFileSize.setText(FileUtils.formatFileSize(fileItem.getSize()));
                ((ChatFileHolder) holder).rivHeadIcon.setVisibility(View.VISIBLE);
                ((ChatFileHolder) holder).rlChatFile.setVisibility(View.VISIBLE);
                ((ChatFileHolder) holder).ivTextChat.setVisibility(View.VISIBLE);
            }
            if (chatBean.isTime()) {//时间是否显示
                ((ChatFileHolder) holder).tvDateTime.setText(DateUtil.showTime2(DateUtil.StringToDate(chatBean.getTime()), new Date()));
                ((ChatFileHolder) holder).tvDateTime.setVisibility(View.VISIBLE);
            } else {
                ((ChatFileHolder) holder).tvDateTime.setVisibility(View.GONE);
            }
            ((ChatFileHolder) holder).rlChatFileMine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onFileClick(fileItem, chatBean.getPath());
                }
            });
            ((ChatFileHolder) holder).rlChatFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onFileClick(fileItem, chatBean.getPath());
                }
            });
            ((ChatFileHolder) holder).rlChatFile.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.CHAT_FILE,null);
                    return false;
                }
            });
            ((ChatFileHolder) holder).rlChatFileMine.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.CHAT_FILE,null);
                    return false;
                }
            });
        } else if (holder instanceof ChatLocationHolder) {
            LocationAddress locationAddress = new Gson().fromJson(chatBean.getStxt(), LocationAddress.class);
            if (chatBean.getFlag() == TEXT_FLAG_MINE) {//我发的消息
                if(Constant.SEND_ERROR_MSG_ID.equals(chatBean.getMsgid())){
                    ((ChatLocationHolder)holder).ivSendError.setVisibility(View.VISIBLE);
                }else{
                    ((ChatLocationHolder)holder).ivSendError.setVisibility(View.GONE);
                }
                Glide.with(context).load(UserUtils.getInstance().getUserBean().getUser_portrait()).into(((ChatLocationHolder) holder).rivHeadIconMine);
                ((ChatLocationHolder) holder).tvLocationMine.setText(locationAddress.getLocationName());
                ((ChatLocationHolder) holder).tvAddressMine.setText(locationAddress.getLocationAddess());
                Glide.with(context).load(chatBean.getPath()).into(((ChatLocationHolder) holder).ivLocationMine);
                ((ChatLocationHolder) holder).rivHeadIcon.setVisibility(View.GONE);
                ((ChatLocationHolder) holder).rivHeadIconMine.setVisibility(View.VISIBLE);
                ((ChatLocationHolder) holder).ivTextChat.setVisibility(View.GONE);
                ((ChatLocationHolder) holder).rlChatLocation.setVisibility(View.GONE);
                ((ChatLocationHolder) holder).rlChatLocationMine.setVisibility(View.VISIBLE);
                ((ChatLocationHolder) holder).tvReadFlag.setVisibility(View.VISIBLE);
                ((ChatLocationHolder) holder).rlChatLocationMine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.onLocationClick(locationAddress);
                    }
                });
            } else {//接收到的消息
                Glide.with(context).load(chatBean.getUserIconUrl()).into(((ChatLocationHolder) holder).rivHeadIcon);
                ((ChatLocationHolder) holder).tvLocation.setText(locationAddress.getLocationName());
                ((ChatLocationHolder) holder).tvAddress.setText(locationAddress.getLocationAddess());
                Glide.with(context).load(chatBean.getPath()).into(((ChatLocationHolder) holder).ivLocation);
                ((ChatLocationHolder) holder).rivHeadIcon.setVisibility(View.VISIBLE);
                ((ChatLocationHolder) holder).rivHeadIconMine.setVisibility(View.GONE);
                ((ChatLocationHolder) holder).ivTextChat.setVisibility(View.VISIBLE);
                ((ChatLocationHolder) holder).rlChatLocation.setVisibility(View.VISIBLE);
                ((ChatLocationHolder) holder).rlChatLocationMine.setVisibility(View.GONE);
                ((ChatLocationHolder) holder).tvReadFlag.setVisibility(View.GONE);
                ((ChatLocationHolder) holder).rlChatLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.onLocationClick(locationAddress);
                    }
                });
            }
            if (chatBean.isTime()) {
                ((ChatLocationHolder) holder).tvDateTime.setText(DateUtil.showTime2(DateUtil.StringToDate(chatBean.getTime()), new Date()));
                ((ChatLocationHolder) holder).tvDateTime.setVisibility(View.VISIBLE);
            } else {
                ((ChatLocationHolder) holder).tvDateTime.setVisibility(View.GONE);
            }
            ((ChatLocationHolder) holder).rlChatLocation.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.CHAT_LOCATION,null);
                    return false;
                }
            });
            ((ChatLocationHolder) holder).rlChatLocationMine.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.CHAT_LOCATION,null);
                    return false;
                }
            });
        } else if (holder instanceof ChatCardHolder) {
            MyFriendBean myFriendBean = new Gson().fromJson(chatBean.getStxt(), MyFriendBean.class);
            if (chatBean.getFlag() == TEXT_FLAG_MINE) {//我发的消息
                if(Constant.SEND_ERROR_MSG_ID.equals(chatBean.getMsgid())){
                    ((ChatCardHolder)holder).ivSendError.setVisibility(View.VISIBLE);
                }else{
                    ((ChatCardHolder)holder).ivSendError.setVisibility(View.GONE);
                }
                Glide.with(context).load(UserUtils.getInstance().getUserBean().getUser_portrait()).into(((ChatCardHolder) holder).rivHeadIconMine);
                Glide.with(context).load(myFriendBean.getUser_portrait()).into(((ChatCardHolder) holder).rivHeadIconCardMine);
                ((ChatCardHolder) holder).tvNameMine.setText(myFriendBean.getNick_name());
                ((ChatCardHolder) holder).rivHeadIcon.setVisibility(View.GONE);
                ((ChatCardHolder) holder).rivHeadIconMine.setVisibility(View.VISIBLE);
                ((ChatCardHolder) holder).ivTextChat.setVisibility(View.GONE);
//                ((ChatCardHolder) holder).ivTextChatMine.setVisibility(View.VISIBLE);
                ((ChatCardHolder) holder).rlChatCard.setVisibility(View.GONE);
                ((ChatCardHolder) holder).rlChatCardMine.setVisibility(View.VISIBLE);
                ((ChatCardHolder) holder).tvReadFlag.setVisibility(View.VISIBLE);
                ((ChatCardHolder) holder).rlChatCardMine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.onCardClick(myFriendBean);
                    }
                });
            } else {//接收到的消息
                Glide.with(context).load(chatBean.getUserIconUrl()).into(((ChatCardHolder) holder).rivHeadIcon);
                ((ChatCardHolder) holder).tvName.setText(myFriendBean.getNick_name());
                Glide.with(context).load(myFriendBean.getUser_portrait()).into(((ChatCardHolder) holder).rivHeadIconCard);
                ((ChatCardHolder) holder).rivHeadIcon.setVisibility(View.VISIBLE);
                ((ChatCardHolder) holder).rivHeadIconMine.setVisibility(View.GONE);
                ((ChatCardHolder) holder).ivTextChat.setVisibility(View.VISIBLE);
                ((ChatCardHolder) holder).ivTextChatMine.setVisibility(View.GONE);
                ((ChatCardHolder) holder).rlChatCard.setVisibility(View.VISIBLE);
                ((ChatCardHolder) holder).rlChatCardMine.setVisibility(View.GONE);
                ((ChatCardHolder) holder).tvReadFlag.setVisibility(View.GONE);
                ((ChatCardHolder) holder).rlChatCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.onCardClick(myFriendBean);
                    }
                });
            }
            if (chatBean.isTime()) {
                ((ChatCardHolder) holder).tvDateTime.setText(DateUtil.showTime2(DateUtil.StringToDate(chatBean.getTime()), new Date()));
                ((ChatCardHolder) holder).tvDateTime.setVisibility(View.VISIBLE);
            } else {
                ((ChatCardHolder) holder).tvDateTime.setVisibility(View.GONE);
            }
            ((ChatCardHolder) holder).rlChatCard.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.CHAT_CARD,null);
                    return false;
                }
            });
            ((ChatCardHolder) holder).rlChatCardMine.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.CHAT_CARD,null);
                    return false;
                }
            });
        }else if (holder instanceof ChatInviteJoinTeamHolder) {//邀请加入公司消息结构
            InviteJoinTeamBean inviteJoinTeamBean = new Gson().fromJson(chatBean.getStxt(), InviteJoinTeamBean.class);
            if (chatBean.getFlag() == TEXT_FLAG_MINE) {//我发的消息
                if(Constant.SEND_ERROR_MSG_ID.equals(chatBean.getMsgid())){
                    ((ChatInviteJoinTeamHolder)holder).ivSendError.setVisibility(View.VISIBLE);
                }else{
                    ((ChatInviteJoinTeamHolder)holder).ivSendError.setVisibility(View.GONE);
                }
                Glide.with(context).load(UserUtils.getInstance().getUserBean().getUser_portrait()).into(((ChatInviteJoinTeamHolder) holder).rivHeadIconMine);
                ((ChatInviteJoinTeamHolder) holder).rivHeadIcon.setVisibility(View.GONE);
                ((ChatInviteJoinTeamHolder) holder).rivHeadIconMine.setVisibility(View.VISIBLE);
                ((ChatInviteJoinTeamHolder) holder).llInvite.setVisibility(View.GONE);
                ((ChatInviteJoinTeamHolder) holder).llInviteMine.setVisibility(View.VISIBLE);
                Glide.with(context).load(inviteJoinTeamBean.getCompany_logo()).into(((ChatInviteJoinTeamHolder) holder).ivLogoMine);
                ((ChatInviteJoinTeamHolder) holder).tvInviteMessageMine.setText(UserUtils.getInstance().getUserBean().getNick_name()+"邀请你加入"+inviteJoinTeamBean.getC_name());
                ((ChatInviteJoinTeamHolder) holder).tvReadFlag.setVisibility(View.VISIBLE);
            } else {//接收到的消息
                Glide.with(context).load(chatBean.getUserIconUrl()).into(((ChatInviteJoinTeamHolder) holder).rivHeadIcon);
                ((ChatInviteJoinTeamHolder) holder).rivHeadIcon.setVisibility(View.VISIBLE);
                ((ChatInviteJoinTeamHolder) holder).rivHeadIconMine.setVisibility(View.GONE);
                ((ChatInviteJoinTeamHolder) holder).llInvite.setVisibility(View.VISIBLE);
                ((ChatInviteJoinTeamHolder) holder).llInviteMine.setVisibility(View.GONE);
                Glide.with(context).load(inviteJoinTeamBean.getCompany_logo()).into(((ChatInviteJoinTeamHolder) holder).ivLogo);
                ((ChatInviteJoinTeamHolder) holder).tvInviteMessage.setText(chatBean.getName()+"邀请你加入"+inviteJoinTeamBean.getC_name());
                ((ChatInviteJoinTeamHolder) holder).tvReadFlag.setVisibility(View.GONE);
            }
            if (chatBean.isTime()) {
                ((ChatInviteJoinTeamHolder) holder).tvDateTime.setText(DateUtil.showTime2(DateUtil.StringToDate(chatBean.getTime()), new Date()));
                ((ChatInviteJoinTeamHolder) holder).tvDateTime.setVisibility(View.VISIBLE);
            } else {
                ((ChatInviteJoinTeamHolder) holder).tvDateTime.setVisibility(View.GONE);
            }
            ((ChatInviteJoinTeamHolder) holder).llInvite.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.INVITE_JOIN_TEAM,null);
                    return false;
                }
            });
            ((ChatInviteJoinTeamHolder) holder).llInviteMine.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v,position,chatBean,Constant.INVITE_JOIN_TEAM,null);
                    return false;
                }
            });
            ((ChatInviteJoinTeamHolder) holder).llInvite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InviteJoinTeamActivity.class);
                    intent.putExtra("id",inviteJoinTeamBean.getId());
                    context.startActivity(intent);
                }
            });
        }else if (holder instanceof ChatNotificationHolder) {//群消息通知
            if (chatBean.isTime()) {
                ((ChatNotificationHolder) holder).tvDateTime.setText(DateUtil.showTime2(DateUtil.StringToDate(chatBean.getTime()), new Date()));
                ((ChatNotificationHolder) holder).tvDateTime.setVisibility(View.VISIBLE);
            } else {
                ((ChatNotificationHolder) holder).tvDateTime.setVisibility(View.GONE);
            }
            ((ChatNotificationHolder) holder).tvNotification.setText(chatBean.getStxt());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChatTextHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.tv_chat_text)
        TextView tvChatText;
        @BindView(R.id.tv_read_flag)
        TextView tvReadFlag;
        @BindView(R.id.riv_head_icon_mine)
        RoundedImageView rivHeadIconMine;
        @BindView(R.id.tv_chat_text_mine)
        TextView tvChatTextMine;
        @BindView(R.id.iv_chat_text_mine)
        ImageView ivChatTextMine;
        @BindView(R.id.iv_text_chat)
        ImageView ivTextChat;
        @BindView(R.id.iv_send_error)
        ImageView ivSendError;

        public ChatTextHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ChatVoiceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.iv_chat)
        ImageView ivChat;
        @BindView(R.id.ll_chat_voice)
        LinearLayout llChatVoice;
        @BindView(R.id.gif_chat_voice)
        GifImageView gifChatVoice;
        @BindView(R.id.tv_voice_long)
        TextView tvVoiceLong;
        @BindView(R.id.riv_head_icon_mine)
        RoundedImageView rivHeadIconMine;
        @BindView(R.id.iv_chat_mine)
        ImageView ivChatMine;
        @BindView(R.id.ll_chat_voice_mine)
        LinearLayout llChatVoiceMine;
        @BindView(R.id.tv_voice_long_mine)
        TextView tvVoiceLongMine;
        @BindView(R.id.gif_chat_voice_mine)
        GifImageView gifChatVoiceMine;
        @BindView(R.id.tv_read_flag)
        TextView tvReadFlag;
        @BindView(R.id.iv_chat_voice_mine_icon)
        ImageView ivChatVoiceMineIcon;
        @BindView(R.id.iv_chat_voice_icon)
        ImageView ivChatVoiceIcon;
        @BindView(R.id.iv_send_error)
        ImageView ivSendError;

        public ChatVoiceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ChatPicHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.riv_head_icon_mine)
        RoundedImageView rivHeadIconMine;
        @BindView(R.id.rl_chat_img)
        RelativeLayout rlChatImg;
        @BindView(R.id.iv_chat_pic)
        RoundedImageView ivChatPic;
        @BindView(R.id.iv_send_error)
        ImageView ivSendError;
        @BindView(R.id.rl_chat_img_mine)
        RelativeLayout rlChatImgMine;
        @BindView(R.id.iv_send_error_mine)
        ImageView ivSendErrorMine;
        @BindView(R.id.iv_chat_pic_mine)
        RoundedImageView ivChatPicMine;
        @BindView(R.id.tv_read_flag)
        TextView tvReadFlag;
        @BindView(R.id.iv_start)
        ImageView ivStart;
        @BindView(R.id.iv_start_mine)
        ImageView ivStartMine;
        @BindView(R.id.tv_video_time)
        TextView tvVideoTime;
        @BindView(R.id.tv_video_time_mine)
        TextView tvVideoTimeMine;

        public ChatPicHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ChatFileHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.rl_chat_file)
        RelativeLayout rlChatFile;
        @BindView(R.id.iv_chat_file)
        ImageView ivChatFile;
        @BindView(R.id.tv_chat_file_name)
        TextView tvChatFileName;
        @BindView(R.id.tv_chat_file_size)
        TextView tvChatFileSize;
        @BindView(R.id.riv_head_icon_mine)
        RoundedImageView rivHeadIconMine;
        @BindView(R.id.rl_chat_file_mine)
        RelativeLayout rlChatFileMine;
        @BindView(R.id.iv_chat_file_mine)
        ImageView ivChatFileMine;
        @BindView(R.id.tv_chat_file_name_mine)
        TextView tvChatFileNameMine;
        @BindView(R.id.tv_chat_file_size_mine)
        TextView tvChatFileSizeMine;
        @BindView(R.id.tv_read_flag)
        TextView tvReadFlag;
        @BindView(R.id.iv_text_chat)
        ImageView ivTextChat;
        @BindView(R.id.iv_chat_text_mine)
        ImageView ivChatTextMine;
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.iv_send_error)
        ImageView ivSendError;

        public ChatFileHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ChatLocationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.iv_text_chat)
        ImageView ivTextChat;
        @BindView(R.id.rl_chat_location)
        RelativeLayout rlChatLocation;
        @BindView(R.id.riv_head_icon_mine)
        RoundedImageView rivHeadIconMine;
        @BindView(R.id.rl_chat_location_mine)
        RelativeLayout rlChatLocationMine;
        @BindView(R.id.tv_read_flag)
        TextView tvReadFlag;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_location)
        ImageView ivLocation;
        @BindView(R.id.tv_location_mine)
        TextView tvLocationMine;
        @BindView(R.id.tv_address_mine)
        TextView tvAddressMine;
        @BindView(R.id.iv_location_mine)
        ImageView ivLocationMine;
        @BindView(R.id.iv_send_error)
        ImageView ivSendError;


        public ChatLocationHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ChatCardHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.iv_text_chat)
        ImageView ivTextChat;
        @BindView(R.id.rl_chat_card)
        RelativeLayout rlChatCard;
        @BindView(R.id.rl_chat_card_mine)
        RelativeLayout rlChatCardMine;
        @BindView(R.id.riv_head_icon_card)
        RoundedImageView rivHeadIconCard;
        @BindView(R.id.riv_head_icon_card_mine)
        RoundedImageView rivHeadIconCardMine;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_name_mine)
        TextView tvNameMine;
        @BindView(R.id.riv_head_icon_mine)
        RoundedImageView rivHeadIconMine;
        @BindView(R.id.iv_chat_text_mine)
        ImageView ivTextChatMine;
        @BindView(R.id.tv_read_flag)
        TextView tvReadFlag;
        @BindView(R.id.iv_send_error)
        ImageView ivSendError;


        public ChatCardHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ChatInviteJoinTeamHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.riv_head_icon_mine)
        RoundedImageView rivHeadIconMine;
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.ll_invite)
        LinearLayout llInvite;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_invite_message)
        TextView tvInviteMessage;
        @BindView(R.id.iv_send_error)
        ImageView ivSendError;
        @BindView(R.id.ll_invite_mine)
        LinearLayout llInviteMine;
        @BindView(R.id.tv_invite_message_mine)
        TextView tvInviteMessageMine;
        @BindView(R.id.iv_logo_mine)
        ImageView ivLogoMine;
        @BindView(R.id.tv_read_flag)
        TextView tvReadFlag;

        public ChatInviteJoinTeamHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ChatNotificationHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.tv_notification)
        TextView tvNotification;

        public ChatNotificationHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface OnItemClick {
        void OnItemClick();

        void onFileClick(FileItem fileItem, String path);

        void onLocationClick(LocationAddress locationAddress);

        void onCardClick(MyFriendBean myFriendBean);
    }

    public interface ChatPicClick {
        void onPicClick(String path);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private ChatPicClick chatPicClick;

    public void setonPicClick(ChatPicClick chatPicClick) {
        this.chatPicClick = chatPicClick;
    }

    @Override
    public int getItemViewType(int position) {//1 文本消息 2 语音消息
        return data.get(position).getFileype();
    }

    private void startVoice(final GifImageView gifImageView, final ImageView imageView, String path) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        final GifDrawable gifDrawable = (GifDrawable) gifImageView.getDrawable();
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
            mediaPlayer.start();
            for (GifImageView item : gifMap.keySet()) {
                if (!item.equals(gifImageView)) {
                    GifDrawable gifDrawable1 = (GifDrawable) gifImageView.getDrawable();
                    gifDrawable1.stop();
                    gifDrawable1.reset();
                    item.setVisibility(View.GONE);
                    gifMap.get(item).setVisibility(View.VISIBLE);
                }
            }
        } catch (IOException e) {
            Log.e("ChatAdapter", e.toString());
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("ChatAdapter", "播放准备");
                gifImageView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                gifDrawable.start();
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("ChatAdapter", "gif播放完毕");
                gifDrawable.stop();
                gifDrawable.reset();
                gifImageView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e("ChatAdapter", "语音播放异常");
                gifDrawable.stop();
                gifDrawable.reset();
                return true;
            }
        });
    }

    private void stopVoice(GifDrawable gifDrawable) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            gifDrawable.stop();
            gifDrawable.reset();
        }
    }

    private void showPopupWindow(View v,int position,ChatBean chatBean,int type,TextView textView){
        popUpMenuBeans.clear();
        PopUpMenuBean popUpMenuBean = new PopUpMenuBean(R.mipmap.icon_chehui,"撤回");
        PopUpMenuBean popUpMenuBean2 = new PopUpMenuBean(R.mipmap.icon_fanyi,"翻译");
        PopUpMenuBean popUpMenuBean3 = new PopUpMenuBean(R.mipmap.icon_shanchu,"删除");
        PopUpMenuBean popUpMenuBean4 = new PopUpMenuBean(R.mipmap.icon_zhuanfa,"转发");
        PopUpMenuBean popUpMenuBean5 = new PopUpMenuBean(R.mipmap.icon_qiangtixing,"强提醒");
        PopUpMenuBean popUpMenuBean6 = new PopUpMenuBean(R.mipmap.icon_fuzhi,"复制");
        PopUpMenuBean popUpMenuBean7 = new PopUpMenuBean(R.mipmap.icon_shoucang,"收藏");
        PopUpMenuBean popUpMenuBean8 = new PopUpMenuBean(R.mipmap.icon_zhuanhuanwenzi,"转换文字");
        if(type == Constant.CHAT_TEXT){
            popUpMenuBeans.add(popUpMenuBean);
            popUpMenuBeans.add(popUpMenuBean2);
            popUpMenuBeans.add(popUpMenuBean3);
            popUpMenuBeans.add(popUpMenuBean4);
            popUpMenuBeans.add(popUpMenuBean5);
            popUpMenuBeans.add(popUpMenuBean6);
            popUpMenuBeans.add(popUpMenuBean7);
        }else if(type == Constant.CHAT_VOICE){
            popUpMenuBeans.add(popUpMenuBean);
            popUpMenuBeans.add(popUpMenuBean3);
            popUpMenuBeans.add(popUpMenuBean5);
            popUpMenuBeans.add(popUpMenuBean8);
            popUpMenuBeans.add(popUpMenuBean7);
        }else if(type == Constant.CHAT_FILE || type == Constant.CHAT_PIC || type == Constant.CHAT_LOCATION ){
            popUpMenuBeans.add(popUpMenuBean);
            popUpMenuBeans.add(popUpMenuBean3);
            popUpMenuBeans.add(popUpMenuBean4);
            popUpMenuBeans.add(popUpMenuBean5);
            popUpMenuBeans.add(popUpMenuBean7);
        }else if(type == Constant.CHAT_CARD|| type == Constant.INVITE_JOIN_TEAM){
            popUpMenuBeans.add(popUpMenuBean);
            popUpMenuBeans.add(popUpMenuBean3);
            popUpMenuBeans.add(popUpMenuBean4);
            popUpMenuBeans.add(popUpMenuBean5);
        }
        ChatPopupWindow.showPopupWindows(context, v, popUpMenuBeans, onListItemClickLitener, position,chatBean.getFlag(),textView);
    }
}
