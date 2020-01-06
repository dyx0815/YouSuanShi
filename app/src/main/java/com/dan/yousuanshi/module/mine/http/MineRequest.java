package com.dan.yousuanshi.module.mine.http;

import com.dan.yousuanshi.http.BaseRequest;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.http.YouSuanShiApi;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.mine.bean.BlackListBean;
import com.dan.yousuanshi.module.mine.bean.MyCollectBean;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class MineRequest extends BaseRequest {
    public static Disposable updateUserInfo(Map<String,String> map, RequestListener<List> listener){
        return request(YouSuanShiApi.api().updateUserInfo(map),listener);
    }

    public static Disposable sendCode(RequestListener<SendCodeBean> listener){
        return request(YouSuanShiApi.api().sendCode(),listener);
    }

    public static Disposable sendCode2(Map<String,String> map,RequestListener<SendCodeBean> listener){
        return request(YouSuanShiApi.api().sendCode2(map),listener);
    }

    public static Disposable checkCode(Map<String,String> map,RequestListener<CheckCodeBean> listener){
        return request(YouSuanShiApi.api().updatePhoneCheckCode(map),listener);
    }

    public static Disposable checkCode2(Map<String,String> map,RequestListener<List> listener){
        return request(YouSuanShiApi.api().updatePhoneCheckCode2(map),listener);
    }

    public static Disposable getMyTeam(Map<String,String> map,RequestListener<List<MyTeamBean>> listener){
        return request(YouSuanShiApi.api().getMyTeam(map),listener);
    }

    public static Disposable getMyTeam(RequestListener<List<MyTeamBean>> listener){
        return request(YouSuanShiApi.api().getMyTeam(),listener);
    }
    public static Disposable feedback(Map<String,String> map,RequestListener<List> listener){
        return request(YouSuanShiApi.api().feedback(map),listener);
    }

    public static Disposable setFirstTeam(Map<String,String> map,RequestListener<List> listener){
        return request(YouSuanShiApi.api().setFirstTeam(map),listener);
    }

    public static Disposable setPassword(Map<String,Object> map,RequestListener<List> listener){
        return request(YouSuanShiApi.api().setPassword(map),listener);
    }

    public static Disposable getBlackList(Map<String,Object> map,RequestListener<BlackListBean> listener){
        return request(YouSuanShiApi.api().getBlackList(map),listener);
    }

    public static Disposable getMyCollect(Map<String,Object> map,RequestListener<MyCollectBean> listener){
        return request(YouSuanShiApi.api().getMyCollect(map),listener);
    }
    public static Disposable deleteCollect(Map<String,Object> map,RequestListener<List> listener){
        return request(YouSuanShiApi.api().deleteCollect(map),listener);
    }
}
