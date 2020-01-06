package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DiyApplicationSettingBean implements Parcelable {

    /**
     * label : 留言
     * controlViewType : TextView
     * isRequired : 1
     * controlViewRank : 1
     */

    private String label;
    private String controlViewType;
    private int isRequired;
    private int controlViewRank;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getControlViewType() {
        return controlViewType;
    }

    public void setControlViewType(String controlViewType) {
        this.controlViewType = controlViewType;
    }

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public int getControlViewRank() {
        return controlViewRank;
    }

    public void setControlViewRank(int controlViewRank) {
        this.controlViewRank = controlViewRank;
    }


    public DiyApplicationSettingBean() {
    }

    public DiyApplicationSettingBean(String label, String controlViewType, int isRequired, int controlViewRank) {
        this.label = label;
        this.controlViewType = controlViewType;
        this.isRequired = isRequired;
        this.controlViewRank = controlViewRank;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeString(this.controlViewType);
        dest.writeInt(this.isRequired);
        dest.writeInt(this.controlViewRank);
        dest.writeString(this.value);
    }

    protected DiyApplicationSettingBean(Parcel in) {
        this.label = in.readString();
        this.controlViewType = in.readString();
        this.isRequired = in.readInt();
        this.controlViewRank = in.readInt();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<DiyApplicationSettingBean> CREATOR = new Parcelable.Creator<DiyApplicationSettingBean>() {
        @Override
        public DiyApplicationSettingBean createFromParcel(Parcel source) {
            return new DiyApplicationSettingBean(source);
        }

        @Override
        public DiyApplicationSettingBean[] newArray(int size) {
            return new DiyApplicationSettingBean[size];
        }
    };
}
