package com.dan.yousuanshi.module.chat.bean;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;

public class FaceCreateGroupBean extends BaseBean {

    /**
     * faceId: "FACE_7777_1"
     * faceGroupId : 13
     * userList : [{"user_id":"3","avatar":"https://dzcdn.zixuezhilu.com/ac1f0d1505a291f0a9963762bd9186b1.jpg"}]
     */

    private String faceGroupId;
    private String faceId;
    private List<UserListBean> userList;

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getFaceGroupId() {
        return faceGroupId;
    }

    public void setFaceGroupId(String faceGroupId) {
        this.faceGroupId = faceGroupId;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
        /**
         * user_id : 3
         * avatar : https://dzcdn.zixuezhilu.com/ac1f0d1505a291f0a9963762bd9186b1.jpg
         */

        private String user_id;
        private String avatar;

        public UserListBean(String user_id, String avatar) {
            this.user_id = user_id;
            this.avatar = avatar;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
