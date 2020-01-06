package com.dan.yousuanshi.module.mine.bean;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public class BlackListBean extends BaseBean {

    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"nick_name":"就","user_id":11,"user_portrait":"https://dzcdn.zixuezhilu.com/fa63bd960e37cce6e85ac0e609f07e67.jpg"}]
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
         * nick_name : 就
         * user_id : 11
         * user_portrait : https://dzcdn.zixuezhilu.com/fa63bd960e37cce6e85ac0e609f07e67.jpg
         */

        private int id;
        private String nick_name;
        private int user_id;
        private String user_portrait;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
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
