package com.dan.yousuanshi.module.shared.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class AddWorkBenchBean implements Parcelable {

    /**
     * id : 238
     * model_name : 考勤打卡
     * parent_id : 4
     * company_parent_id : 254
     * min_power : 0
     * level_index : 2
     * user_list : []
     * department_list : []
     * is_offline : 0
     * model_id : 5
     * model_icon : icon_shared_kaoqindaka.png
     * is_master : 0
     * children : [{"id":239,"model_name":"打卡","parent_id":5,"company_parent_id":238,"min_power":0,"level_index":3,"user_list":[],"department_list":[],"is_offline":0,"model_id":17,"model_icon":"icon_shared_daka.png","is_master":0,"children":[]},{"id":240,"model_name":"补卡","parent_id":5,"company_parent_id":238,"min_power":0,"level_index":3,"user_list":[],"department_list":[],"is_offline":1,"model_id":18,"model_icon":"","is_master":0,"children":[]},{"id":241,"model_name":"调班","parent_id":5,"company_parent_id":238,"min_power":0,"level_index":3,"user_list":[],"department_list":[],"is_offline":1,"model_id":19,"model_icon":"","is_master":0,"children":[]},{"id":242,"model_name":"考勤统计","parent_id":5,"company_parent_id":238,"min_power":0,"level_index":3,"user_list":[],"department_list":[],"is_offline":1,"model_id":20,"model_icon":"","is_master":0,"children":[]},{"id":259,"model_name":"打卡","parent_id":5,"company_parent_id":258,"min_power":0,"level_index":2,"user_list":[],"department_list":[],"is_offline":0,"model_id":17,"model_icon":"","is_master":0,"children":[]}]
     */

    private int id;
    private String model_name;
    private int parent_id;
    private int company_parent_id;
    private int min_power;
    private int level_index;
    private int is_offline;
    private int model_id;
    private String model_icon;
    private int is_master;
    private List<Integer> user_list;
    private List<Integer> department_list;
    private List<ChildrenBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getCompany_parent_id() {
        return company_parent_id;
    }

    public void setCompany_parent_id(int company_parent_id) {
        this.company_parent_id = company_parent_id;
    }

    public int getMin_power() {
        return min_power;
    }

    public void setMin_power(int min_power) {
        this.min_power = min_power;
    }

    public int getLevel_index() {
        return level_index;
    }

    public void setLevel_index(int level_index) {
        this.level_index = level_index;
    }

    public int getIs_offline() {
        return is_offline;
    }

    public void setIs_offline(int is_offline) {
        this.is_offline = is_offline;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public String getModel_icon() {
        return model_icon;
    }

    public void setModel_icon(String model_icon) {
        this.model_icon = model_icon;
    }

    public int getIs_master() {
        return is_master;
    }

    public void setIs_master(int is_master) {
        this.is_master = is_master;
    }

    public List<?> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<Integer> user_list) {
        this.user_list = user_list;
    }

    public List<?> getDepartment_list() {
        return department_list;
    }

    public void setDepartment_list(List<Integer> department_list) {
        this.department_list = department_list;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean implements Parcelable {
        /**
         * id : 239
         * model_name : 打卡
         * parent_id : 5
         * company_parent_id : 238
         * min_power : 0
         * level_index : 3
         * user_list : []
         * department_list : []
         * is_offline : 0
         * model_id : 17
         * model_icon : icon_shared_daka.png
         * is_master : 0
         * children : []
         */

        private int id;
        private String model_name;
        private int parent_id;
        private int company_parent_id;
        private int min_power;
        private int level_index;
        private int is_offline;
        private int model_id;
        private String model_icon;
        private int is_master;
        private List<Integer> user_list;
        private List<Integer> department_list;
        private List<ChildrenBean> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModel_name() {
            return model_name;
        }

        public void setModel_name(String model_name) {
            this.model_name = model_name;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getCompany_parent_id() {
            return company_parent_id;
        }

        public void setCompany_parent_id(int company_parent_id) {
            this.company_parent_id = company_parent_id;
        }

        public int getMin_power() {
            return min_power;
        }

        public void setMin_power(int min_power) {
            this.min_power = min_power;
        }

        public int getLevel_index() {
            return level_index;
        }

        public void setLevel_index(int level_index) {
            this.level_index = level_index;
        }

        public int getIs_offline() {
            return is_offline;
        }

        public void setIs_offline(int is_offline) {
            this.is_offline = is_offline;
        }

        public int getModel_id() {
            return model_id;
        }

        public void setModel_id(int model_id) {
            this.model_id = model_id;
        }

        public String getModel_icon() {
            return model_icon;
        }

        public void setModel_icon(String model_icon) {
            this.model_icon = model_icon;
        }

        public int getIs_master() {
            return is_master;
        }

        public void setIs_master(int is_master) {
            this.is_master = is_master;
        }

        public List<Integer> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<Integer> user_list) {
            this.user_list = user_list;
        }

        public List<Integer> getDepartment_list() {
            return department_list;
        }

        public void setDepartment_list(List<Integer> department_list) {
            this.department_list = department_list;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }


        public ChildrenBean() {
        }

        public ChildrenBean(int model_id, String model_name, int parent_id, String model_icon) {
            this.model_name = model_name;
            this.parent_id = parent_id;
            this.model_id = model_id;
            this.model_icon = model_icon;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.model_name);
            dest.writeInt(this.parent_id);
            dest.writeInt(this.company_parent_id);
            dest.writeInt(this.min_power);
            dest.writeInt(this.level_index);
            dest.writeInt(this.is_offline);
            dest.writeInt(this.model_id);
            dest.writeString(this.model_icon);
            dest.writeInt(this.is_master);
            dest.writeList(this.user_list);
            dest.writeList(this.department_list);
            dest.writeList(this.children);
        }

        protected ChildrenBean(Parcel in) {
            this.id = in.readInt();
            this.model_name = in.readString();
            this.parent_id = in.readInt();
            this.company_parent_id = in.readInt();
            this.min_power = in.readInt();
            this.level_index = in.readInt();
            this.is_offline = in.readInt();
            this.model_id = in.readInt();
            this.model_icon = in.readString();
            this.is_master = in.readInt();
            this.user_list = new ArrayList<Integer>();
            in.readList(this.user_list, Integer.class.getClassLoader());
            this.department_list = new ArrayList<Integer>();
            in.readList(this.department_list, Integer.class.getClassLoader());
            this.children = new ArrayList<ChildrenBean>();
            in.readList(this.children, ChildrenBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<ChildrenBean> CREATOR = new Parcelable.Creator<ChildrenBean>() {
            @Override
            public ChildrenBean createFromParcel(Parcel source) {
                return new ChildrenBean(source);
            }

            @Override
            public ChildrenBean[] newArray(int size) {
                return new ChildrenBean[size];
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
        dest.writeString(this.model_name);
        dest.writeInt(this.parent_id);
        dest.writeInt(this.company_parent_id);
        dest.writeInt(this.min_power);
        dest.writeInt(this.level_index);
        dest.writeInt(this.is_offline);
        dest.writeInt(this.model_id);
        dest.writeString(this.model_icon);
        dest.writeInt(this.is_master);
        dest.writeList(this.user_list);
        dest.writeList(this.department_list);
        dest.writeTypedList(this.children);
    }

    public AddWorkBenchBean() {
    }

    protected AddWorkBenchBean(Parcel in) {
        this.id = in.readInt();
        this.model_name = in.readString();
        this.parent_id = in.readInt();
        this.company_parent_id = in.readInt();
        this.min_power = in.readInt();
        this.level_index = in.readInt();
        this.is_offline = in.readInt();
        this.model_id = in.readInt();
        this.model_icon = in.readString();
        this.is_master = in.readInt();
        this.user_list = new ArrayList<Integer>();
        in.readList(this.user_list, Integer.class.getClassLoader());
        this.department_list = new ArrayList<Integer>();
        in.readList(this.department_list, Integer.class.getClassLoader());
        this.children = in.createTypedArrayList(ChildrenBean.CREATOR);
    }

    public static final Parcelable.Creator<AddWorkBenchBean> CREATOR = new Parcelable.Creator<AddWorkBenchBean>() {
        @Override
        public AddWorkBenchBean createFromParcel(Parcel source) {
            return new AddWorkBenchBean(source);
        }

        @Override
        public AddWorkBenchBean[] newArray(int size) {
            return new AddWorkBenchBean[size];
        }
    };
}
