package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public class IndustryBean extends BaseBean implements Parcelable {

    /**
     * id : 1
     * parent_id : 0
     * industry : 互联网/信息技术
     * child : [{"id":2,"industry":"计算机软件"},{"id":3,"industry":"硬件设施服务"},{"id":4,"industry":"电子商务"},{"id":5,"industry":"游戏"},{"id":6,"industry":"企业应用"}]
     */

    private int id;
    private int parent_id;
    private String industry;
    private String icon_select;
    private String icon_unselect;
    private List<ChildBean> child;
    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getIcon_unselect() {
        return icon_unselect;
    }

    public void setIcon_unselect(String icon_unselect) {
        this.icon_unselect = icon_unselect;
    }

    public String getIcon_select() {
        return icon_select;
    }

    public void setIcon_select(String icon_select) {
        this.icon_select = icon_select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean implements Parcelable {
        /**
         * id : 2
         * industry : 计算机软件
         */

        private int id;
        private String industry;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.industry);
        }

        public ChildBean() {
        }

        protected ChildBean(Parcel in) {
            this.id = in.readInt();
            this.industry = in.readString();
        }

        public static final Creator<ChildBean> CREATOR = new Creator<ChildBean>() {
            @Override
            public ChildBean createFromParcel(Parcel source) {
                return new ChildBean(source);
            }

            @Override
            public ChildBean[] newArray(int size) {
                return new ChildBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.parent_id);
        dest.writeString(this.industry);
        dest.writeString(this.icon_select);
        dest.writeString(this.icon_unselect);
        dest.writeList(this.child);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    public IndustryBean() {
    }

    protected IndustryBean(Parcel in) {
        this.id = in.readInt();
        this.parent_id = in.readInt();
        this.industry = in.readString();
        this.icon_select = in.readString();
        this.icon_unselect = in.readString();
        this.child = new ArrayList<ChildBean>();
        in.readList(this.child, ChildBean.class.getClassLoader());
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<IndustryBean> CREATOR = new Parcelable.Creator<IndustryBean>() {
        @Override
        public IndustryBean createFromParcel(Parcel source) {
            return new IndustryBean(source);
        }

        @Override
        public IndustryBean[] newArray(int size) {
            return new IndustryBean[size];
        }
    };
}
