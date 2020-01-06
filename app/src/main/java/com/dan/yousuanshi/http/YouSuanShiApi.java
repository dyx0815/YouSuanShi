package com.dan.yousuanshi.http;

import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.addressbook.bean.AddressBookBean;
import com.dan.yousuanshi.module.addressbook.bean.CountryBean;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentInfoBean;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;
import com.dan.yousuanshi.module.addressbook.bean.DiyApplicationSettingBean;
import com.dan.yousuanshi.module.addressbook.bean.IndustryBean;
import com.dan.yousuanshi.module.addressbook.bean.MyApplyRecordBean;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.addressbook.bean.MyGroupBean;
import com.dan.yousuanshi.module.addressbook.bean.NewFriendBean;
import com.dan.yousuanshi.module.addressbook.bean.PartPurviewBean;
import com.dan.yousuanshi.module.addressbook.bean.PeopleSizeBean;
import com.dan.yousuanshi.module.addressbook.bean.SearchFriendBean;
import com.dan.yousuanshi.module.addressbook.bean.SearchTeamBean;
import com.dan.yousuanshi.module.addressbook.bean.SettingTeamBean;
import com.dan.yousuanshi.module.addressbook.bean.SonAdminBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamApplyListBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamHomeBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamInfoBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamNewApplyBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleInfo;
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
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.LoginBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.mine.bean.BlackListBean;
import com.dan.yousuanshi.module.mine.bean.MyCollectBean;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;
import com.dan.yousuanshi.module.shared.bean.AnnouncementBean;
import com.dan.yousuanshi.module.shared.bean.AnnouncementLisBean;
import com.dan.yousuanshi.module.shared.bean.BannerBean;
import com.dan.yousuanshi.module.shared.bean.TemplateBean;
import com.dan.yousuanshi.module.shared.bean.WorkbenchBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import per.goweii.rxhttp.request.Api;
import per.goweii.rxhttp.request.base.BaseBean;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class YouSuanShiApi extends Api {

    public static ApiService api() {
        return api(ApiService.class);
    }

    public interface Code{
        int SUCCESS = 0;
        int ERROR = 1;
        int FAILED_NOT_LOGIN = -1001; //请先登录
    }

    public interface ApiService{
        @FormUrlEncoded
        @POST("apilogin"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<LoginBean>> login(@FieldMap Map<String,String> map);

        @POST("apigetuser"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<UserBean>> getUserInfo();

        @FormUrlEncoded
        @POST("apisendmsg"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<SendCodeBean>> sendCode(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("apiregister"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<LoginBean>> register(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("apicheckmsg"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<CheckCodeBean>> checkCode(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("apiforgetpwd"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<LoginBean>> forgetPwd(@FieldMap Map<String,String> map);

        @GET("chattable"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> chattable();

        @FormUrlEncoded
        @POST("getuserinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<QueryUserBean>> getUserInfoById(@FieldMap Map<String,String> map);

        @GET("getnewtoken"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<LoginBean>> getNewToken();

        @GET("getqiniutoken"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<QiniuTokenBean>> getQiniuToken();

        @GET("getindustry"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<IndustryBean>>> getIndustry();

        @GET("getpeople"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<PeopleSizeBean>>> getPeopleSize();

        @FormUrlEncoded
        @POST("getcity"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<CountryBean>>> getCity(@FieldMap Map<String,String> map);

        @GET("getfriendsendlist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<NewFriendBean>> getNewFriend();

        @FormUrlEncoded
        @POST("agreefriend"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> agreeFriend(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("clearfriendsend"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> clearNewFriend(@FieldMap Map<String,String> map);

        @GET("getgroupteamlist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<MyGroupBean>>> getMyGroup();

        @GET("getmyfriendlist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<MyFriendBean>>> getMyFriend();

        @FormUrlEncoded
        @POST("setmineinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> updateUserInfo(@FieldMap Map<String,String> map);

        @GET("setmobilestep1"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<SendCodeBean>> sendCode();

        @FormUrlEncoded
        @POST("setmobilestep2"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<CheckCodeBean>> updatePhoneCheckCode(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("setmobilestep3"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<SendCodeBean>> sendCode2(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("setmobilestep4"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> updatePhoneCheckCode2(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("getuserlistbysearch"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<SearchFriendBean>> searchFriendByPhone(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("sendaddfriends"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> sendAddFriend(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("createcompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> createTeam(@FieldMap Map<String,String> map);

        @GET("addressbook"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<AddressBookBean>> addressBook();

        @GET("getmyallcompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<MyTeamBean>>> getMyTeam();

        @FormUrlEncoded
        @POST("getmyallcompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<MyTeamBean>>> getMyTeam(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("sendchatmsg"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<MessageId>> sendMessage(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("departmentlist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<DepartmentBean>>> getDepartment(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("deleteFriend"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> deleteFriend(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("changefriendnickname"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> updateRemark(@FieldMap Map<String,String> map);

        @GET("socketSuccess"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> socketSuccess();

        @FormUrlEncoded
        @POST("feedback"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> feedback(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("searchUserFromMyAllCompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<SearchPeopleBean>> searchPeople(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("searchcompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<SearchTeamBean>> searchTeam(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("applyjoingroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> applyJoinTeam(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("leavecompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> exitTeam(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("changemymaincompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> setFirstTeam(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("creategroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<CreateGroupBean>> createGroup(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("registerfinishsetinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> writeData(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("companyinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<TeamHomeBean>> teamHome(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("getnewapplylist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<TeamNewApplyBean>> getNewApply(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("agreereplyinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> agreeApply(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("clearalllist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> clearApply(@FieldMap Map<String,String> map);

        @FormUrlEncoded
        @POST("getMyApplyList"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<MyApplyRecordBean>> getApplyRecord(@FieldMap Map<String,Object> map);

        @POST("clearmyapplyalllist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> clearApplyRecord();

        @FormUrlEncoded
        @POST("setpassword"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> setPassword(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getmyblacklist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BlackListBean>> getBlackList(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("putuserintoblack"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> addBlackList(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("putuseroutblack"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> removeBlackList(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("createdepartment"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> addDepartment(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("changecompanyinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> updateTeamInfo(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("creategrouptitle"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> submitGroupAnnouncement(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getgroupinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<MyGroupInfoBean>> getGroupInfo(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("changegroupmsg"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> updateGroupInfo(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("gettitlelist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<GroupAnnouncementBean>> getGroupAnnouncement(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getgroupfilelist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<GroupFileBean>> getGroupFile(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("editgrouptitle"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> updateGroupAnnouncement(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("delgrouptitle"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> deleteAnnouncement(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("joinuserinfogroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> addGroupPeople(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("kickuserfromgroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> removeGroupPeople(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("leavegroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> exitGroup(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("disbandGroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> disbandGroup(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("transferGroupCreater"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> transferGroup(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("setmynicknamebygroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> updateMyGroupNickName(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("changeGroupMaster"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> addGroupMaster(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("facetofacecreategroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<FaceCreateGroupBean>> faceCreateGroup(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("leaveFaceGroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> exitFaceCreateGroup(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("faceJoinGroup"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<JoinFaceCreateGroupBean>> joinFaceCreateGroup(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getmycompanyuser"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<TeamPeopleBean>>> getTeamPeople(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getdepartmentuserlist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<DepartmentPeopleBean>>> getDepartmentPeople(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("editdepartment"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> updateDepartment(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getdepartmentinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<DepartmentInfoBean>> departmentInfo(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("disbandDepartment"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> deleteDepartment(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("leaveuserfromcompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> deleteTeamPeople(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("addpersonjoindepartment"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> addPeopleToDepartment(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getcompanyuserinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<TeamPeopleInfo>> teamPeopleInfo(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("invitefriendjoincompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> addTeamPeople(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("editpersonnel"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> editTeamPeople(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getcompanyinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<TeamInfoBean>> getTeamInfo(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("setcompany"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<SettingTeamBean>> settingTeam(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getdiymessage"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<DiyApplicationSettingBean>>> getDiyApplicationSetting(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("addreplyfrom"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> settingApplication(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("changemastersendmsg"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<SendCodeBean>> getCode(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("changemasterstep1"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<CheckCodeBean>> updateAdminCheckCode(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("changemasterstep2"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> updateAdmin(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getqrcodemessage"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<TeamQrCodeBean>> getTeamQrcode(@FieldMap Map<String,Object> map);

        @POST("getuserqrcodemessage"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<TeamQrCodeBean>> getMyQrcode();

        @FormUrlEncoded
        @POST("getcollectionlist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<MyCollectBean>> getMyCollect(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("addcollection"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> addCollect(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("delcollection"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List>> deleteCollect(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getthecompanyinfo"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<TeamApplyListBean>> getTeamApplyList(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("delgroupfile"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> deleteGroupFile(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("renameGroupFile"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> renameGroupFile(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("addgroupfile"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> addGroupFile(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getallmaster"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<SonAdminBean>>> getSonAdmin(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("setmasterpower"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> addSonAdmin(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getcompanymasterpower"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<PartPurviewBean>> getPartPurview(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("delcompanymaster"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> removeSonAdmin(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getnewaffiche"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<AnnouncementBean>> getNewAnnouncement(@Field("companyId") int companyId);

        @FormUrlEncoded
        @POST("getworkbench"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<WorkbenchBean>> getWorkbench(@Field("companyId") int companyId);

        @FormUrlEncoded
        @POST("getcompanybanner"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<BannerBean>>> getBanner(@Field("companyId") int companyId);

        @FormUrlEncoded
        @POST("getmodellist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<AddWorkBenchBean>>> getModelList(@Field("companyId") int companyId);

        @FormUrlEncoded
        @POST("setcompanymodel"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> setCompanyModel(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("setcompanymodelonline"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> onLineModel(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getaffichelist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<AnnouncementLisBean>> getAnnouncement(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getcompanymodellist"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<AddWorkBenchBean>>> getWorkbenchModel(@Field("companyId") int companyId);

        @FormUrlEncoded
        @POST("addAffiche"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> addAnnouncement(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("editaffiche"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> updateAnnouncement(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("delaffiche"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> deleteSharedAnnouncement(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("setcompanymodel2offline"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> offLineModel(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("delmodelmenulevel1"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> deleteWorkBench(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("renamecompanymodel"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> updateModelName(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("addcompanymodel"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> addModel(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("ranknumset"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<BaseBean>> sortModel(@FieldMap Map<String,Object> map);

        @FormUrlEncoded
        @POST("getTemplateList"+"?"+Constant.GUOJIHUA+"&"+Constant.APIVERSION)
        Observable<ResponseBean<List<TemplateBean>>> getTemplate(@Field("companyId") int companyId);
    }
}
