package com.dan.yousuanshi.module.mine.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MyTeamBean implements Parcelable {

    /**
     * companyId : 13
     * companyName : 傻屌公司
     * companyLogo :
     * companyMaster : 0 //如果是1,显示管理按钮
     */

    private int companyId;
    private String companyName;
    private String companyLogo;
    private int companyMaster;
    private int mainCompany;//1主企业
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public MyTeamBean(int companyId, String companyName, String companyLogo) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
    }

    public int getMainCompany() {
        return mainCompany;
    }

    public void setMainCompany(int mainCompany) {
        this.mainCompany = mainCompany;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public int getCompanyMaster() {
        return companyMaster;
    }

    public void setCompanyMaster(int companyMaster) {
        this.companyMaster = companyMaster;
    }

    @Override
    public String toString() {
        return "MyTeamBean{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", companyLogo='" + companyLogo + '\'' +
                ", companyMaster=" + companyMaster +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.companyId);
        dest.writeString(this.companyName);
        dest.writeString(this.companyLogo);
        dest.writeInt(this.companyMaster);
    }

    public MyTeamBean() {
    }

    protected MyTeamBean(Parcel in) {
        this.companyId = in.readInt();
        this.companyName = in.readString();
        this.companyLogo = in.readString();
        this.companyMaster = in.readInt();
    }

    public static final Parcelable.Creator<MyTeamBean> CREATOR = new Parcelable.Creator<MyTeamBean>() {
        @Override
        public MyTeamBean createFromParcel(Parcel source) {
            return new MyTeamBean(source);
        }

        @Override
        public MyTeamBean[] newArray(int size) {
            return new MyTeamBean[size];
        }
    };
}
