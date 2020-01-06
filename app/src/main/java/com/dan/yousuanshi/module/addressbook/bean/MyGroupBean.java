package com.dan.yousuanshi.module.addressbook.bean;

import per.goweii.rxhttp.request.base.BaseBean;

public class MyGroupBean extends BaseBean{

    /**
     * id : 1
     * group_name : 测试创建群组
     * group_avatar : null
     * group_type : 全员群
     */

    private int id;
    private String group_name;
    private String group_avatar;
    private Integer group_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_avatar() {
        return group_avatar;
    }

    public void setGroup_avatar(String group_avatar) {
        this.group_avatar = group_avatar;
    }

    public Integer getGroup_type() {
        return group_type;
    }

    public void setGroup_type(Integer group_type) {
        this.group_type = group_type;
    }

    @Override
    public String toString() {
        return "MyGroupBean{" +
                "id=" + id +
                ", group_name='" + group_name + '\'' +
                ", group_avatar='" + group_avatar + '\'' +
                ", group_type=" + group_type +
                '}';
    }
}
