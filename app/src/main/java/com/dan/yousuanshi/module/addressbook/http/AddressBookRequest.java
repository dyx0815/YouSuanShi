package com.dan.yousuanshi.module.addressbook.http;

import androidx.annotation.NonNull;

import com.dan.yousuanshi.http.BaseRequest;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.http.YouSuanShiApi;
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
import com.dan.yousuanshi.module.chat.bean.QueryUserBean;
import com.dan.yousuanshi.module.login.bean.CheckCodeBean;
import com.dan.yousuanshi.module.login.bean.SendCodeBean;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import per.goweii.rxhttp.request.base.BaseBean;

public class AddressBookRequest extends BaseRequest {
    public static Disposable getIndustry(@NonNull RequestListener<List<IndustryBean>> listener) {
        return request(YouSuanShiApi.api().getIndustry(), listener);
    }

    public static Disposable getPeopleSize(@NonNull RequestListener<List<PeopleSizeBean>> listener) {
        return request(YouSuanShiApi.api().getPeopleSize(), listener);
    }

    public static Disposable getCity(Map<String,String> map, @NonNull RequestListener<List<CountryBean>> listener) {
        return request(YouSuanShiApi.api().getCity(map), listener);
    }

    public static Disposable getNewFriend( @NonNull RequestListener<NewFriendBean> listener) {
        return request(YouSuanShiApi.api().getNewFriend(), listener);
    }

    public static Disposable agreeFriend(Map<String,String> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().agreeFriend(map), listener);
    }

    public static Disposable clearNewFriend(Map<String,String> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().clearNewFriend(map), listener);
    }

    public static Disposable getMyGroup(@NonNull RequestListener<List<MyGroupBean>> listener) {
        return request(YouSuanShiApi.api().getMyGroup(), listener);
    }

    public static Disposable getMyFriend(@NonNull RequestListener<List<MyFriendBean>> listener) {
        return request(YouSuanShiApi.api().getMyFriend(), listener);
    }

    public static Disposable searchFriendByPhone(Map<String,String> map, @NonNull RequestListener<SearchFriendBean> listener) {
        return request(YouSuanShiApi.api().searchFriendByPhone(map), listener);
    }

    public static Disposable getUserInfoById(Map<String,String> map, @NonNull RequestListener<QueryUserBean> listener) {
        return request(YouSuanShiApi.api().getUserInfoById(map), listener);
    }

    public static Disposable sendAddFriend(Map<String,String> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().sendAddFriend(map), listener);
    }

    public static Disposable createTeam(Map<String,String> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().createTeam(map), listener);
    }

    public static Disposable addressBook(@NonNull RequestListener<AddressBookBean> listener) {
        return request(YouSuanShiApi.api().addressBook(), listener);
    }

    public static Disposable deleteFriend(Map<String,String> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().deleteFriend(map), listener);
    }

    public static Disposable updateRemark(Map<String,String> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().updateRemark(map), listener);
    }

    public static Disposable searchTeam(Map<String,String> map, @NonNull RequestListener<SearchTeamBean> listener) {
        return request(YouSuanShiApi.api().searchTeam(map), listener);
    }

    public static Disposable applyJoinTeam(Map<String,String> map, @NonNull RequestListener<BaseBean> listener) {
        return request(YouSuanShiApi.api().applyJoinTeam(map), listener);
    }

    public static Disposable exitTeam(Map<String,String> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().exitTeam(map), listener);
    }

    public static Disposable teamHome(Map<String,String> map, @NonNull RequestListener<TeamHomeBean> listener) {
        return request(YouSuanShiApi.api().teamHome(map), listener);
    }

    public static Disposable getNewApply(Map<String,String> map, @NonNull RequestListener<TeamNewApplyBean> listener) {
        return request(YouSuanShiApi.api().getNewApply(map), listener);
    }
    public static Disposable agreeApply(Map<String,String> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().agreeApply(map), listener);
    }
    public static Disposable clearApply(Map<String,String> map, @NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().clearApply(map), listener);
    }

    public static Disposable getMyApplyRecord(Map<String,Object> map,@NonNull RequestListener<MyApplyRecordBean> listener) {
        return request(YouSuanShiApi.api().getApplyRecord(map), listener);
    }

    public static Disposable getMyApplyRecord(@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().clearApplyRecord(), listener);
    }

    public static Disposable addBlackList(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().addBlackList(map), listener);
    }

    public static Disposable removeBlackList(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().removeBlackList(map), listener);
    }

    public static Disposable addDepartment(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().addDepartment(map), listener);
    }

    public static Disposable updateTeamInfo(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().updateTeamInfo(map), listener);
    }
    public static Disposable getTeamPeople(Map<String,Object> map,@NonNull RequestListener<List<TeamPeopleBean>> listener) {
        return request(YouSuanShiApi.api().getTeamPeople(map), listener);
    }
    public static Disposable getDepartmentPeople(Map<String,Object> map,@NonNull RequestListener<List<DepartmentPeopleBean>> listener) {
        return request(YouSuanShiApi.api().getDepartmentPeople(map), listener);
    }
    public static Disposable updateDepartment(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().updateDepartment(map), listener);
    }
    public static Disposable departmentInfo(Map<String,Object> map,@NonNull RequestListener<DepartmentInfoBean> listener) {
        return request(YouSuanShiApi.api().departmentInfo(map), listener);
    }
    public static Disposable deleteDepartment(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().deleteDepartment(map), listener);
    }
    public static Disposable deleteTeamPeople(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().deleteTeamPeople(map), listener);
    }
    public static Disposable addPeopleToDepartment(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().addPeopleToDepartment(map), listener);
    }
    public static Disposable teamPeopleInfo(Map<String,Object> map,@NonNull RequestListener<TeamPeopleInfo> listener) {
        return request(YouSuanShiApi.api().teamPeopleInfo(map), listener);
    }
    public static Disposable addTeamPeople(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().addTeamPeople(map), listener);
    }
    public static Disposable editTeamPeople(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().editTeamPeople(map), listener);
    }
    public static Disposable getTeamInfo(Map<String,Object> map,@NonNull RequestListener<TeamInfoBean> listener) {
        return request(YouSuanShiApi.api().getTeamInfo(map), listener);
    }
    public static Disposable settingTeam(Map<String,Object> map,@NonNull RequestListener<SettingTeamBean> listener) {
        return request(YouSuanShiApi.api().settingTeam(map), listener);
    }
    public static Disposable getDiyApplicationSetting(Map<String,Object> map,@NonNull RequestListener<List<DiyApplicationSettingBean>> listener) {
        return request(YouSuanShiApi.api().getDiyApplicationSetting(map), listener);
    }
    public static Disposable settingApplication(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().settingApplication(map), listener);
    }
    public static Disposable getCode(Map<String,Object> map,@NonNull RequestListener<SendCodeBean> listener) {
        return request(YouSuanShiApi.api().getCode(map), listener);
    }
    public static Disposable updateAdminCheckCode(Map<String,Object> map,@NonNull RequestListener<CheckCodeBean> listener) {
        return request(YouSuanShiApi.api().updateAdminCheckCode(map), listener);
    }
    public static Disposable updateAdmin(Map<String,Object> map,@NonNull RequestListener<List> listener) {
        return request(YouSuanShiApi.api().updateAdmin(map), listener);
    }
    public static Disposable getTeamQrcode(Map<String,Object> map,@NonNull RequestListener<TeamQrCodeBean> listener) {
        return request(YouSuanShiApi.api().getTeamQrcode(map), listener);
    }
    public static Disposable getTeamApplyList(Map<String,Object> map,@NonNull RequestListener<TeamApplyListBean> listener) {
        return request(YouSuanShiApi.api().getTeamApplyList(map), listener);
    }
    public static Disposable getSonAdmin(Map<String,Object> map,@NonNull RequestListener<List<SonAdminBean>> listener) {
        return request(YouSuanShiApi.api().getSonAdmin(map), listener);
    }
    public static Disposable addSonAdmin(Map<String,Object> map,@NonNull RequestListener<BaseBean> listener) {
        return request(YouSuanShiApi.api().addSonAdmin(map), listener);
    }
    public static Disposable getPartPurview(Map<String,Object> map,@NonNull RequestListener<PartPurviewBean> listener) {
        return request(YouSuanShiApi.api().getPartPurview(map), listener);
    }
    public static Disposable removeSonAdmin(Map<String,Object> map,@NonNull RequestListener<BaseBean> listener) {
        return request(YouSuanShiApi.api().removeSonAdmin(map), listener);
    }
}
