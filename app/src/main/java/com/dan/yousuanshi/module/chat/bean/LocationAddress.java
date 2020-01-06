package com.dan.yousuanshi.module.chat.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationAddress implements Parcelable {
    private String locationName;
    private String locationAddess;
    private double latitude;
    private double longitude;
    private boolean isSelect = false;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddess() {
        return locationAddess;
    }

    public void setLocationAddess(String locationAddess) {
        this.locationAddess = locationAddess;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.locationName);
        dest.writeString(this.locationAddess);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    public LocationAddress() {
    }

    protected LocationAddress(Parcel in) {
        this.locationName = in.readString();
        this.locationAddess = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<LocationAddress> CREATOR = new Parcelable.Creator<LocationAddress>() {
        @Override
        public LocationAddress createFromParcel(Parcel source) {
            return new LocationAddress(source);
        }

        @Override
        public LocationAddress[] newArray(int size) {
            return new LocationAddress[size];
        }
    };
}
