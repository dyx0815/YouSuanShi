package com.dan.yousuanshi.module.shared.bean;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchBean {


    /**
     * is_show_add : 1
     * modelList : [{"id":234,"model_name":"管理控制台","parent_id":0,"company_parent_id":0,"min_power":1,"level_index":1,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":1,"model_icon":"","is_master":1,"children":[{"id":235,"model_name":"管理工作台","parent_id":1,"company_parent_id":234,"min_power":1,"level_index":2,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":2,"model_icon":"","is_master":1,"children":[]},{"id":236,"model_name":"公告管理","parent_id":1,"company_parent_id":234,"min_power":1,"level_index":2,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":3,"model_icon":"","is_master":1,"children":[]}]},{"id":254,"model_name":"不爱了","parent_id":0,"company_parent_id":0,"min_power":0,"level_index":1,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":0,"model_icon":"","is_master":0,"children":[{"id":247,"model_name":"外出","parent_id":4,"company_parent_id":254,"min_power":0,"level_index":2,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":10,"model_icon":"","is_master":0,"children":[]},{"id":238,"model_name":"考勤打卡","parent_id":4,"company_parent_id":254,"min_power":0,"level_index":2,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":5,"model_icon":"","is_master":0,"children":[]}]},{"id":258,"model_name":"考勤管理","parent_id":0,"company_parent_id":0,"min_power":0,"level_index":1,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":0,"model_icon":"","is_master":0,"children":[{"id":259,"model_name":"打卡","parent_id":5,"company_parent_id":258,"min_power":0,"level_index":2,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":17,"model_icon":"","is_master":0,"children":[]}]}]
     * is_creater : 1
     * is_master : 1
     */

    private int is_show_add;
    private int is_creater;
    private int is_master;
    private List<ModelListBean> modelList;

    public int getIs_show_add() {
        return is_show_add;
    }

    public void setIs_show_add(int is_show_add) {
        this.is_show_add = is_show_add;
    }

    public int getIs_creater() {
        return is_creater;
    }

    public void setIs_creater(int is_creater) {
        this.is_creater = is_creater;
    }

    public int getIs_master() {
        return is_master;
    }

    public void setIs_master(int is_master) {
        this.is_master = is_master;
    }

    public List<ModelListBean> getModelList() {
        return modelList;
    }

    public void setModelList(List<ModelListBean> modelList) {
        this.modelList = modelList;
    }

    public static class     ModelListBean {
        /**
         * id : 234
         * model_name : 管理控制台
         * parent_id : 0
         * company_parent_id : 0
         * min_power : 1
         * level_index : 1
         * user_list : []
         * department_list : []
         * is_offline : 0
         * model_id : 1
         * model_icon :
         * is_master : 1
         * children : [{"id":235,"model_name":"管理工作台","parent_id":1,"company_parent_id":234,"min_power":1,"level_index":2,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":2,"model_icon":"","is_master":1,"children":[]},{"id":236,"model_name":"公告管理","parent_id":1,"company_parent_id":234,"min_power":1,"level_index":2,"user_list":"[]","department_list":"[]","is_offline":0,"model_id":3,"model_icon":"","is_master":1,"children":[]}]
         */

        private int id;
        private String model_name;
        private int parent_id;
        private int company_parent_id;
        private int min_power;
        private int level_index;
        private String user_list;
        private String department_list;
        private int is_offline;
        private int model_id;
        private String model_icon;
        private int is_master;
        private List<ChildrenBean> children;

        public ModelListBean(String model_name) {
            this.model_name = model_name;
            children = new ArrayList<>();
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

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 235
             * model_name : 管理工作台
             * parent_id : 1
             * company_parent_id : 234
             * min_power : 1
             * level_index : 2
             * user_list : []
             * department_list : []
             * is_offline : 0
             * model_id : 2
             * model_icon :
             * is_master : 1
             * children : []
             */

            private int id;
            private String model_name;
            private int parent_id;
            private int company_parent_id;
            private int min_power;
            private int level_index;
            private String user_list;
            private String department_list;
            private int is_offline;
            private int model_id;
            private String model_icon;
            private int is_master;
            private List<?> children;

            public ChildrenBean(int model_id,String model_name, int parent_id, String model_icon) {
                this.model_id = model_id;
                this.model_name = model_name;
                this.parent_id = parent_id;
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

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }
        }
    }
}
