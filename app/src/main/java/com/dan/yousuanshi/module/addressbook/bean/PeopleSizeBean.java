package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import per.goweii.rxhttp.request.base.BaseBean;

public class PeopleSizeBean extends BaseBean implements Parcelable {

    /**
     * id : 1
     * personnel_str : 1-9
     */

    private int id;
    private String personnel_str;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonnel_str() {
        return personnel_str;
    }

    public void setPersonnel_str(String personnel_str) {
        this.personnel_str = personnel_str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.personnel_str);
    }

    public PeopleSizeBean() {
    }

    protected PeopleSizeBean(Parcel in) {
        this.id = in.readInt();
        this.personnel_str = in.readString();
    }

    public static final Parcelable.Creator<PeopleSizeBean> CREATOR = new Parcelable.Creator<PeopleSizeBean>() {
        @Override
        public PeopleSizeBean createFromParcel(Parcel source) {
            return new PeopleSizeBean(source);
        }

        @Override
        public PeopleSizeBean[] newArray(int size) {
            return new PeopleSizeBean[size];
        }
    };
}
