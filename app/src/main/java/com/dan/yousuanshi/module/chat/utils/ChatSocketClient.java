package com.dan.yousuanshi.module.chat.utils;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class ChatSocketClient extends WebSocketClient {
    public ChatSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.e("Chat", "Socket已打开");
    }

    @Override
    public void onMessage(String message) {
        Log.e("Chat", "Socket:" + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("Chat", "Socket已关闭");
    }

    @Override
    public void onError(Exception ex) {
        Log.e("Chat", "Socket："+ex.getMessage());
    }
}
