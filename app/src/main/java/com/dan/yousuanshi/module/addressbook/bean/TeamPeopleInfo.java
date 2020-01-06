package com.dan.yousuanshi.module.addressbook.bean;

import java.util.List;

public class TeamPeopleInfo {

    /**
     * id : 10
     * company_id : 3 企业ID
     * user_id : 3 用户ID
     * explain : 备注
     * join_time : 2019-12-11 加入时间
     * my_leader : 0 我的上级领导
     * department : 0
     * power_id : 0
     * is_creater : 0
     * office :职位
     * user_tel : 18888888888
     * real_name : 侯彬
     * departmentIdList : [{"group_id":7,"group_name":"人事部"},{"group_id":30,"group_name":"ios手机"},{"group_id":26,"group_name":"亚历山大部"}]
     */

    private int id;
    private int company_id;
    private int user_id;
    private String explain;
    private String join_time;
    private int my_leader;
    private int department;
    private int power_id;
    private int is_creater;
    private String office;
    private String user_tel;
    private String real_name;
    private List<DepartmentIdListBean> departmentIdList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getJoin_time() {
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
    }

    public int getMy_leader() {
        return my_leader;
    }

    public void setMy_leader(int my_leader) {
        this.my_leader = my_leader;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getPower_id() {
        return power_id;
    }

    public void setPower_id(int power_id) {
        this.power_id = power_id;
    }

    public int getIs_creater() {
        return is_creater;
    }

    public void setIs_creater(int is_creater) {
        this.is_creater = is_creater;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
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

    public List<DepartmentIdListBean> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<DepartmentIdListBean> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public static class DepartmentIdListBean {
        /**
         * group_id : 7
         * group_name : 人事部
         */

        private int group_id;
        private String group_name;

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }
    }
}
