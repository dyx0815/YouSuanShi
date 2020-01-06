package com.dan.yousuanshi.module.chat.bean;

import java.io.Serializable;

public class ChatBean extends SendMessageBean implements Serializable {

    private int id;//在本地数据库的id
    private String name;
    private boolean isTop;//是否置顶
    private String time;//发送时间
    private int flag;//是接受还是发送
    private int messageCount = 0;//消息数量
    private int isRead;//是否已读
    private String userIconUrl;//用户头像url
    private boolean isTime = false;//是否显示时间
    private String path;//当类型为语音 文件 图片时在本地的路径
    private long size = 0;//当类型为语音 为时长 当类型为图片文件 为大小
    private ChatUserInfoBean pUserInfo;
    private int groupType;
    private String groupIcon;//群头像
    private long chatListId;

    public long getChatListId() {
        return chatListId;
    }

    public void setChatListId(long chatListId) {
        this.chatListId = chatListId;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public ChatUserInfoBean getpUserInfo() {
        return pUserInfo;
    }

    public void setpUserInfo(ChatUserInfoBean pUserInfo) {
        this.pUserInfo = pUserInfo;
    }

    public ChatBean(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChatBean(int pid, String name, String userIconUrl,int type){
        super.setPid(pid);
        this.name = name;
        this.userIconUrl = userIconUrl;
        super.setType(type);
    }

    public ChatBean(int pid,String stxt,int mid,int type,int fileype,int flag,String url,ChatUserInfoBean userinfo,String time,ChatUserInfoBean pUserInfo){
        super(pid,stxt,mid,type,fileype,userinfo);
        this.flag = flag;
        this.userIconUrl = url;
        this.time = time;
        this.pUserInfo = pUserInfo;
    }

    public ChatBean(int pid,String stxt,int mid,int type,int fileype,int flag,String url,ChatUserInfoBean userinfo,String time,ChatUserInfoBean pUserInfo,String path){
        super(pid,stxt,mid,type,fileype,userinfo);
        this.flag = flag;
        this.userIconUrl = url;
        this.time = time;
        this.pUserInfo = pUserInfo;
        this.path = path;
    }



    /**
     * 收到消息构造方法
     * @param sendMessageBean
     * @param time
     */
    public ChatBean(SendMessageBean sendMessageBean,String time){
        super(sendMessageBean.getPid(),sendMessageBean.getStxt(),sendMessageBean.getMid(),sendMessageBean.getType(),sendMessageBean.getFileype(),sendMessageBean.getUserinfo(),sendMessageBean.getTemp(),sendMessageBean.getGroupinfo());
        this.time = time;
        this.flag = 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTime(boolean time) {
        isTime = time;
    }

    public boolean isTime() {
        return isTime;
    }

    public void setIsTime(boolean time) {
        isTime = time;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

//    public SendMessageBean getSendMessageBean(){
//        SendMessageBean sendMessageBean = new SendMessageBean();
//        sendMessageBean.setPid(super.getPid());
//        sendMessageBean.setMid(super.getMid());
//        sendMessageBean.setFileype(super.getFileype());
//        sendMessageBean.setTemp(super.getTemp());
//        sendMessageBean.setStxt(super.getStxt());
//        sendMessageBean.setType(super.getType());
//        return sendMessageBean;
//    }

    @Override
    public String toString() {
        return "ChatBean{" +
                ", stxt=" + super.getStxt() +
                ", temp=" + super.getTemp() +
                ", type=" + super.getType() +
                ", fileype='" + super.getFileype() + '\'' +
                ", name='" + name + '\'' +
                ", isTop=" + isTop +
                ", time='" + time + '\'' +
                ", flag=" + flag +
                ", messageCount=" + messageCount +
                ", isRead=" + isRead +
                ", userIconUrl='" + userIconUrl + '\'' +
                ", isTime=" + isTime +
                ", path='" + path + '\'' +
                ", size=" + size +
                '}';
    }



}
