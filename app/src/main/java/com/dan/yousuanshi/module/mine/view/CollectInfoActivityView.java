package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface CollectInfoActivityView extends BaseView {
    void deleteCollectSuccess(int code, List list);
    void deleteCollectFailed(int code,String msg);
}
