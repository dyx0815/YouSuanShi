package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.shared.bean.TemplateBean;

import java.util.List;

public interface TemplateManagerActivityView extends BaseView {
    void getTemplateSuccess(int code, List<TemplateBean> data);
    void getTemplateFailed(int code,String msg);
}
