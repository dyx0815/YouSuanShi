package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public interface WorkBenchManagerActivityView extends BaseView {
    void getWorkBenchModelSuccess(int code, List<AddWorkBenchBean> data);
    void getWorkBenchModelFailed(int code,String msg);
    void offLineModelSuccess(int code, BaseBean data);
    void offLineModelFailed(int code,String msg);
    void deleteWorkBenchSuccess(int code,BaseBean data);
    void deleteWorkBenchFailed(int code,String msg);
}
