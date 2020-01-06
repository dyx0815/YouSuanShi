package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.IndustryBean;

import java.util.List;

public interface ChooseIndustryView extends BaseView {
    void getIndustrySuccess(int code, List<IndustryBean> industryBean);
    void getIndustryFailed(int code,String message);
}
