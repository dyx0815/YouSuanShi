package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import per.goweii.rxhttp.request.base.BaseBean;

public class CountryBean extends BaseBean implements Parcelable {

    /**
     * id : 1
     * name : 中国
     */

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public CountryBean() {
    }

    protected CountryBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<CountryBean> CREATOR = new Parcelable.Creator<CountryBean>() {
        @Override
        public CountryBean createFromParcel(Parcel source) {
            return new CountryBean(source);
        }

        @Override
        public CountryBean[] newArray(int size) {
            return new CountryBean[size];
        }
    };
}
