package com.dan.yousuanshi.module.chat.bean;

public class QueryUserBean{

    /**
     * user_id : 12 //用户ID
     * user_portrait : http://39.98.186.118/reckoner//Public/admin/images/user_img.png //头像
     * nick_name : null //昵称:实际情况下,此处肯定不为空
     * is_friend : 1 //是否是好友,1,是,0,否
     * user_department : 112 /部门
     * user_position : 厂领导 /职位
     * user_name : 张富畋 //真实姓名
     * user_tel : 18092232641 //手机号
     * same_company : 1 //是否是同一家公司,1,是,0,否
     */

    private int user_id;
    private String user_portrait;
    private String nick_name = "未设置";
    private int is_friend;
    private String user_department;
    private String user_position;
    private String user_name;
    private String user_tel;
    private int same_company;
    private String real_name;
    private int black_id; //1,黑名单,0,正常

    public int getBlack_id() {
        return black_id;
    }

    public void setBlack_id(int black_id) {
        this.black_id = black_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_portrait() {
        return user_portrait;
    }

    public void setUser_portrait(String user_portrait) {
        this.user_portrait = user_portrait;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }

    public String getUser_department() {
        return user_department;
    }

    public void setUser_department(String user_department) {
        this.user_department = user_department;
    }

    public String getUser_position() {
        return user_position;
    }

    public void setUser_position(String user_position) {
        this.user_position = user_position;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public int getSame_company() {
        return same_company;
    }

    public void setSame_company(int same_company) {
        this.same_company = same_company;
    }
}
