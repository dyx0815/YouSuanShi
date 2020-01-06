package com.dan.yousuanshi.base;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.http.PublicHeadersInterceptor;
import com.dan.yousuanshi.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import per.goweii.rxhttp.core.RxHttp;
import per.goweii.rxhttp.download.setting.DefaultDownloadSetting;
import per.goweii.rxhttp.request.setting.DefaultRequestSetting;

public class MyApplication extends Application {

    private static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initRxHttp();
        initRxDownload();
        Utils.init(this);
    }


    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void clearActivity() {
        if (activityList != null) {
            for (Activity activity : activityList) {
                activity.finish();
            }

            activityList.clear();
        }
    }

    public void initRxHttp(){
        RxHttp.init(this);
        RxHttp.initRequest(new DefaultRequestSetting() {
            @Override
            public String getBaseUrl() {
                return Constant.BASE_URL;
            }

            @Override
            public int getSuccessCode() {
                return 0;
            }

            @Override
            public long getTimeout() {
                return 5000;
            }

            @Override
            public void setOkHttpClient(OkHttpClient.Builder builder) {
                super.setOkHttpClient(builder);
                builder.addInterceptor(new PublicHeadersInterceptor());
            }
        });
    }

    public void initRxDownload(){
        RxHttp.initDownload(new DefaultDownloadSetting() {
            @Override
            public long getTimeout() {
                return 60000;
            }

            @Nullable
            @Override
            public String getSaveDirPath() {
                return getApplicationContext().getFilesDir().getAbsolutePath()+"/download";
            }
        });
    }
}
