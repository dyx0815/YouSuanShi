package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public interface AddWorkBenchActivityView extends BaseView {
    void getModelListSuccess(int code, List<AddWorkBenchBean> data);
    void getModelListFailed(int code,String msg);
    void setCompanyModelSuccess(int code, BaseBean data);
    void setCompanyModelFailed(int code,String msg);
    void onLineModelSuccess(int code,BaseBean data);
    void onLineModelFailed(int code,String msg);
}
