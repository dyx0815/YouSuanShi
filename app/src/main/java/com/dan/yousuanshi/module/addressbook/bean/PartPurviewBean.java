package com.dan.yousuanshi.module.addressbook.bean;

import java.util.List;

public class PartPurviewBean {

    /**
     * powerList : [{"id":42,"model_name":"管理控制台","parent_id":0,"min_power":0,"level_index":1,"user_list":"{}","department_list":"{}","is_offline":0,"model_id":1,"is_had":0,"children":[{"id":43,"model_name":"管理工作台","parent_id":1,"min_power":0,"level_index":2,"user_list":"{}","department_list":"{}","is_offline":0,"model_id":2,"is_had":0},{"id":44,"model_name":"公告管理","parent_id":1,"min_power":0,"level_index":2,"user_list":"{}","department_list":"{}","is_offline":0,"model_id":3,"is_had":0}]},{"id":45,"model_name":"考勤管理","parent_id":0,"min_power":0,"level_index":1,"user_list":"{}","department_list":"{}","is_offline":0,"model_id":4,"is_had":0,"children":[{"id":46,"model_name":"考勤打卡","parent_id":4,"min_power":0,"level_index":2,"user_list":"{}","department_list":"{}","is_offline":0,"model_id":5,"is_had":0}]},{"id":52,"model_name":"劳动关系","parent_id":0,"min_power":0,"level_index":1,"user_list":"{}","department_list":"{}","is_offline":0,"model_id":11,"is_had":0,"children":[{"id":53,"model_name":"招聘","parent_id":11,"min_power":0,"level_index":2,"user_list":"{}","department_list":"{}","is_offline":0,"model_id":12,"is_had":0}]}]
     * isAllModel : 0
     */

    private int isAllModel;
    private List<PowerListBean> powerList;

    public int getIsAllModel() {
        return isAllModel;
    }

    public void setIsAllModel(int isAllModel) {
        this.isAllModel = isAllModel;
    }

    public List<PowerListBean> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<PowerListBean> powerList) {
        this.powerList = powerList;
    }

    public static class PowerListBean {
        /**
         * id : 42
         * model_name : 管理控制台
         * parent_id : 0
         * min_power : 0
         * level_index : 1
         * user_list : {}
         * department_list : {}
         * is_offline : 0
         * model_id : 1
         * is_had : 0
         * children : [{"id":43,"model_name":"管理工作台","parent_id":1,"min_power":0,"level_index":2,"user_list":"{}","department_list":"{}","is_offline":0,"model_id":2,"is_had":0},{"id":44,"model_name":"公告管理","parent_id":1,"min_power":0,"level_index":2,"user_list":"{}","department_list":"{}","is_offline":0,"model_id":3,"is_had":0}]
         */

        private int id;
        private String model_name;
        private int parent_id;
        private int min_power;
        private int level_index;
        private String user_list;
        private String department_list;
        private int is_offline;
        private int model_id;
        private int is_had;
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

        public String getUser_list() {
            return user_list;
        }

        public void setUser_list(String user_list) {
            this.user_list = user_list;
        }

        public String getDepartment_list() {
            return department_list;
        }

        public void setDepartment_list(String department_list) {
            this.department_list = department_list;
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

        public int getIs_had() {
            return is_had;
        }

        public void setIs_had(int is_had) {
            this.is_had = is_had;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 43
             * model_name : 管理工作台
             * parent_id : 1
             * min_power : 0
             * level_index : 2
             * user_list : {}
             * department_list : {}
             * is_offline : 0
             * model_id : 2
             * is_had : 0
             */

            private int id;
            private String model_name;
            private int parent_id;
            private int min_power;
            private int level_index;
            private String user_list;
            private String department_list;
            private int is_offline;
            private int model_id;
            private int is_had;
            private String model_icon;

            public String getModel_icon() {
                return model_icon;
            }

            public void setModel_icon(String model_icon) {
                this.model_icon = model_icon;
            }

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

            public String getUser_list() {
                return user_list;
            }

            public void setUser_list(String user_list) {
                this.user_list = user_list;
            }

            public String getDepartment_list() {
                return department_list;
            }

            public void setDepartment_list(String department_list) {
                this.department_list = department_list;
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

            public int getIs_had() {
                return is_had;
            }

            public void setIs_had(int is_had) {
                this.is_had = is_had;
            }
        }
    }
}
