package com.dan.yousuanshi.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {
    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    /**
     * 提示
     *
     * @param mContext
     * @param text     String 内容
     */
    public static void showToast(Context mContext, String text) {
        if (!StringUtils.isEmpty(text)) {
            if (mToast != null)
                mToast.setText(text.trim());
            else
                mToast = Toast.makeText(mContext, text.trim(), Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    /**
     * 提示
     *
     * @param mContext
     * @param text     String 内容
     */
    public static void showCenterToast(Context mContext, String text) {
        if (!StringUtils.isEmpty(text)) {
            if (mToast != null)
                mToast.setText(text.trim());
            else
                mToast = Toast.makeText(mContext, text.trim(), Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER,0,0);
            mToast.show();
        }
    }


    public static void showToast(Context mContext, String text, int duration) {
        if (!StringUtils.isEmpty(text)) {
            mHandler.removeCallbacks(r);
            if (mToast != null)
                mToast.setText(text);
            else
                mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
            mHandler.postDelayed(r, duration);

            mToast.show();
        }
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }
}
