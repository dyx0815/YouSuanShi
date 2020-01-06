package com.dan.yousuanshi.module.main.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.event.AddMessageCountEvent;
import com.dan.yousuanshi.http.PublicHeadersInterceptor;
import com.dan.yousuanshi.module.addressbook.fragment.AddressBookFragment;
import com.dan.yousuanshi.module.chat.fragment.ChatFragment;
import com.dan.yousuanshi.module.chat.service.ChatSocketService;
import com.dan.yousuanshi.module.chat.utils.ChatSocketClient;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.main.adapter.MyPagerAdapter;
import com.dan.yousuanshi.module.main.presenter.MainPresenter;
import com.dan.yousuanshi.module.main.view.MainView;
import com.dan.yousuanshi.module.mine.fragment.MineFragment;
import com.dan.yousuanshi.module.shared.fragment.SharedFragment;
import com.dan.yousuanshi.utils.CustomScrollViewPager;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.leolin.shortcutbadger.ShortcutBadger;

import static android.app.Notification.VISIBILITY_SECRET;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    @BindView(R.id.viewPager)
    CustomScrollViewPager viewPager;
    @BindView(R.id.bottom_tab)
    AlphaTabsIndicator bottomTab;
    @BindView(R.id.atv_chat)
    AlphaTabView atvChat;

    private List<Fragment> fragmentList;
    private ChatSocketClient chatSocketClient;
    private ChatSocketService.ChatSocketServiceBinder binder;
    private Dialog dialog;
    private MyPagerAdapter adapter;
    private ChatSocketService chatSocketService;
    private int messageCount = 0;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (ChatSocketService.ChatSocketServiceBinder) service;
            chatSocketService = binder.getService();
            chatSocketClient = chatSocketService.chatSocketClient;
            thread.start();
            presenter.socketSuccess();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("Socket", "服务断开");
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Nullable
    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        initFragment();
        initBottomBar();
        EventBus.getDefault().register(this);
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        boolean isOpened = manager.areNotificationsEnabled();
        Log.e("WelcomeActivity","通知权限是否开启："+isOpened);
        if(!isOpened) {
            Log.e("WelcomeActivity", "打开权限！！！");
            showDialog();
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                String channelId = "chat";
                String channelName = "聊天消息";
                int importance = NotificationManager.IMPORTANCE_MAX;
                createNotificationChannel(channelId, channelName, importance);
            }
        }
    }

    @Override
    protected void loadData() {
        PublicHeadersInterceptor.updateToken(SPUtils.getInstance().get("KEY_WORD_TOKEN", ""));
        presenter.getNewToken();
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ChatFragment());
        fragmentList.add(new SharedFragment());
        fragmentList.add(new AddressBookFragment());
        fragmentList.add(new MineFragment());
    }

    private void initBottomBar() {
        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        bottomTab.setViewPager(viewPager);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
        super.onBackPressed();
    }

    private void bindService() {
        Intent intent = new Intent(this, ChatSocketService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        thread.interrupt();
        thread = null;
        EventBus.getDefault().unregister(this);
    }

    Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (1 == 1) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!chatSocketClient.isClosed()) {
                    chatSocketClient.send("{\"type\":1}");
                    Log.e("Socket","发送心跳!!!!");
                }
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addMessageCount(AddMessageCountEvent addMessageCountEvent) {
        messageCount = addMessageCountEvent.getMessageCount();
        showNumber();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void removeChatCount(Integer position) {
        messageCount -= position;
        showNumber();
    }

    @Override
    public void getNewTokenSuccess(int code, LoginBean loginBean) {
        PublicHeadersInterceptor.updateToken(loginBean.getToken());
        SPUtils.getInstance().save("KEY_WORD_TOKEN", loginBean.getToken());
        bindService();
    }

    @Override
    public void getNewTokenFailed(int code, String message) {
        Log.e("MainActivity", "code:" + code + "\t" + message);
        ToastUtils.showToast(this, "获取新Token失败！  code:" + code + "\tmessage" + message);
    }

    @Override
    public void socketSuccess(int code, List list) {
        Log.e("Socket","群聊加载成功！！");
    }

    @Override
    public void socketFaied(int code, String msg) {
        ToastUtils.showToast(this,"群聊加载失败："+code+msg);
        Log.e("Socket","群聊加载失败！！");
    }

    private void showNumber() {
        atvChat.showNumber(messageCount);
        ShortcutBadger.applyCount(this, messageCount);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        //桌面launcher的消息角标
        channel.canShowBadge();
        //锁屏显示通知
        channel.setLockscreenVisibility(VISIBILITY_SECRET);
        //是否允许震动
        channel.enableVibration(true);
        //设置震动模式
        channel.setVibrationPattern(new long[]{100, 100, 200});
        notificationManager.createNotificationChannel(channel);
    }

    private void showDialog(){
        if(dialog == null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_start_notification);
            ImageView ivStart = dialog.findViewById(R.id.iv_start);
            ImageView ivStop = dialog.findViewById(R.id.iv_stop);
            ivStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            ivStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
        }
        dialog.show();
    }
}
