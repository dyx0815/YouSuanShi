package com.dan.yousuanshi.utils;

import android.content.Context;
import android.text.TextUtils;

import com.dan.yousuanshi.module.login.activity.LoginActivity;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.google.gson.Gson;

public class UserUtils {
    private static final String KEY_LOGIN_BEAN = "KEY_LOGIN_BEAN";

    private UserBean muserBean = null;

    private static class Holder {
        private static final UserUtils INSTANCE = new UserUtils();
    }

    public static UserUtils getInstance() {
        return Holder.INSTANCE;
    }

    private UserUtils() {
        getUserBean();
    }

    public UserBean getUserBean() {
        if (muserBean == null) {
            String json = SPUtils.getInstance().get(KEY_LOGIN_BEAN, "");
            if (!TextUtils.isEmpty(json)) {
                try {
                    muserBean = new Gson().fromJson(json, UserBean.class);
                } catch (Exception ignore) {
                    return  null;
                }
            }
        }
        return muserBean;
    }

    public void login(UserBean userBean) {
        muserBean = userBean;
        String json = new Gson().toJson(userBean);
        SPUtils.getInstance().save(KEY_LOGIN_BEAN, json);
    }

    public void logout() {
        muserBean = null;
        SPUtils.getInstance().clear();
    }

    public void update(UserBean userBean) {
        muserBean = userBean;
        SPUtils.getInstance().save(KEY_LOGIN_BEAN, muserBean);
    }

    public boolean isLogin() {
        UserBean userBean = getUserBean();
        if (userBean == null) {
            return false;
        }
        if (userBean.getUser_id() > 0) {
            return true;
        }
        return false;
    }

    public boolean doIfLogin(Context context) {
        if (isLogin()) {
            return true;
        } else {
//            LoginActivity.start(context);
            return false;
        }
    }
}
