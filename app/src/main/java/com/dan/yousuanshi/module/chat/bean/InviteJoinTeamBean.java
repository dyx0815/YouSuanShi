package com.dan.yousuanshi.module.chat.bean;

import per.goweii.rxhttp.request.base.BaseBean;

public class InviteJoinTeamBean extends BaseBean {

    /**
     * c_name : 测试公司
     * company_logo : 这是logo
     * id : 18
     */

    private String c_name;
    private String company_logo;
    private int id;

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InviteJoinTeamBean(String c_name, String company_logo, int id) {
        this.c_name = c_name;
        this.company_logo = company_logo;
        this.id = id;
    }
}
