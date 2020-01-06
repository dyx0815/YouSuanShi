package com.dan.yousuanshi.module.chat.bean;

import java.util.List;

public class GroupFileBean {

    /**
     * total : 2
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":3,"create_time":"1970-01-01 08:00","file_url":"http://www.zixuezhilu.com/a.jpg","file_type":2,"upload_user":4286,"nick_name":"","user_portrait":"","file_name":"我的头像"},{"id":4,"create_time":"1970-01-01 08:00","file_url":"http://www.zixuezhilu.com/a.jpg","file_type":2,"upload_user":4286,"nick_name":"","user_portrait":""}]
     */

    private int total; //总数量
    private int per_page; //每页显示数量
    private int current_page;//当前页
    private int last_page; //最后一页
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

    public static class DataBean implements Comparable{
        /**
         * id : 3
         * create_time : 1970-01-01 08:00
         * file_url : http://www.zixuezhilu.com/a.jpg
         * file_type : 2
         * upload_user : 4286
         * nick_name :
         * user_portrait :
         * file_name : 我的头像
         */

        private int id;
        private String create_time; //上传时间
        private String file_url; //文件地址
        private int file_type;//文件类型
        private long file_size;
        private int upload_user;  //上传用户ID
        private String nick_name; //上传用户昵称
        private String user_portrait; //上传用户头像
        private String file_name; //文件名称
        private boolean isChecked;
        private char headLetter;

        public long getFile_size() {
            return file_size;
        }

        public void setFile_size(long file_size) {
            this.file_size = file_size;
        }

        public char getHeadLetter() {
            return headLetter;
        }

        public void setHeadLetter(char headLetter) {
            this.headLetter = headLetter;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

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

        public String getFile_url() {
            return file_url;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public int getFile_type() {
            return file_type;
        }

        public void setFile_type(int file_type) {
            this.file_type = file_type;
        }

        public int getUpload_user() {
            return upload_user;
        }

        public void setUpload_user(int upload_user) {
            this.upload_user = upload_user;
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

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof GroupFileBean.DataBean) {
                GroupFileBean.DataBean that = (GroupFileBean.DataBean) o;
                if (getHeadLetter() == ' ') {
                    if (that.getHeadLetter() == ' ') {
                        return 0;
                    }
                    return -1;
                }
                if (that.getHeadLetter() == ' ') {
                    return 1;
                } else if (that.getHeadLetter() > getHeadLetter()) {
                    return -1;
                } else if (that.getHeadLetter() == getHeadLetter()) {
                    return 0;
                }
                return 1;
            } else {
                throw new ClassCastException();
            }
        }
    }
}
