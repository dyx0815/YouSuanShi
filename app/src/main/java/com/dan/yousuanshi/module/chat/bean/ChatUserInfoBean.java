package com.dan.yousuanshi.module.chat.bean;

import java.io.Serializable;

import per.goweii.rxhttp.request.base.BaseBean;

public class ChatUserInfoBean extends BaseBean implements Serializable {
    private String nickName;//昵称
    private String avatar;//头像路径
    private int groupType;//群类型

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ChatUserInfoBean(){

    }
    public ChatUserInfoBean(String nickName, String avatar) {
        this.nickName = nickName;
        this.avatar = avatar;
    }

    public ChatUserInfoBean(String nickName, String avatar, int groupType) {
        this.nickName = nickName;
        this.avatar = avatar;
        this.groupType = groupType;
    }

    @Override
    public String toString() {
        return "ChatUserInfoBean{" +
                "nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", groupType=" + groupType +
                '}';
    }
}
