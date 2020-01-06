package com.dan.yousuanshi.module.shared.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AnnouncementLisBean implements Parcelable {

    /**
     * total : 10
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":1,"company_id":1,"title":"测试企业公告","create_time":"2019-12-14 09:17","update_time":"","content":"企业公告内容","c_name":"阿里控股"},{"id":2,"company_id":1,"title":"测试企业公告","create_time":"2019-12-14 09:17","update_time":"","content":"企业公告内容","c_name":"阿里控股"},{"id":3,"company_id":1,"title":"测试企业公告","create_time":"2019-12-14 09:17","update_time":"","content":"企业公告内容","c_name":"阿里控股"},{"id":4,"company_id":1,"title":"测试企业公告","create_time":"2019-12-14 09:17","update_time":"","content":"企业公告内容","c_name":"阿里控股"},{"id":5,"company_id":1,"title":"测试企业公告ampltampgt","create_time":"2019-12-14 09:24","update_time":"","content":"企业公告内容","c_name":"阿里控股"},{"id":6,"company_id":1,"title":"测试企业公告ampltampgt[]","create_time":"2019-12-14 09:26","update_time":"","content":"企业公告内容","c_name":"阿里控股"},{"id":7,"company_id":1,"title":"测试企业公告ampltampgt[]()","create_time":"2019-12-14 09:26","update_time":"","content":"企业公告内容","c_name":"阿里控股"},{"id":8,"company_id":1,"title":"[通知]顶顶顶顶","create_time":"2019-12-14 09:27","update_time":"","content":"企业公告内容","c_name":"阿里控股"},{"id":9,"company_id":1,"title":"[]肥嘟嘟多多","create_time":"2019-12-14 09:27","update_time":"","content":"企业公告内容","c_name":"阿里控股"},{"id":10,"company_id":1,"title":"[]【】肥嘟嘟多多","create_time":"2019-12-14 09:27","update_time":"","content":"企业公告内容","c_name":"阿里控股"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 1
         * company_id : 1
         * title : 测试企业公告
         * create_time : 2019-12-14 09:17
         * update_time :
         * content : 企业公告内容
         * c_name : 阿里控股
         */

        private int id;
        private int company_id;
        private String title;
        private String create_time;
        private String update_time;
        private String content;
        private String real_name;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.company_id);
            dest.writeString(this.title);
            dest.writeString(this.create_time);
            dest.writeString(this.update_time);
            dest.writeString(this.content);
            dest.writeString(this.real_name);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.company_id = in.readInt();
            this.title = in.readString();
            this.create_time = in.readString();
            this.update_time = in.readString();
            this.content = in.readString();
            this.real_name = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeInt(this.per_page);
        dest.writeInt(this.current_page);
        dest.writeInt(this.last_page);
        dest.writeTypedList(this.data);
    }

    public AnnouncementLisBean() {
    }

    protected AnnouncementLisBean(Parcel in) {
        this.total = in.readInt();
        this.per_page = in.readInt();
        this.current_page = in.readInt();
        this.last_page = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<AnnouncementLisBean> CREATOR = new Parcelable.Creator<AnnouncementLisBean>() {
        @Override
        public AnnouncementLisBean createFromParcel(Parcel source) {
            return new AnnouncementLisBean(source);
        }

        @Override
        public AnnouncementLisBean[] newArray(int size) {
            return new AnnouncementLisBean[size];
        }
    };
}
