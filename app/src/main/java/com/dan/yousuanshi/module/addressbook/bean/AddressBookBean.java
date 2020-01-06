package com.dan.yousuanshi.module.addressbook.bean;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public class AddressBookBean extends BaseBean {

    /**
     * newApplyList : 0 // 0,没有新申请列表,1,有
     * companyMaster : 1 //是否是公司管理员.如果不为0,则显示管理按钮
     * departmentMaster : 0 //是不是部门主管,如果不为0,则显示我的下属
     * departmentList : [{"did":140 //部门ID,"d_name":"技术部" //部门名称,"is_create_group":1,"gid":8 //群聊ID,如果没有群聊,则是0,可以根据这个判断是否显示 部门群按}]
     * otherTeam : 5  //其他团队
     * companyName : 傻屌公司11111222
     * companyId : 1 //企业ID,没有企业,则为0
     */

    private int newApplyList;
    private int companyMaster;
    private int departmentMaster;
    private int otherTeam;
    private String companyName;
    private int companyId;
    private String company_logo;
    private List<DepartmentListBean> departmentList;

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public int getNewApplyList() {
        return newApplyList;
    }

    public void setNewApplyList(int newApplyList) {
        this.newApplyList = newApplyList;
    }

    public int getCompanyMaster() {
        return companyMaster;
    }

    public void setCompanyMaster(int companyMaster) {
        this.companyMaster = companyMaster;
    }

    public int getDepartmentMaster() {
        return departmentMaster;
    }

    public void setDepartmentMaster(int departmentMaster) {
        this.departmentMaster = departmentMaster;
    }

    public int getOtherTeam() {
        return otherTeam;
    }

    public void setOtherTeam(int otherTeam) {
        this.otherTeam = otherTeam;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public List<DepartmentListBean> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<DepartmentListBean> departmentList) {
        this.departmentList = departmentList;
    }

    public static class DepartmentListBean {
        /**
         * did : 140
         * d_name : 技术部
         * stop_chat : 1 //是否开启群聊,0,开启,1,关闭,当为0时,显示聊天图标
         * parent_id : 8 //父部门ID,这里肯定为0
         */

        private int id;
        private String group_name;
        private int stop_chat;
        private int parent_id;

        public int getDid() {
            return id;
        }

        public void setDid(int did) {
            this.id = did;
        }

        public String getD_name() {
            return group_name;
        }

        public void setD_name(String d_name) {
            this.group_name = d_name;
        }

        public int getStop_chat() {
            return stop_chat;
        }

        public void setStop_chat(int stop_chat) {
            this.stop_chat = stop_chat;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }
    }
}
