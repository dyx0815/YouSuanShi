package com.dan.yousuanshi.module.chat.bean;

import java.io.Serializable;

public class SendMessageBean implements Serializable {
    /**
     * pid : 3私聊时,为对方UID:这个数据在请求用户数据时返回的user_id,群聊时,为群ID
     * stxt : 11发送的内容:取决于发送消息的类型
     * mid : 2我的UID
     * type : 2发送类型,1,心跳,2,私聊,3,群聊
     * fileype : 1消息类型,1,文本,2,语音,3,文件(类似一个压缩包之类的文件),4,图片,5.表情
     * "temp":"自己定义", ///自己定义
     */

    private int pid;//接收人id
    private String stxt;//文本 当类型为语音时 为语音时长
    private int mid;//用户id
    private int type;//发送类型1,心跳,2,私聊,3,群聊
    private String temp;
    private int fileype = 1 ;//消息类型1,文本,2,语音,3,文件(类似一个压缩包之类的文件),4,图片,5.表情
    private ChatUserInfoBean userinfo; //用户信息json
    private String msgid = "0"; //消息id
    private int sId;//群聊时发送人id
    private ChatUserInfoBean groupinfo;//群聊信息json


    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public ChatUserInfoBean getGroupinfo() {
        return groupinfo;
    }

    public void setGroupinfo(ChatUserInfoBean groupinfo) {
        this.groupinfo = groupinfo;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public SendMessageBean(){

    }

    public SendMessageBean(int pid, String stxt, int mid, int type, int fileype,ChatUserInfoBean userinfo,ChatUserInfoBean groupinfo) {
        this.pid = pid;
        this.stxt = stxt;
        this.mid = mid;
        this.type = type;
        this.fileype = fileype;
        this.userinfo = userinfo;
        this.groupinfo = groupinfo;
    }

    public SendMessageBean(int pid, String stxt, int mid, int type, int fileype,ChatUserInfoBean userinfo) {
        this.pid = pid;
        this.stxt = stxt;
        this.mid = mid;
        this.type = type;
        this.fileype = fileype;
        this.userinfo = userinfo;
    }

    public SendMessageBean(int pid, String stxt, int mid, int type, int fileype,ChatUserInfoBean userinfo,String temp,ChatUserInfoBean groupinfo) {
        this.pid = pid;
        this.stxt = stxt;
        this.mid = mid;
        this.type = type;
        this.fileype = fileype;
        this.userinfo = userinfo;
        this.temp = temp;
        this.groupinfo = groupinfo;
    }

    public ChatUserInfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(ChatUserInfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getStxt() {
        return stxt;
    }

    public void setStxt(String stxt) {
        this.stxt = stxt;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getFileype() {
        return fileype;
    }

    public void setFileype(int fileype) {
        this.fileype = fileype;
    }

    @Override
    public String toString() {
        return "SendMessageBean{" +
                "pid=" + pid +
                ", stxt='" + stxt + '\'' +
                ", mid=" + mid +
                ", type=" + type +
                ", temp='" + temp + '\'' +
                ", fileype=" + fileype +
                ", userinfo='" + userinfo + '\'' +
                ", msgid='" + msgid + '\'' +
                ", groupinfo='" + groupinfo + '\'' +
                '}';
    }
}
