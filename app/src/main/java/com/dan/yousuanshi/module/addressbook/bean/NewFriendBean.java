package com.dan.yousuanshi.module.addressbook.bean;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public class NewFriendBean extends BaseBean {

    /**
     * total : 1
     * per_page : 20
     * current_page : 1
     * last_page : 1
     * data : [{"id":2,"sid":3,"send_msg":"xxx","nick_name":null,"user_portrait":"http://39.98.186.118/reckoner//Public/admin/images/user_img.png","send_time":null,"is_pass":1}]
     */

    private int total; //所有数量
    private int per_page; ////每页显示数量
    private int current_page;//当前页数
    private int last_page;//最后一页
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
         * id : 2//申请ID
         * sid : 3//用户ID
         * send_msg : xxx//申请理由
         * nick_name : null//昵称
         * user_portrait : http://39.98.186.118/reckoner//Public/admin/images/user_img.png //头像
         * send_time : null //发送时间
         * is_pass : 1 //1,已通过,0,未处理,2,已拒绝
         */

        private int id;
        private int sid;
        private String send_msg;
        private String nick_name;
        private String user_portrait;
        private Object send_time;
        private int is_pass;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getSend_msg() {
            return send_msg;
        }

        public void setSend_msg(String send_msg) {
            this.send_msg = send_msg;
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

        public Object getSend_time() {
            return send_time;
        }

        public void setSend_time(Object send_time) {
            this.send_time = send_time;
        }

        public int getIs_pass() {
            return is_pass;
        }

        public void setIs_pass(int is_pass) {
            this.is_pass = is_pass;
        }
    }

    @Override
    public String toString() {
        return "NewFriendBean{" +
                "total=" + total +
                ", per_page=" + per_page +
                ", current_page=" + current_page +
                ", last_page=" + last_page +
                ", data=" + data +
                '}';
    }
}
