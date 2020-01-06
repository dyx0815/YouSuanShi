package com.dan.yousuanshi.module.shared.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.shared.bean.AnnouncementBean;
import com.dan.yousuanshi.module.shared.bean.BannerBean;
import com.dan.yousuanshi.module.shared.bean.WorkbenchBean;

import java.util.List;

public interface SharedFragmentView extends BaseView {
    void getNewAnnouncementSuccess(int code, AnnouncementBean data);
    void getNewAnnouncementFailed(int code,String msg);
    void getWorkbenchSuccess(int code, WorkbenchBean data);
    void getWorkbenchFailed(int code,String msg);
    void getBannerSuccess(int code, List<BannerBean> data);
    void getBannerFailed(int code,String msg);
}
