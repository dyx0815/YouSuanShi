package com.dan.yousuanshi.module.chat.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.chat.activity.ChatActivity;
import com.dan.yousuanshi.module.chat.bean.ChatBean;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtil {
    public static void sendMessage(Context context, Bitmap bitmap, ChatBean chatBean){
        switch (chatBean.getFileype()) {
            case Constant.CHAT_TEXT://文本
                chatBean.setStxt(chatBean.getStxt());
                break;
            case Constant.CHAT_VOICE://语音
                chatBean.setStxt("[语音]");
                break;
            case Constant.CHAT_PIC://图片或视频
                if (MediaFile.isVideoFileType(chatBean.getPath())) {//如果是视频
                    chatBean.setStxt("[视频]");
                } else {
                    chatBean.setStxt("[图片]");
                }
                break;
            case Constant.CHAT_FILE://文件
                chatBean.setStxt("[文件]");
                break;
            case Constant.CHAT_LOCATION://位置
                chatBean.setStxt("[位置]");
                break;
            case Constant.CHAT_CARD://名片
                chatBean.setStxt("[名片]");
                break;
        }
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent clickIntent = new Intent(context, ChatActivity.class);
        clickIntent.putExtra("user",chatBean);
        PendingIntent clickPI = PendingIntent.getActivity(context, 1, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel("chat");
            notification = new NotificationCompat.Builder(context, "chat")
                    .setContentTitle(chatBean.getName())
                    .setContentText(chatBean.getStxt())
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.icon_logo)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .setContentIntent(clickPI)
                    .build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle(chatBean.getName())
                    .setContentText(chatBean.getStxt())
                    .setSmallIcon(R.mipmap.icon_logo)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .setContentIntent(clickPI)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
            notification = notificationBuilder.build();
        }
        manager.cancel(1);
        manager.notify(1, notification);
    }
}
