package com.dan.yousuanshi.module.chat.bean;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public class MyGroupInfoBean extends BaseBean {

    /**
     * groupInfo : {"id":19,"group_avatar":"https://dzcdn.zixuezhilu.com/groupavatar/14.jpg","group_name":"百度","group_creater":10,"group_type":1,"group_company":15}
     * groupUser : [{"user_portrait":"https://dzcdn.zixuezhilu.com/fa63bd960e37cce6e85ac0e609f07e67.jpg","user_id":11,"real_name":"就","is_creater":0,"is_master":0},{"user_portrait":"https://dzcdn.zixuezhilu.com/7b7aafb1c4737e89d427f12444b5280c.jpg","user_id":12,"real_name":"dan","is_creater":0,"is_master":0},{"user_portrait":"https://dzcdn.zixuezhilu.com/ac1f0d1505a291f0a9963762bd9186b1.jpg","user_id":13,"real_name":"13888888888","is_creater":0,"is_master":0},{"user_portrait":"https://dzcdn.zixuezhilu.com/ac1f0d1505a291f0a9963762bd9186b1.jpg","user_id":15,"real_name":"12345678913","is_creater":0,"is_master":0},{"user_portrait":"https://dzcdn.zixuezhilu.com/dzys20191205145519image.jpg","user_id":10,"real_name":"东周科技","is_creater":1,"is_master":0}]
     */

    private GroupInfoBean groupInfo;
    private List<GroupUserBean> groupUser;

    public GroupInfoBean getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfoBean groupInfo) {
        this.groupInfo = groupInfo;
    }

    public List<GroupUserBean> getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(List<GroupUserBean> groupUser) {
        this.groupUser = groupUser;
    }

    public static class GroupInfoBean {
        /**
         * id : 19
         * group_avatar : https://dzcdn.zixuezhilu.com/groupavatar/14.jpg
         * group_name : 百度
         * group_creater : 10
         * group_type : 1
         * group_company : 15
         */

        private int id;
        private String group_avatar;
        private String group_name;
        private int group_creater;
        private int group_type;
        private int group_company;
        private String group_company_name;
        private String group_company_logo;

        public String getGroup_company_name() {
            return group_company_name;
        }

        public void setGroup_company_name(String group_company_name) {
            this.group_company_name = group_company_name;
        }

        public String getGroup_company_logo() {
            return group_company_logo;
        }

        public void setGroup_company_logo(String group_company_logo) {
            this.group_company_logo = group_company_logo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGroup_avatar() {
            return group_avatar;
        }

        public void setGroup_avatar(String group_avatar) {
            this.group_avatar = group_avatar;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public int getGroup_creater() {
            return group_creater;
        }

        public void setGroup_creater(int group_creater) {
            this.group_creater = group_creater;
        }

        public int getGroup_type() {
            return group_type;
        }

        public void setGroup_type(int group_type) {
            this.group_type = group_type;
        }

        public int getGroup_company() {
            return group_company;
        }

        public void setGroup_company(int group_company) {
            this.group_company = group_company;
        }
    }

    public static class GroupUserBean {
        /**
         * user_portrait : https://dzcdn.zixuezhilu.com/fa63bd960e37cce6e85ac0e609f07e67.jpg
         * user_id : 11
         * real_name : 就
         * is_creater : 0
         * is_master : 0
         */

        private String user_portrait;
        private int user_id;
        private String real_name;
        private int is_creater; //1为群主
        private int is_master; //1为管理

        public String getUser_portrait() {
            return user_portrait;
        }

        public void setUser_portrait(String user_portrait) {
            this.user_portrait = user_portrait;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
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
    }
}
