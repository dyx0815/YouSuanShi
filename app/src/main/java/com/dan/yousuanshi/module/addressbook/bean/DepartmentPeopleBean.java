package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DepartmentPeopleBean implements Parcelable {

    /**
     * user_portrait : https://dzcdn.zixuezhilu.com/ac1f0d1505a291f0a9963762bd9186b1.jpg //用户头像
     * user_id : 5 //用户ID
     * real_name : lixianghe //真实姓名
     * is_creater : 0 //群主,貌似不用
     * is_master : 0 //忽视它
     * is_supervisor : 0 /主管
     * canot_chat : 0 /忽视它
     */

    private String user_portrait;
    private int user_id;
    private String real_name;
    private int is_creater;
    private int is_master;
    private int is_supervisor;
    private int canot_chat;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getUser_portrait() {
        return user_portrait;
    }

    public void setUser_portrait(String user_portrait) {
        this.user_portrait = user_portrait;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public int getIs_creater() {
        return is_creater;
    }

    public void setIs_creater(int is_creater) {
        this.is_creater = is_creater;
    }

    public int getIs_master() {
        return is_master;
    }

    public void setIs_master(int is_master) {
        this.is_master = is_master;
    }

    public int getIs_supervisor() {
        return is_supervisor;
    }

    public void setIs_supervisor(int is_supervisor) {
        this.is_supervisor = is_supervisor;
    }

    public int getCanot_chat() {
        return canot_chat;
    }

    public void setCanot_chat(int canot_chat) {
        this.canot_chat = canot_chat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_portrait);
        dest.writeInt(this.user_id);
        dest.writeString(this.real_name);
        dest.writeInt(this.is_creater);
        dest.writeInt(this.is_master);
        dest.writeInt(this.is_supervisor);
        dest.writeInt(this.canot_chat);
    }

    public DepartmentPeopleBean() {


    }

    public DepartmentPeopleBean(String user_portrait, int user_id, String real_name) {
        this.user_portrait = user_portrait;
        this.user_id = user_id;
        this.real_name = real_name;
    }

    protected DepartmentPeopleBean(Parcel in) {
        this.user_portrait = in.readString();
        this.user_id = in.readInt();
        this.real_name = in.readString();
        this.is_creater = in.readInt();
        this.is_master = in.readInt();
        this.is_supervisor = in.readInt();
        this.canot_chat = in.readInt();
    }

    public static final Parcelable.Creator<DepartmentPeopleBean> CREATOR = new Parcelable.Creator<DepartmentPeopleBean>() {
        @Override
        public DepartmentPeopleBean createFromParcel(Parcel source) {
            return new DepartmentPeopleBean(source);
        }

        @Override
        public DepartmentPeopleBean[] newArray(int size) {
            return new DepartmentPeopleBean[size];
        }
    };
}
