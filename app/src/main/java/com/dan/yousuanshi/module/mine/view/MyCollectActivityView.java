package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.mine.bean.MyCollectBean;

import java.util.List;

public interface MyCollectActivityView extends BaseView {
    void getMyCollectSuccess(int code, MyCollectBean data);
    void getMyCollectFailed(int code,String msg);
    void deleteCollectSuccess(int code, List list,int position);
    void deleteCollectFailed(int code,String msg);
}
