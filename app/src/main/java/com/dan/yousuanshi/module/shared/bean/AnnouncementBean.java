package com.dan.yousuanshi.module.shared.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AnnouncementBean implements Parcelable {

    /**
     * id : 1
     * title : 新消息
     * content : 这是一个新消息
     * creater_user : 2
     * real_name : 王麻子
     * nick_name : 战五渣
     */

    private int id;
    private String title;
    private String content;
    private int creater_user;
    private String real_name;
    private String nick_name;
    private String create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreater_user() {
        return creater_user;
    }

    public void setCreater_user(int creater_user) {
        this.creater_user = creater_user;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.creater_user);
        dest.writeString(this.real_name);
        dest.writeString(this.nick_name);
        dest.writeString(this.create_time);
    }

    public AnnouncementBean() {
    }

    protected AnnouncementBean(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.creater_user = in.readInt();
        this.real_name = in.readString();
        this.nick_name = in.readString();
        this.create_time = in.readString();
    }

    public static final Parcelable.Creator<AnnouncementBean> CREATOR = new Parcelable.Creator<AnnouncementBean>() {
        @Override
        public AnnouncementBean createFromParcel(Parcel source) {
            return new AnnouncementBean(source);
        }

        @Override
        public AnnouncementBean[] newArray(int size) {
            return new AnnouncementBean[size];
        }
    };
}
