package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public class TeamNewApplyBean extends BaseBean {

    /**
     * total : 2
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":38,"userid":5,"c_name":"测试公司名称","cid":46,"user_tel":"15111111111","real_name":null,"user_portrait":"https://www.zixuezhilu.com:8767/a.jpg","send_msg":"就看你很吊","send_time":"2019-11-06 14:50","diy_message":[],"agree_user":0,"agree_real_name":"","is_pass":0},{"id":38,"userid":5,"c_name":"测试公司名称","cid":46,"user_tel":"15255555555","real_name":null,"user_portrait":"https://www.zixuezhilu.com:8767/a.jpg","send_msg":"就看你很吊","send_time":"2019-11-06 10:09","diy_message":false,"agree_user":0,"agree_real_name":null,"is_pass":0}]
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
         * id : 38 //申请ID
         * userid : 5 //用户ID
         * c_name : 测试公司名称 //企业名称
         * cid : 46 //企业ID
         * user_tel : 15111111111 //申请人手机
         * real_name : null  //申请人真实姓名
         * user_portrait : https://www.zixuezhilu.com:8767/a.jpg //申请人头像
         * send_msg : 就看你很吊 //申请理由
         * send_time : 2019-11-06 14:50 //申请时间
         * diy_message : [] //DIY信息
         * agree_user : 0 //如果有人同意.这里是这个人的userID
         * agree_real_name :  //同意人的真实姓名
         * is_pass : 0 //0,未处理,1,已通过,2,未通过
         */

        private int id;
        private int userid;
        private String c_name;
        private int cid;
        private String user_tel;
        private String real_name;
        private String user_portrait;
        private String send_msg;
        private String send_time;
        private int agree_user;
        private String agree_real_name;
        private int is_pass;
        private List<DiyApplicationSettingBean> diy_message;
        private String explain;

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getUser_tel() {
            return user_tel;
        }

        public void setUser_tel(String user_tel) {
            this.user_tel = user_tel;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getUser_portrait() {
            return user_portrait;
        }

        public void setUser_portrait(String user_portrait) {
            this.user_portrait = user_portrait;
        }

        public String getSend_msg() {
            return send_msg;
        }

        public void setSend_msg(String send_msg) {
            this.send_msg = send_msg;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public int getAgree_user() {
            return agree_user;
        }

        public void setAgree_user(int agree_user) {
            this.agree_user = agree_user;
        }

        public String getAgree_real_name() {
            return agree_real_name;
        }

        public void setAgree_real_name(String agree_real_name) {
            this.agree_real_name = agree_real_name;
        }

        public int getIs_pass() {
            return is_pass;
        }

        public void setIs_pass(int is_pass) {
            this.is_pass = is_pass;
        }

        public List<DiyApplicationSettingBean> getDiy_message() {
            return diy_message;
        }

        public void setDiy_message(List<DiyApplicationSettingBean> diy_message) {
            this.diy_message = diy_message;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.userid);
            dest.writeString(this.c_name);
            dest.writeInt(this.cid);
            dest.writeString(this.user_tel);
            dest.writeString(this.real_name);
            dest.writeString(this.user_portrait);
            dest.writeString(this.send_msg);
            dest.writeString(this.send_time);
            dest.writeInt(this.agree_user);
            dest.writeString(this.agree_real_name);
            dest.writeInt(this.is_pass);
            dest.writeTypedList(this.diy_message);
            dest.writeString(this.explain);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.userid = in.readInt();
            this.c_name = in.readString();
            this.cid = in.readInt();
            this.user_tel = in.readString();
            this.real_name = in.readString();
            this.user_portrait = in.readString();
            this.send_msg = in.readString();
            this.send_time = in.readString();
            this.agree_user = in.readInt();
            this.agree_real_name = in.readString();
            this.is_pass = in.readInt();
            this.diy_message = in.createTypedArrayList(DiyApplicationSettingBean.CREATOR);
            this.explain = in.readString();
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
