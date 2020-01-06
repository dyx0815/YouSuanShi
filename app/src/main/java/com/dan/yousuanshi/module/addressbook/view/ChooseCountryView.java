package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.CountryBean;

import java.util.List;

public interface ChooseCountryView extends BaseView {
    void getCountrySuccess(int code, List<CountryBean> data);
    void getCountryFailed(int code, String message);
    void updateUserInfoSuccess(int code, List list);
    void updateUserInfoFailed(int code,String msg);
}
