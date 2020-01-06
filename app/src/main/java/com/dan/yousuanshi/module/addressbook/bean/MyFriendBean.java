package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import per.goweii.rxhttp.request.base.BaseBean;

public class MyFriendBean extends BaseBean implements Comparable, Parcelable {

    /**
     * user_id : 2
     * nick_name : null
     * user_portrait : http://39.98.186.118/reckoner//Public/admin/images/user_img.png
     * c_name : 百度人工智能
     * cid : 18
     * did : 112
     * d_name : 厂领导
     */

    private int user_id;
    private String nick_name;
    private String user_portrait;
    private int isCreate = 0;
    private int isMaster = 0;
    private String c_name;
    private int cid;
    private int did;
    private String d_name;
    private char headLetter;
    private boolean isShowLetter;
    private int type = 0;//填充类型
    private boolean isChecked = false;

    public int getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(int isCreate) {
        this.isCreate = isCreate;
    }

    public int getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(int isMaster) {
        this.isMaster = isMaster;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isShowLetter() {
        return isShowLetter;
    }

    public void setShowLetter(boolean showLetter) {
        isShowLetter = showLetter;
    }

    public char getHeadLetter() {
        return headLetter;
    }

    public void setHeadLetter(char headLetter) {
        this.headLetter = headLetter;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_portrait() {
        return user_portrait;
    }

    public void setUser_portrait(String user_portrait) {
        this.user_portrait = user_portrait;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof MyFriendBean) {
            MyFriendBean that = (MyFriendBean) o;
            if (getHeadLetter() == ' ') {
                if (that.getHeadLetter() == ' ') {
                    return 0;
                }
                return -1;
            }
            if (that.getHeadLetter() == ' ') {
                return 1;
            } else if (that.getHeadLetter() > getHeadLetter()) {
                return -1;
            } else if (that.getHeadLetter() == getHeadLetter()) {
                return 0;
            }
            return 1;
        } else {
            throw new ClassCastException();
        }
    }


    public MyFriendBean() {
    }



    public MyFriendBean(int uId,String nick_name, String user_portrait) {
        this.user_id = uId;
        this.nick_name = nick_name;
        this.user_portrait = user_portrait;
    }

    public MyFriendBean(int user_id, String nick_name, String user_portrait, int  isCreate, int isMaster) {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.user_portrait = user_portrait;
        this.isCreate = isCreate;
        this.isMaster = isMaster;
    }

    @Override
    public String toString() {
        return "MyFriendBean{" +
                "user_id=" + user_id +
                ", nick_name='" + nick_name + '\'' +
                ", user_portrait='" + user_portrait + '\'' +
                ", c_name='" + c_name + '\'' +
                ", cid=" + cid +
                ", did=" + did +
                ", d_name='" + d_name + '\'' +
                ", headLetter=" + headLetter +
                ", isShowLetter=" + isShowLetter +
                ", type=" + type +
                ", isChecked=" + isChecked +
                ", isCreate=" + isCreate +
                ", isMaster=" + isMaster +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.user_id);
        dest.writeString(this.nick_name);
        dest.writeString(this.user_portrait);
        dest.writeInt(this.isCreate);
        dest.writeInt(this.isMaster);
        dest.writeString(this.c_name);
        dest.writeInt(this.cid);
        dest.writeInt(this.did);
        dest.writeString(this.d_name);
        dest.writeInt(this.headLetter);
        dest.writeByte(this.isShowLetter ? (byte) 1 : (byte) 0);
        dest.writeInt(this.type);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected MyFriendBean(Parcel in) {
        this.user_id = in.readInt();
        this.nick_name = in.readString();
        this.user_portrait = in.readString();
        this.isCreate = in.readInt();
        this.isMaster = in.readInt();
        this.c_name = in.readString();
        this.cid = in.readInt();
        this.did = in.readInt();
        this.d_name = in.readString();
        this.headLetter = (char) in.readInt();
        this.isShowLetter = in.readByte() != 0;
        this.type = in.readInt();
        this.isChecked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MyFriendBean> CREATOR = new Parcelable.Creator<MyFriendBean>() {
        @Override
        public MyFriendBean createFromParcel(Parcel source) {
            return new MyFriendBean(source);
        }

        @Override
        public MyFriendBean[] newArray(int size) {
            return new MyFriendBean[size];
        }
    };
}
