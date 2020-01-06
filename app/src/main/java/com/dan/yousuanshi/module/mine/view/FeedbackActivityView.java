package com.dan.yousuanshi.module.mine.view;

import com.dan.yousuanshi.base.BaseView;

import java.util.List;

public interface FeedbackActivityView extends BaseView {
    void feedbackSuccess(int code, List list);
    void feedbackFailed(int code,String msg);
}
