package com.dan.yousuanshi.module.addressbook.bean;

import java.util.List;

public class TeamApplyListBean {

    /**
     * company_logo : https://dzcdn.zixuezhilu.com/dzys20191224153719image.jpg
     * c_name : 陕西师范大学
     * diyMessage : []
     */

    private String company_logo;
    private String c_name;
    private List<DiyApplicationSettingBean> diyMessage;

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public List<DiyApplicationSettingBean> getDiyMessage() {
        return diyMessage;
    }

    public void setDiyMessage(List<DiyApplicationSettingBean> diyMessage) {
        this.diyMessage = diyMessage;
    }
}
