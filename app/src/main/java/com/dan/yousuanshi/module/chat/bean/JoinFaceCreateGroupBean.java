package com.dan.yousuanshi.module.chat.bean;

import per.goweii.rxhttp.request.base.BaseBean;

public class JoinFaceCreateGroupBean extends BaseBean {

    /**
     * group_avatar : xxx.jpg
     * group_name : 群名称
     * id : 1
     * group_type : 0
     */

    private String group_avatar;
    private String group_name;
    private int id;
    private int group_type;

    public String getGroup_avatar() {
        return group_avatar;
    }

    public void setGroup_avatar(String group_avatar) {
        this.group_avatar = group_avatar;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_type() {
        return group_type;
    }

    public void setGroup_type(int group_type) {
        this.group_type = group_type;
    }
}
