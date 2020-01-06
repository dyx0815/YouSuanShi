package com.dan.yousuanshi.module.shared.bean;

import java.util.List;

public class TemplateBean {

    /**
     * id : 64
     * model_name : 考勤管理
     * company_parent_id : 0
     * is_template : 1
     * level_index : 1
     * template_icon :
     * children : [{"id":65,"model_name":"考勤打卡","company_parent_id":64,"is_template":1,"model_id":1,"template_icon":"","level_index":2,"children":[]}]
     */

    private int id;
    private String model_name;
    private int company_parent_id;
    private int is_template;
    private int level_index;
    private String template_icon;
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

    public int getCompany_parent_id() {
        return company_parent_id;
    }

    public void setCompany_parent_id(int company_parent_id) {
        this.company_parent_id = company_parent_id;
    }

    public int getIs_template() {
        return is_template;
    }

    public void setIs_template(int is_template) {
        this.is_template = is_template;
    }

    public int getLevel_index() {
        return level_index;
    }

    public void setLevel_index(int level_index) {
        this.level_index = level_index;
    }

    public String getTemplate_icon() {
        return template_icon;
    }

    public void setTemplate_icon(String template_icon) {
        this.template_icon = template_icon;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 65
         * model_name : 考勤打卡
         * company_parent_id : 64
         * is_template : 1
         * model_id : 1
         * template_icon :
         * level_index : 2
         * children : []
         */

        private int id;
        private String model_name;
        private int company_parent_id;
        private int is_template;
        private int model_id;
        private String template_icon;
        private int level_index;
        private List<?> children;

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

        public int getCompany_parent_id() {
            return company_parent_id;
        }

        public void setCompany_parent_id(int company_parent_id) {
            this.company_parent_id = company_parent_id;
        }

        public int getIs_template() {
            return is_template;
        }

        public void setIs_template(int is_template) {
            this.is_template = is_template;
        }

        public int getModel_id() {
            return model_id;
        }

        public void setModel_id(int model_id) {
            this.model_id = model_id;
        }

        public String getTemplate_icon() {
            return template_icon;
        }

        public void setTemplate_icon(String template_icon) {
            this.template_icon = template_icon;
        }

        public int getLevel_index() {
            return level_index;
        }

        public void setLevel_index(int level_index) {
            this.level_index = level_index;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }
    }
}
