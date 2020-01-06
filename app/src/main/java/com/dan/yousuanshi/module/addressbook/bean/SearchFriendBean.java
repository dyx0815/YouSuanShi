package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import per.goweii.rxhttp.request.base.BaseBean;

public class SearchFriendBean extends BaseBean implements Parcelable {

    /**
     * user_id : 4285
     */

    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.user_id);
    }

    public SearchFriendBean() {
    }

    protected SearchFriendBean(Parcel in) {
        this.user_id = in.readInt();
    }

    public static final Parcelable.Creator<SearchFriendBean> CREATOR = new Parcelable.Creator<SearchFriendBean>() {
        @Override
        public SearchFriendBean createFromParcel(Parcel source) {
            return new SearchFriendBean(source);
        }

        @Override
        public SearchFriendBean[] newArray(int size) {
            return new SearchFriendBean[size];
        }
    };
}
