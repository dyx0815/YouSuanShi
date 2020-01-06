package com.dan.yousuanshi.module.login.bean;

import androidx.annotation.NonNull;

import com.dan.yousuanshi.http.BaseRequest;
import com.dan.yousuanshi.http.YouSuanShiApi;
import com.dan.yousuanshi.http.RequestListener;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class LoginRequest extends BaseRequest {
    public static Disposable login(Map<String, String> map, @NonNull RequestListener<LoginBean> listener) {
        return request(YouSuanShiApi.api().login(map), listener);
    }

    public static Disposable getUser(RequestListener<UserBean> listener){
        return request(YouSuanShiApi.api().getUserInfo(),listener);
    }
    public static Disposable sendCode(Map<String,String> map,RequestListener<SendCodeBean> listener){
        return request(YouSuanShiApi.api().sendCode(map),listener);
    }
    public static Disposable register(Map<String,String> map,RequestListener<LoginBean> listener){
        return request(YouSuanShiApi.api().register(map),listener);
    }
    public static Disposable checkCode(Map<String,String> map,RequestListener<CheckCodeBean> listener){
        return request(YouSuanShiApi.api().checkCode(map),listener);
    }
    public static Disposable forgetPwd(Map<String,String> map,RequestListener<LoginBean> listener){
        return request(YouSuanShiApi.api().forgetPwd(map),listener);
    }

    public static Disposable chattable(RequestListener<List> listener){
        return request(YouSuanShiApi.api().chattable(),listener);
    }
    public static Disposable getNewToken(RequestListener<LoginBean> listener){
        return request(YouSuanShiApi.api().getNewToken(),listener);
    }

    public static Disposable writeData(Map<String,String> map,RequestListener<List> listener){
        return request(YouSuanShiApi.api().writeData(map),listener);
    }
}
