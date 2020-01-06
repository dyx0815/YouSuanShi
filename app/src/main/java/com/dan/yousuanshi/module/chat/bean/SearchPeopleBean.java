package com.dan.yousuanshi.module.chat.bean;

import java.util.List;

public class SearchPeopleBean {
    /**
     * total : 2
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"nick_name":"12345678913","real_name":"","c_name":"阿里巴巴","user_id":15,"user_portrait":"https://dzcdn.zixuezhilu.com/ac1f0d1505a291f0a9963762bd9186b1.jpg"},{"nick_name":"13888888888","real_name":"","c_name":"伟哥公司","user_id":13,"user_portrait":"https://dzcdn.zixuezhilu.com/ac1f0d1505a291f0a9963762bd9186b1.jpg"}]
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

    public static class DataBean {
        /**
         * nick_name : 12345678913
         * real_name :
         * c_name : 阿里巴巴
         * user_id : 15
         * user_portrait : https://dzcdn.zixuezhilu.com/ac1f0d1505a291f0a9963762bd9186b1.jpg
         */

        private String nick_name;
        private String real_name;
        private String c_name;
        private int user_id;
        private String user_portrait;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_portrait() {
            return user_portrait;
        }

        public void setUser_portrait(String user_portrait) {
            this.user_portrait = user_portrait;
        }
    }

}
