package com.dan.yousuanshi.module.shared.bean;

public class BannerBean {

    /**
     * id : 1
     * company_id : 0
     * banner_img : https://dzcdn.zixuezhilu.com/defaultbanner/banner.jpg
     * banner_url :
     */

    private int id;
    private int company_id;
    private String banner_img;
    private String banner_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }
}
