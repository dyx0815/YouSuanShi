package com.dan.yousuanshi.module.addressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public class SearchTeamBean extends BaseBean {

    /**
     * total : 7
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":76,"c_name":"傻屌公司名称1","company_logo":null,"province":"北京市","city":"东城区","district":null,"industry1":"互联网/信息技术1","industry2":"计算机软件","is_join":0}]
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
         * id : 76
         * c_name : 傻屌公司名称1
         * company_logo : null
         * province : 北京市
         * city : 东城区
         * district : null
         * industry1 : 互联网/信息技术1
         * industry2 : 计算机软件
         * is_join : 0
         */

        private int id;
        private String c_name;
        private String company_logo;
        private String province;
        private String city;
        private String district;
        private String industry1;
        private String industry2;
        private int is_join;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getCompany_logo() {
            return company_logo;
        }

        public void setCompany_logo(String company_logo) {
            this.company_logo = company_logo;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getIndustry1() {
            return industry1;
        }

        public void setIndustry1(String industry1) {
            this.industry1 = industry1;
        }

        public String getIndustry2() {
            return industry2;
        }

        public void setIndustry2(String industry2) {
            this.industry2 = industry2;
        }

        public int getIs_join() {
            return is_join;
        }

        public void setIs_join(int is_join) {
            this.is_join = is_join;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.c_name);
            dest.writeString(this.company_logo);
            dest.writeString(this.province);
            dest.writeString(this.city);
            dest.writeString(this.district);
            dest.writeString(this.industry1);
            dest.writeString(this.industry2);
            dest.writeInt(this.is_join);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.c_name = in.readString();
            this.company_logo = in.readString();
            this.province = in.readString();
            this.city = in.readString();
            this.district = in.readString();
            this.industry1 = in.readString();
            this.industry2 = in.readString();
            this.is_join = in.readInt();
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
