package com.dan.yousuanshi.module.addressbook.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseFragment;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.module.addressbook.activity.AddFriendActivity;
import com.dan.yousuanshi.module.addressbook.activity.CreateTeamActivity;
import com.dan.yousuanshi.module.addressbook.activity.MyApplyRecordActivity;
import com.dan.yousuanshi.module.addressbook.activity.MyFriendActivity;
import com.dan.yousuanshi.module.addressbook.activity.MyGroupActivity;
import com.dan.yousuanshi.module.addressbook.activity.NewFriendActivity;
import com.dan.yousuanshi.module.addressbook.activity.OtherTeamActivity;
import com.dan.yousuanshi.module.addressbook.activity.TeamHomeActivity;
import com.dan.yousuanshi.module.addressbook.activity.TeamManagerActivity;
import com.dan.yousuanshi.module.addressbook.activity.TeamNewPeopleActivity;
import com.dan.yousuanshi.module.addressbook.activity.TeamStructureActivity;
import com.dan.yousuanshi.module.addressbook.adapter.RecentFriendAdapter;
import com.dan.yousuanshi.module.addressbook.adapter.TeamStructureAdapter;
import com.dan.yousuanshi.module.addressbook.bean.AddressBookBean;
import com.dan.yousuanshi.module.addressbook.presenter.AddressBookFragmentPresenter;
import com.dan.yousuanshi.module.addressbook.view.AddressBookFragmentView;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.utils.MyHanlder;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressBookFragment extends BaseFragment<AddressBookFragmentPresenter> implements AddressBookFragmentView {

    List<ChatBean> chatList;
    @BindView(R.id.ll_add_friend)
    LinearLayout llAddFriend;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_create_team)
    LinearLayout llCreateTeam;
    @BindView(R.id.ll_my_group)
    LinearLayout llMyGroup;
    @BindView(R.id.ll_new_friend)
    LinearLayout llNewFriend;
    @BindView(R.id.ll_my_friend)
    LinearLayout llMyFriend;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_recent_people)
    LinearLayout llRecentPeople;
    @BindView(R.id.rv_team_structure)
    RecyclerView rvTeamStructure;
    @BindView(R.id.tv_look)
    TextView tvLook;
    @BindView(R.id.v_util)
    View vUtil;
    @BindView(R.id.rl_team)
    RelativeLayout rlTeam;
    @BindView(R.id.iv_team_icon)
    RoundedImageView ivTeamIcon;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.iv_team_proof)
    ImageView ivTeamProof;
    @BindView(R.id.tv_first_team)
    TextView tvFirstTeam;
    @BindView(R.id.iv_team_structure)
    ImageView ivTeamStructure;
    @BindView(R.id.tv_team_structure_title)
    TextView tvTeamStructureTitle;
    @BindView(R.id.rl_team_structure)
    RelativeLayout rlTeamStructure;
    @BindView(R.id.iv_my_subordinate)
    ImageView ivMySubordinate;
    @BindView(R.id.tv_my_subordinate_title)
    TextView tvMySubordinateTitle;
    @BindView(R.id.rl_my_subordinate)
    RelativeLayout rlMySubordinate;
    @BindView(R.id.iv_team_home)
    ImageView ivTeamHome;
    @BindView(R.id.tv_team_home_title)
    TextView tvTeamHomeTitle;
    @BindView(R.id.rl_team_home)
    RelativeLayout rlTeamHome;
    @BindView(R.id.iv_other_team)
    ImageView ivOtherTeam;
    @BindView(R.id.tv_other_team_title)
    TextView tvOtherTeamTitle;
    @BindView(R.id.rl_other_team)
    RelativeLayout rlOtherTeam;
    @BindView(R.id.iv_new_people)
    ImageView ivNewPeople;
    @BindView(R.id.tv_new_people_title)
    TextView tvNewPeopleTitle;
    @BindView(R.id.rl_new_people)
    RelativeLayout rlNewPeople;
    @BindView(R.id.iv_request_record)
    ImageView ivRequestRecord;
    @BindView(R.id.tv_request_record_title)
    TextView tvRequestRecordTitle;
    @BindView(R.id.rl_request_record)
    RelativeLayout rlRequestRecord;
    @BindView(R.id.iv_master)
    ImageView ivMaster;

    private int uId;
    private TeamStructureAdapter adapter;
    private AddressBookBean addressBookBean = new AddressBookBean();
    private List<AddressBookBean.DepartmentListBean> departmentList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address_book;
    }

    @Nullable
    @Override
    protected AddressBookFragmentPresenter initPresenter() {
        return new AddressBookFragmentPresenter();
    }

    @Override
    protected void initView() {
        rvTeamStructure.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TeamStructureAdapter(getActivity(), departmentList);
        rvTeamStructure.setAdapter(adapter);
        if (StringUtils.isEmpty(UserUtils.getInstance().getUserBean().getC_name())) {
            vUtil.setVisibility(View.VISIBLE);
            llCreateTeam.setVisibility(View.VISIBLE);
            rlTeam.setVisibility(View.GONE);
        } else {
            vUtil.setVisibility(View.GONE);
            llCreateTeam.setVisibility(View.GONE);
            tvTeamName.setText(UserUtils.getInstance().getUserBean().getC_name());
            Glide.with(this).load(UserUtils.getInstance().getUserBean().getCompany_logo()).into(ivTeamIcon);
            if (addressBookBean.getNewApplyList() == 0) {
                rlNewPeople.setVisibility(View.GONE);
            } else {
                rlNewPeople.setVisibility(View.VISIBLE);
            }
            if (addressBookBean.getCompanyMaster() == 0) {
                ivMaster.setVisibility(View.GONE);
            } else {
                ivMaster.setVisibility(View.VISIBLE);
            }
            if (addressBookBean.getDepartmentMaster() == 0) {
                rlMySubordinate.setVisibility(View.GONE);
            } else {
                rlMySubordinate.setVisibility(View.VISIBLE);
            }
            tvOtherTeamTitle.setText("其他团队（" + addressBookBean.getOtherTeam() + "）");
            rlTeam.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void loadData() {
        uId = UserUtils.getInstance().getUserBean().getUser_id();
        if (DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getChatListTableName(uId))) {
            chatList = DataBaseHelper.queryChatList(getActivity(), uId);
            Iterator<ChatBean> iterator = chatList.iterator();
            while (iterator.hasNext()) {
                ChatBean chatBean = iterator.next();
                if (chatBean.getType() != Constant.CHAT_ONE_TYPE) {
                    iterator.remove();
                }
            }
            if (chatList.size() > 0) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(new RecentFriendAdapter(getActivity(), chatList));
                llRecentPeople.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.addressBook();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MyHanlder.getInstance().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StatusBarUtil.setColor(getActivity(), getActivity().getColor(R.color.white), 0);
                    if (!StringUtils.isEmpty(UserUtils.getInstance().getUserBean().getC_name())) {
                        presenter.addressBook();
                    }
                }
            }, 1);
        }
    }

    @OnClick({R.id.ll_add_friend, R.id.ll_search, R.id.ll_create_team, R.id.ll_my_group, R.id.ll_new_friend, R.id.ll_my_friend, R.id.tv_look
            , R.id.rl_team_structure, R.id.rl_team_home, R.id.rl_other_team, R.id.rl_new_people, R.id.rl_request_record, R.id.iv_master})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add_friend://右上角添加图标
                startActivity(new Intent(getActivity(), AddFriendActivity.class));
                break;
            case R.id.ll_search:
                break;
            case R.id.ll_create_team://创建团队
                startActivity(new Intent(getActivity(), CreateTeamActivity.class));
                break;
            case R.id.ll_my_group://我的群组
                startActivity(new Intent(getActivity(), MyGroupActivity.class));
                break;
            case R.id.ll_new_friend://新的好友
                startActivity(new Intent(getActivity(), NewFriendActivity.class));
                break;
            case R.id.ll_my_friend://我的好友
                startActivity(new Intent(getActivity(), MyFriendActivity.class));
                break;
            case R.id.tv_look:
                if (rvTeamStructure.getVisibility() == View.VISIBLE) {
                    rvTeamStructure.setVisibility(View.GONE);
                    tvLook.setText("展开");
                } else {
                    adapter.notifyDataSetChanged();
                    rvTeamStructure.setVisibility(View.VISIBLE);
                    tvLook.setText("收起");
                }
                break;
            case R.id.rl_team_structure://企业组织架构
                startActivity(new Intent(getActivity(), TeamStructureActivity.class));
                break;
            case R.id.rl_team_home://企业主页
                Intent intent = new Intent(getActivity(), TeamHomeActivity.class);
                if (addressBookBean.getCompanyId() != 0) {
                    intent.putExtra("id", addressBookBean.getCompanyId());
                } else {
                    intent.putExtra("id", UserUtils.getInstance().getUserBean().getUser_company());
                }
                startActivity(intent);
                break;
            case R.id.rl_other_team://其他团队
                startActivity(new Intent(getActivity(), OtherTeamActivity.class));
                break;
            case R.id.rl_new_people://新成员申请
                Intent intent1 = new Intent(getActivity(), TeamNewPeopleActivity.class);
                if (addressBookBean.getCompanyId() != 0) {
                    intent1.putExtra("id", addressBookBean.getCompanyId());
                } else {
                    intent1.putExtra("id", UserUtils.getInstance().getUserBean().getUser_company());
                }
                startActivity(intent1);
                break;
            case R.id.rl_request_record:
                startActivity(new Intent(getActivity(), MyApplyRecordActivity.class));
                break;
            case R.id.iv_master://管理按钮
                startActivity(new Intent(getActivity(), TeamManagerActivity.class));
                break;
        }
    }

    @Override
    public void addressBookSuccess(int code, AddressBookBean addressBookBean) {
        if (addressBookBean.getCompanyName() != null) {
            this.addressBookBean = addressBookBean;
            UserBean userBean = UserUtils.getInstance().getUserBean();
            userBean.setUser_company(addressBookBean.getCompanyId());
            userBean.setC_name(addressBookBean.getCompanyName());
            userBean.setCompany_logo(addressBookBean.getCompany_logo());
            UserUtils.getInstance().login(userBean);//更新用户信息
            if (departmentList.size() > 0) {
                departmentList.clear();
            }
            Glide.with(this).load(addressBookBean.getCompany_logo()).into(ivTeamIcon);
            tvTeamName.setText(addressBookBean.getCompanyName());
            departmentList.addAll(addressBookBean.getDepartmentList());
            if (rvTeamStructure.getVisibility() == View.VISIBLE) {
                adapter.notifyDataSetChanged();
            }
            vUtil.setVisibility(View.GONE);
            llCreateTeam.setVisibility(View.GONE);
            tvTeamName.setText(UserUtils.getInstance().getUserBean().getC_name());
            if (addressBookBean.getNewApplyList() == 0) {
                rlNewPeople.setVisibility(View.GONE);
            } else {
                rlNewPeople.setVisibility(View.VISIBLE);
            }
            if (addressBookBean.getCompanyMaster() == 0) {
                ivMaster.setVisibility(View.GONE);
            } else {
                ivMaster.setVisibility(View.VISIBLE);
            }
            if (addressBookBean.getDepartmentMaster() == 0) {
                rlMySubordinate.setVisibility(View.GONE);
            } else {
                rlMySubordinate.setVisibility(View.VISIBLE);
            }
            tvOtherTeamTitle.setText("其他团队（" + addressBookBean.getOtherTeam() + "）");
            rlTeam.setVisibility(View.VISIBLE);
        } else {
            this.addressBookBean = null;
            vUtil.setVisibility(View.VISIBLE);
            llCreateTeam.setVisibility(View.VISIBLE);
            rlTeam.setVisibility(View.GONE);
            UserBean userBean = UserUtils.getInstance().getUserBean();
            userBean.setUser_company(0);
            userBean.setC_name("");
            userBean.setCompany_logo("");
            UserUtils.getInstance().login(userBean);//更新用户信息
        }
    }

    @Override
    public void addressBookFailed(int code, String msg) {
        ToastUtils.showToast(getActivity(), "请求通讯录失败:" + code + "\t" + msg);
        Log.e("AddressBookFragment", "请求通讯录失败：" + code + "\t" + msg);
    }
}
