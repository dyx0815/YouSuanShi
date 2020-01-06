package com.dan.yousuanshi.module.chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.dan.yousuanshi.http.PublicHeadersInterceptor;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatResponseBean;
import com.dan.yousuanshi.module.chat.utils.ChatSocketClient;
import com.dan.yousuanshi.utils.DateUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.net.URI;
import java.util.Date;

public class ChatSocketService extends Service {

    private URI uri;
    public ChatSocketClient chatSocketClient;
    private ChatSocketServiceBinder mBinder = new ChatSocketServiceBinder();


    @Override
    public IBinder onBind(Intent intent) {
        uri = URI.create("wss://www.zixuezhilu.com:8081?token=" + PublicHeadersInterceptor.getToken());
        chatSocketClient = new ChatSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                Log.e("Socket", "Socket收到消息："+message);
                ChatResponseBean chatResponseBean = new Gson().fromJson(message, ChatResponseBean.class);
                ChatBean chatBean = new ChatBean(chatResponseBean.getSendMessageBean(),DateUtil.dateToString(new Date()));
                EventBus.getDefault().post(chatBean);
            }
        };
        try {
            chatSocketClient.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mBinder;
    }

    public class ChatSocketServiceBinder extends Binder {
        public ChatSocketService getService() {
            return ChatSocketService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (chatSocketClient != null) {
            chatSocketClient.close();
            chatSocketClient = null;
        }
        return true;
    }
}
