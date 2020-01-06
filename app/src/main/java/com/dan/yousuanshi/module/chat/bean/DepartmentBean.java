package com.dan.yousuanshi.module.chat.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DepartmentBean implements Parcelable {

    /**
     * did : 140 //部门ID
     * d_name : 技术部 //部门名称
     * parent_id : 0  //上级ID
     * nums : 2 //人员数量
     * stop_chat
     */

    private int id;
    private String group_name;
    private int parent_id;
    private int nums;
    private int stop_chat;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getStop_chat() {
        return stop_chat;
    }

    public void setStop_chat(int stop_chat) {
        this.stop_chat = stop_chat;
    }

    public int getDid() {
        return id;
    }

    public void setDid(int did) {
        this.id = did;
    }

    public String getD_name() {
        return group_name;
    }

    public void setD_name(String d_name) {
        this.group_name = d_name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.group_name);
        dest.writeInt(this.parent_id);
        dest.writeInt(this.nums);
    }

    public DepartmentBean() {
    }

    public DepartmentBean(int id, String group_name) {
        this.id = id;
        this.group_name = group_name;
    }

    protected DepartmentBean(Parcel in) {
        this.id = in.readInt();
        this.group_name = in.readString();
        this.parent_id = in.readInt();
        this.nums = in.readInt();
    }

    public static final Parcelable.Creator<DepartmentBean> CREATOR = new Parcelable.Creator<DepartmentBean>() {
        @Override
        public DepartmentBean createFromParcel(Parcel source) {
            return new DepartmentBean(source);
        }

        @Override
        public DepartmentBean[] newArray(int size) {
            return new DepartmentBean[size];
        }
    };
}
