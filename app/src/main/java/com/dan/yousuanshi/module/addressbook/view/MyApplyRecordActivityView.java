package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.MyApplyRecordBean;

import java.util.List;

public interface MyApplyRecordActivityView extends BaseView {
    void getMyApplyRecordSuccess(int code, MyApplyRecordBean data);
    void getMyApplyRecordFailed(int code,String msg);
    void clearApplyRecordSuccess(int code, List list);
    void clearApplyRecordFailed(int code,String msg);
}
