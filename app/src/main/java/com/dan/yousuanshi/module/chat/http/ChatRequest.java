package com.dan.yousuanshi.module.chat.http;

import androidx.annotation.NonNull;

import com.dan.yousuanshi.http.BaseRequest;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.http.YouSuanShiApi;
import com.dan.yousuanshi.module.addressbook.bean.TeamQrCodeBean;
import com.dan.yousuanshi.module.chat.bean.CreateGroupBean;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.chat.bean.FaceCreateGroupBean;
import com.dan.yousuanshi.module.chat.bean.GroupAnnouncementBean;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;
import com.dan.yousuanshi.module.chat.bean.JoinFaceCreateGroupBean;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.module.chat.bean.MyGroupInfoBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;
import com.dan.yousuanshi.module.chat.bean.SearchPeopleBean;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import per.goweii.rxhttp.request.base.BaseBean;

public class ChatRequest extends BaseRequest {
    public static Disposable getUserInfoById(Map<String,String> map, RequestListener<QueryUserBean> listener){
        return request(YouSuanShiApi.api().getUserInfoById(map),listener);
    }
    public static Disposable getQiniuToken(RequestListener<QiniuTokenBean> listener) {
        return request(YouSuanShiApi.api().getQiniuToken(), listener);
    }
    public static Disposable sendMessage(Map<String,String> map, @NonNull RequestListener<MessageId> listener) {
        return request(YouSuanShiApi.api().sendMessage(map), listener);
    }
    public static Disposable getDepartment(Map<String,String> map, @NonNull RequestListener<List<DepartmentBean>> listener) {
        return request(YouSuanShiApi.api().getDepartment(map), listener);
    }
    public static Disposable socketSuccess(RequestListener<List> listener) {
        return request(YouSuanShiApi.api().socketSuccess(), listener);
    }
    public static Disposable searchPeople(Map<String,String> map, @NonNull RequestListener<SearchPeopleBean> listener) {
        return request(YouSuanShiApi.api().searchPeople(map), listener);
    }
    public static Disposable createGroup(Map<String,String> map, @NonNull RequestListener<CreateGroupBean> listener) {
        return request(YouSuanShiApi.api().createGroup(map), listener);
    }
    public static Disposable submitGroupAnnouncement(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().submitGroupAnnouncement(map), listener);
    }
    public static Disposable getGroupInfo(Map<String,Object> map, @NonNull RequestListener<MyGroupInfoBean> listener) {
        return request(YouSuanShiApi.api().getGroupInfo(map), listener);
    }
    public static Disposable updateGroupInfo(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().updateGroupInfo(map), listener);
    }
    public static Disposable getGroupAnnouncement(Map<String,Object> map, @NonNull RequestListener<GroupAnnouncementBean> listener) {
        return request(YouSuanShiApi.api().getGroupAnnouncement(map), listener);
    }
    public static Disposable getGroupFile(Map<String,Object> map, @NonNull RequestListener<GroupFileBean> listener) {
        return request(YouSuanShiApi.api().getGroupFile(map), listener);
    }
    public static Disposable updateGroupAnnouncement(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().updateGroupAnnouncement(map), listener);
    }
    public static Disposable deleteAnnouncement(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().deleteAnnouncement(map), listener);
    }
    public static Disposable addGroupPeople(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().addGroupPeople(map), listener);
    }
    public static Disposable removeGroupPeople(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().removeGroupPeople(map), listener);
    }
    public static Disposable exitGroup(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().exitGroup(map), listener);
    }
    public static Disposable disbandGroup(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().disbandGroup(map), listener);
    }
    public static Disposable transferGroup(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().transferGroup(map), listener);
    }
    public static Disposable updateMyGroupNickName(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().updateMyGroupNickName(map), listener);
    }
    public static Disposable addGroupMaster(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().addGroupMaster(map), listener);
    }
    public static Disposable faceCreateGroup(Map<String,Object> map, @NonNull RequestListener<FaceCreateGroupBean> listener) {
        return request(YouSuanShiApi.api().faceCreateGroup(map), listener);
    }
    public static Disposable exitFaceCreateGroup(Map<String,Object> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().exitFaceCreateGroup(map), listener);
    }
    public static Disposable joinFaceCreateGroup(Map<String,Object> map, @NonNull RequestListener<JoinFaceCreateGroupBean> listener) {
        return request(YouSuanShiApi.api().joinFaceCreateGroup(map), listener);
    }
    public static Disposable getMyQrcode( @NonNull RequestListener<TeamQrCodeBean> listener) {
        return request(YouSuanShiApi.api().getMyQrcode(), listener);
    }
    public static Disposable addCollect(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().addCollect(map), listener);
    }
    public static Disposable deleteGroupFile(Map<String,Object> map,@NonNull RequestListener<BaseBean> listener) {
        return request(YouSuanShiApi.api().deleteGroupFile(map), listener);
    }
    public static Disposable renameGroupFile(Map<String,Object> map,@NonNull RequestListener<BaseBean> listener) {
        return request(YouSuanShiApi.api().renameGroupFile(map), listener);
    }
    public static Disposable addGroupFile(Map<String,Object> map,@NonNull RequestListener<BaseBean> listener) {
        return request(YouSuanShiApi.api().addGroupFile(map), listener);
    }
}
