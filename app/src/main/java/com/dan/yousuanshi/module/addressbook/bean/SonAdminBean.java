package com.dan.yousuanshi.module.addressbook.bean;

import java.util.List;

public class SonAdminBean {

    /**
     * id : 16 //权限ID
     * company_model : [42,44,45,46] //权限列表,ID数组
     * is_all_model : 0 //0,部分权限,1,全部权限
     * real_name : 伟哥 //真实姓名
     * nick_name : 18866666666 //昵称
     * user_id : 4 //用户ID
     */

    private int id;
    private int is_all_model;
    private String real_name;
    private String nick_name;
    private int user_id;
    private List<Integer> company_model;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_all_model() {
        return is_all_model;
    }

    public void setIs_all_model(int is_all_model) {
        this.is_all_model = is_all_model;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<Integer> getCompany_model() {
        return company_model;
    }

    public void setCompany_model(List<Integer> company_model) {
        this.company_model = company_model;
    }
}
