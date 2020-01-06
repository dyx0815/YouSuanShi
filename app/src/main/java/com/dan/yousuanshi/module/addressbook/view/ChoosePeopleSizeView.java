package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.PeopleSizeBean;

import java.util.List;

public interface ChoosePeopleSizeView extends BaseView {
    void getPeopleSizeSuccess(int code, List<PeopleSizeBean> data);
    void getPeopleSizeFailed(int code,String msg);
}
