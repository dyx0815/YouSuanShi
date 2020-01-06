package com.dan.yousuanshi.module.chat.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public class GroupAnnouncementBean extends BaseBean {

    /**
     * total : 1 /##总数量
     * per_page : 10 //每页显示数量
     * current_page : 1 //当前页数
     * last_page : 1 //最后一页
     * data : [{"id":3,"create_time":"10-29 15:48","content":"测试测试","user_id":4286,"nick_name":"","user_portrait":""}]
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
         * id : 3 //公告ID
         * create_time : 10-29 15:48 //创建时间:已格式化
         * content : 测试测试 //公告内容
         * user_id : 4286 //发布者ID
         * nick_name : //发布者昵称
         * user_portrait : //发布者头像
         */

        private int id;
        private String create_time;
        private String content;
        private int user_id;
        private String nick_name;
        private String user_portrait;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getUser_portrait() {
            return user_portrait;
        }

        public void setUser_portrait(String user_portrait) {
            this.user_portrait = user_portrait;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.create_time);
            dest.writeString(this.content);
            dest.writeInt(this.user_id);
            dest.writeString(this.nick_name);
            dest.writeString(this.user_portrait);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.create_time = in.readString();
            this.content = in.readString();
            this.user_id = in.readInt();
            this.nick_name = in.readString();
            this.user_portrait = in.readString();
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
