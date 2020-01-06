package com.dan.yousuanshi.module.mine.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MyCollectBean {

    /**
     * total : 5
     * per_page : 15
     * current_page : 1
     * last_page : 1
     * data : [{"id":3,"file_type":2,"content":"[]","create_time":"2019-11-26","file_suffix":"","file_size":0,"send_nick_name":"张三丰"},{"id":4,"file_type":3,"content":"http://www.cc","create_time":"2019-11-26","file_suffix":"","file_size":1,"send_nick_name":"张三丰"},{"id":5,"file_type":4,"content":"http://","create_time":"2019-11-26","file_suffix":"png","file_size":1,"send_nick_name":"张三丰"},{"id":6,"file_type":4,"content":"http://ccc.ccc","create_time":"2019-11-26","file_suffix":".png","file_size":1,"send_nick_name":"张三丰"},{"id":7,"file_type":4,"content":"http://ccc.ccc1","create_time":"2019-11-26","file_suffix":".png","file_size":1,"send_nick_name":"张三丰"}]
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
         * id : 3
         * file_type : 2
         * content : []
         * create_time : 2019-11-26
         * file_suffix :
         * file_size : 0
         * send_nick_name : 张三丰
         */

        private int id;
        private int file_type;
        private String content;
        private String create_time;
        private String file_suffix;
        private int file_size;
        private String send_nick_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFile_type() {
            return file_type;
        }

        public void setFile_type(int file_type) {
            this.file_type = file_type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getFile_suffix() {
            return file_suffix;
        }

        public void setFile_suffix(String file_suffix) {
            this.file_suffix = file_suffix;
        }

        public int getFile_size() {
            return file_size;
        }

        public void setFile_size(int file_size) {
            this.file_size = file_size;
        }

        public String getSend_nick_name() {
            return send_nick_name;
        }

        public void setSend_nick_name(String send_nick_name) {
            this.send_nick_name = send_nick_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.file_type);
            dest.writeString(this.content);
            dest.writeString(this.create_time);
            dest.writeString(this.file_suffix);
            dest.writeInt(this.file_size);
            dest.writeString(this.send_nick_name);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.file_type = in.readInt();
            this.content = in.readString();
            this.create_time = in.readString();
            this.file_suffix = in.readString();
            this.file_size = in.readInt();
            this.send_nick_name = in.readString();
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
}
