package com.dan.yousuanshi.common;

public class Constant {
    /**
     * 请求地址
     */
    public static final String BASE_URL = "https://www.zixuezhilu.com:8767/";
    /**
     * 下载地址
     */
    public static final String DOWNLOAD_URL = "https://dzcdn.zixuezhilu.com/";
    /**
     * 接口版本
     */
    public static final String APIVERSION = "apiversion=100";
    /**
     * 国际化语言类型
     */
    public static final String GUOJIHUA = "lang=zh-cn";
    /**
     * 请求头字段名称
     */
    public static final String PUBLIC_REQUEST_HEADER = "AUTHORIZATION";
    /**
     * 聊天类型：文本
     */
    public static final int CHAT_TEXT = 1;
    /**
     * 聊天类型：语音
     */
    public static final int CHAT_VOICE = 2;
    /**
     * 聊天类型：文件
     */
    public static final int CHAT_FILE = 3;
    /**
     * 聊天类型：图片
     */
    public static final int CHAT_PIC = 4;
    /**
     * 聊天类型：视频
     */
    public static final int CHAT_VIDEO = 5;
    /**
     * 聊天类型：名片
     */
    public static final int CHAT_CARD = 6;
    /**
     * 聊天类型：位置
     */
    public static final int CHAT_LOCATION = 7;
    /**
     * 聊天类型：群提醒
     */
    public static final int CHAT_GROUP_NOTIFICATION = 8;
    /**
     * 聊天类型：给用户提示离开群聊
     */
    public static final int CHAT_EXIT_GROUP = 9;
    /**
     * 聊天类型：给用户提加入群聊
     */
    public static final int CHAT_ADD_GROUP = 10;
    /**
     * 聊天类型：好友同意你的请求
     */
    public static final int CHAT_FRIEND_AGREE_YOU = 12;
    /**
     * 聊天类型：面对面群聊加入
     */
    public static final int CHAT_FACE_CREATE_GROUP_JOIN = 13;
    /**
     * 聊天类型：面对面群聊退出
     */
    public static final int CHAT_FACE_CREATE_GROUP_EXIT = 14;
    /**
     * 聊天类型：邀请加入企业
     */
    public static final int INVITE_JOIN_TEAM = 15;
    /**
     * 七牛上传头：语音
     */
    public static final String QINIU_VOICE = "audioTemp/";
    /**
     * 七牛上传头：图片
     */
    public static final String QINIU_PIC = "picTemp/";
    /**
     * 七牛上传头：视频
     */
    public static final String QINIU_VIDEO = "videoTemp/";
    /**
     * 七牛上传头：视频收藏
     */
    public static final String QINIU_COLLECT_VIDEO = "videoCollection/";
    /**
     * 七牛上传头：文件
     */
    public static final String QINIU_FILE = "fileTemp/";
    /**
     * 七牛上传头：文件收藏
     */
    public static final String QINIU_COLLECT_FILE = "fileCollection/";
    /**
     * 七牛上传头：位置
     */
    public static final String QINIU_LOCATION = "locationTemp/";
    /**
     * 七牛上传头：头像
     */
    public static final String QINIU_AVATAR = "avatarTemp/";
    /**
     * 群类型：普通群
     */
    public static final int ORDINARY_GROUP = 0;
    /**
     * 群类型：全员群
     */
    public static final int ALL_PEOPLE_GROUP = 1;
    /**
     * 群类型：部门群
     */
    public static final int DEPARTMENT_GROUP = 2;
    /**
     * 群类型：内部群
     */
    public static final int INTERNAL_GROUP = 3;
    /**
     * 聊天类型：单聊
     */
    public static final int CHAT_ONE_TYPE = 2;
    /**
     * 聊天类型：群聊
     */
    public static final int CHAT_GROUP_TYPE = 3;
    /**
     * 发送失败消息id
     */
    public static final String SEND_ERROR_MSG_ID = "-1";
    /**
     * SP.是否发送消息通知 格式为IS_SEND_NOTIFICATION+type+id
     */
    public static final String IS_SEND_NOTIFICATION = "IS_SEND_NOTIFICATION";
    /**
     * SP.共享公司
     */
    public static final String SHARED_COMPANY = "SHARED_COMPANY";
}
