package com.dan.yousuanshi.module.shared;

import com.dan.yousuanshi.http.BaseRequest;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.http.YouSuanShiApi;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;
import com.dan.yousuanshi.module.shared.bean.AnnouncementBean;
import com.dan.yousuanshi.module.shared.bean.AnnouncementLisBean;
import com.dan.yousuanshi.module.shared.bean.BannerBean;
import com.dan.yousuanshi.module.shared.bean.TemplateBean;
import com.dan.yousuanshi.module.shared.bean.WorkbenchBean;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import per.goweii.rxhttp.request.base.BaseBean;

public class SharedRequest extends BaseRequest {
    public static Disposable getNewAnnouncement(int teamId, RequestListener<AnnouncementBean> listener){
        return request(YouSuanShiApi.api().getNewAnnouncement(teamId),listener);
    }
    public static Disposable getWorkbench(int teamId, RequestListener<WorkbenchBean> listener){
        return request(YouSuanShiApi.api().getWorkbench(teamId),listener);
    }
    public static Disposable getBanner(int teamId, RequestListener<List<BannerBean>> listener){
        return request(YouSuanShiApi.api().getBanner(teamId),listener);
    }
    public static Disposable getModelList(int teamId, RequestListener<List<AddWorkBenchBean>> listener){
        return request(YouSuanShiApi.api().getModelList(teamId),listener);
    }
    public static Disposable setCompanyModel(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().setCompanyModel(map),listener);
    }
    public static Disposable onLineModel(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().onLineModel(map),listener);
    }
    public static Disposable getAnnouncement(Map<String,Object> map, RequestListener<AnnouncementLisBean> listener){
        return request(YouSuanShiApi.api().getAnnouncement(map),listener);
    }
    public static Disposable getWorkbenchModel(int companyId, RequestListener<List<AddWorkBenchBean>> listener){
        return request(YouSuanShiApi.api().getWorkbenchModel(companyId),listener);
    }
    public static Disposable addAnnouncement(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().addAnnouncement(map),listener);
    }
    public static Disposable updateAnnouncement(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().updateAnnouncement(map),listener);
    }
    public static Disposable deleteSharedAnnouncement(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().deleteSharedAnnouncement(map),listener);
    }
    public static Disposable offLineModel(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().offLineModel(map),listener);
    }
    public static Disposable deleteWorkBench(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().deleteWorkBench(map),listener);
    }
    public static Disposable updateModelName(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().updateModelName(map),listener);
    }
    public static Disposable addModel(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().addModel(map),listener);
    }
    public static Disposable sortModel(Map<String,Object> map, RequestListener<BaseBean> listener){
        return request(YouSuanShiApi.api().sortModel(map),listener);
    }
    public static Disposable getTemplate(int companyId, RequestListener<List<TemplateBean>> listener){
        return request(YouSuanShiApi.api().getTemplate(companyId),listener);
    }
}
