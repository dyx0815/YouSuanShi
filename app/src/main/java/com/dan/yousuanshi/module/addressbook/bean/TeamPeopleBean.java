package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TeamPeopleBean implements Parcelable,Comparable {

    /**
     * user_portrait : https://dzcdn.zixuezhilu.com/ac1f0d1505a291f0a9963762bd9186b1.jpg
     * user_id : 1
     * real_name : danyixiong
     * office :
     * is_create : 0
     * is_master : 1
     * power_id : 4
     * is_supervisor : 1
     */

    private String user_portrait;
    private int user_id;
    private String real_name;
    private String office;
    private int is_create;
    private int is_master;
    private int power_id;
    private int is_supervisor;
    private boolean isChecked;//判断是否选中
    private char headLetter;
    private boolean isShowLetter;

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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public int getIs_create() {
        return is_create;
    }

    public void setIs_create(int is_create) {
        this.is_create = is_create;
    }

    public int getIs_master() {
        return is_master;
    }

    public void setIs_master(int is_master) {
        this.is_master = is_master;
    }

    public int getPower_id() {
        return power_id;
    }

    public void setPower_id(int power_id) {
        this.power_id = power_id;
    }

    public int getIs_supervisor() {
        return is_supervisor;
    }

    public void setIs_supervisor(int is_supervisor) {
        this.is_supervisor = is_supervisor;
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
        dest.writeString(this.office);
        dest.writeInt(this.is_create);
        dest.writeInt(this.is_master);
        dest.writeInt(this.power_id);
        dest.writeInt(this.is_supervisor);
    }

    public TeamPeopleBean() {
    }

    protected TeamPeopleBean(Parcel in) {
        this.user_portrait = in.readString();
        this.user_id = in.readInt();
        this.real_name = in.readString();
        this.office = in.readString();
        this.is_create = in.readInt();
        this.is_master = in.readInt();
        this.power_id = in.readInt();
        this.is_supervisor = in.readInt();
    }

    public static final Parcelable.Creator<TeamPeopleBean> CREATOR = new Parcelable.Creator<TeamPeopleBean>() {
        @Override
        public TeamPeopleBean createFromParcel(Parcel source) {
            return new TeamPeopleBean(source);
        }

        @Override
        public TeamPeopleBean[] newArray(int size) {
            return new TeamPeopleBean[size];
        }
    };

    @Override
    public int compareTo(Object o) {
        if (o instanceof TeamPeopleBean) {
            TeamPeopleBean that = (TeamPeopleBean) o;
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
}
