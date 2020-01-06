package com.dan.yousuanshi.module.addressbook.bean;

public class DepartmentInfoBean {

    /**
     * group_name : 测试公司
     * id : 4
     * group_creater : 6
     * parent_id : 0
     * real_name : lixianghe
     * group_creater_name : 侯彬
     * group_parent_name :
     * stop_chat : 0
     */

    private String group_name;
    private int id;
    private int group_creater;
    private int parent_id;
    private String real_name;
    private String group_creater_name;
    private String group_parent_name;
    private int stop_chat;

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

    public int getGroup_creater() {
        return group_creater;
    }

    public void setGroup_creater(int group_creater) {
        this.group_creater = group_creater;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getGroup_creater_name() {
        return group_creater_name;
    }

    public void setGroup_creater_name(String group_creater_name) {
        this.group_creater_name = group_creater_name;
    }

    public String getGroup_parent_name() {
        return group_parent_name;
    }

    public void setGroup_parent_name(String group_parent_name) {
        this.group_parent_name = group_parent_name;
    }

    public int getStop_chat() {
        return stop_chat;
    }

    public void setStop_chat(int stop_chat) {
        this.stop_chat = stop_chat;
    }
}
