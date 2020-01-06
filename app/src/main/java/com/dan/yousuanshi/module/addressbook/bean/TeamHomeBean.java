package com.dan.yousuanshi.module.addressbook.bean;

import per.goweii.rxhttp.request.base.BaseBean;

public class TeamHomeBean extends BaseBean {

    /**
     * cid : 17
     * c_name : 傻屌公司11111222
     * company_logo :
     * industrya : 互联网/信息技术1 //分类1
     * industryb : 计算机软件 /分类2
     * province : 北京市 //省
     * city : 东城区  /市
     * district :  //区县
     * create_time : 未知 //创建时间
     * register_money : 未知 //注册资金
     * register_address : 未知 //注册地址
     * unified_social_credit_code : 未知 //信用代码
     * business_scope : 未知 //经营范围
     */

    private int cid;
    private String c_name;
    private String company_logo;
    private String industrya;
    private String industryb;
    private String province;
    private String city;
    private String district;
    private String create_time;
    private String register_money;
    private String register_address;
    private String unified_social_credit_code;
    private String business_scope;
    private int compay_status;

    public int getCompay_status() {
        return compay_status;
    }

    public void setCompay_status(int compay_status) {
        this.compay_status = compay_status;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

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

    public String getIndustrya() {
        return industrya;
    }

    public void setIndustrya(String industrya) {
        this.industrya = industrya;
    }

    public String getIndustryb() {
        return industryb;
    }

    public void setIndustryb(String industryb) {
        this.industryb = industryb;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getRegister_money() {
        return register_money;
    }

    public void setRegister_money(String register_money) {
        this.register_money = register_money;
    }

    public String getRegister_address() {
        return register_address;
    }

    public void setRegister_address(String register_address) {
        this.register_address = register_address;
    }

    public String getUnified_social_credit_code() {
        return unified_social_credit_code;
    }

    public void setUnified_social_credit_code(String unified_social_credit_code) {
        this.unified_social_credit_code = unified_social_credit_code;
    }

    public String getBusiness_scope() {
        return business_scope;
    }

    public void setBusiness_scope(String business_scope) {
        this.business_scope = business_scope;
    }
}
