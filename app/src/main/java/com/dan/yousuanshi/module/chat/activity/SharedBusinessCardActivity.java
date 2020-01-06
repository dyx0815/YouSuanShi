package com.dan.yousuanshi.module.chat.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.module.addressbook.activity.AllTeamPeopleActivity;
import com.dan.yousuanshi.module.addressbook.activity.ChooseMyFriendActivity;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.chat.adapter.OftenPeopleAdapter;
import com.dan.yousuanshi.module.chat.adapter.SharedBusinessCardAdapter;
import com.dan.yousuanshi.module.chat.presenter.SharedBusinessCardActivityPresenter;
import com.dan.yousuanshi.module.chat.view.SharedBusinessCardActivityView;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SharedBusinessCardActivity extends BaseActivity<SharedBusinessCardActivityPresenter> implements SharedBusinessCardActivityView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_face_create_group)
    LinearLayout llFaceCreateGroup;
    @BindView(R.id.ll_often_people)
    LinearLayout llOftenPeople;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_often)
    RecyclerView rvOften;
    @BindView(R.id.tv_choose_count)
    TextView tvChooseCount;
    @BindView(R.id.ll_my_friend)
    LinearLayout llMyFriend;
    @BindView(R.id.ll_sure)
    LinearLayout llSure;
    @BindView(R.id.ll_team_people)
    LinearLayout llTeamPeople;

    private List<MyTeamBean> myTeamList = new ArrayList<>();
    private SharedBusinessCardAdapter adapter;
    private int type = 1;//1为分享 2为创建群 3为群组加人 5为公司 6为选择新管理员
    private List<MyFriendBean> friendList;
    private List<MyFriendBean> oftenPeople = new ArrayList<>();//常用联系人
    private OftenPeopleAdapter oftenPeopleAdapter;
    private int groupId;
    private int groupType;
    private MyTeamBean team;//当为公司群时只显示公司
    private String sign;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shared_business_card;
    }

    @Nullable
    @Override
    protected SharedBusinessCardActivityPresenter initPresenter() {
        return new SharedBusinessCardActivityPresenter();
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 1);
        friendList = getIntent().getParcelableArrayListExtra("friendList");
        groupId = getIntent().getIntExtra("groupId", 0);
        groupType = getIntent().getIntExtra("groupType", 0);
        if (type == 2) {
            tvTitle.setText("发起群聊");
            llFaceCreateGroup.setVisibility(View.VISIBLE);
        }
        if (type != 1) {//不是分享名片
            List<MyFriendBean> data = DataBaseHelper.queryChatList2(this, UserUtils.getInstance().getUserBean().getUser_id());
            oftenPeople.addAll(data);
            llOftenPeople.setVisibility(View.VISIBLE);
            if (friendList != null) {//传过来的联系人不为空 就是给群聊添加成员
                for (MyFriendBean item : oftenPeople) {
                    for (MyFriendBean friend : friendList) {
                        if (item.getUser_id() == friend.getUser_id()) {
                            item.setChecked(true);
                        }
                    }
                }
            }
            rvOften.setLayoutManager(new LinearLayoutManager(this));
            oftenPeopleAdapter = new OftenPeopleAdapter(this, oftenPeople, type);
            oftenPeopleAdapter.setOnItemClick(new OftenPeopleAdapter.OnItemClick() {
                @Override
                public void onItemClick(int count) {
                    llSure.setVisibility(View.VISIBLE);
                    tvChooseCount.setText("已选择（" + count + "）人");
                }
            });
            rvOften.setAdapter(oftenPeopleAdapter);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SharedBusinessCardAdapter(this, myTeamList);
        adapter.setOnItemClick(new SharedBusinessCardAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SharedBusinessCardActivity.this, SharedDepartmentActivity.class);
                intent.putExtra("data", myTeamList.get(position));
                startActivityForResult(intent, 1);
            }
        });
        recyclerView.setAdapter(adapter);
        if (groupType > 0) {//0以上为公司群
            llMyFriend.setVisibility(View.GONE);
            llOftenPeople.setVisibility(View.GONE);
            team = getIntent().getParcelableExtra("team");
            myTeamList.add(team);
            adapter.notifyDataSetChanged();
        }
        if (type == 5 || type == 6 || type == 7) {
            sign = getIntent().getStringExtra("sign");
            llTeamPeople.setVisibility(View.VISIBLE);
            llMyFriend.setVisibility(View.GONE);
            llOftenPeople.setVisibility(View.GONE);
            myTeamList.add(new MyTeamBean(UserUtils.getInstance().getUserBean().getUser_company(), UserUtils.getInstance().getUserBean().getC_name(), UserUtils.getInstance().getUserBean().getCompany_logo()));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void loadData() {
        if (groupType == 0 && type < 5) {//不为公司群请求公司列表
            if (!StringUtils.isEmpty(UserUtils.getInstance().getUserBean().getC_name())) {
                Map<String,String> map = new HashMap<>();
                map.put("isAllCompany","1");
                presenter.getMyTeam(map);
            }
        }
    }

    @Override
    public void getMyTeamSuccess(int code, List<MyTeamBean> data) {
        myTeamList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getMyTeamFailed(int code, String msg) {
        ToastUtils.showToast(this, "请求公司列表失败；" + code + "\t" + msg);
        Log.e("SharedBusinessCard", "请求公司列表失败；" + code + "\t" + msg);
    }

    @Override
    public void addGroupPeopleSuccess(int code, List list) {
        finish();
    }

    @Override
    public void addGroupPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this, "添加群员失败：" + code + msg);
        Log.e("ChooseMyFriend", "添加群员失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_search, R.id.ll_my_friend, R.id.ll_face_create_group, R.id.ll_often_people, R.id.tv_sure, R.id.ll_team_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                startActivity(new Intent(this, SearchPeopleActivity.class));
                break;
            case R.id.ll_my_friend:
                if (type == 1) {
                    startActivityForResult(new Intent(this, SharedMyFriendActivity.class), 1);
                } else if (type == 2) {
                    startActivityForResult(new Intent(this, ChooseMyFriendActivity.class), 1);
                } else if (type == 3) {//发起群聊
                    Intent intent = new Intent(this, ChooseMyFriendActivity.class);
                    intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                    startActivityForResult(intent, 1);
                } else if (type == 4) {//群消息设置
                    Intent intent = new Intent(this, ChooseMyFriendActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("groupId", groupId);
                    intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.ll_face_create_group://面对面建群
                startActivity(new Intent(this, FaceCreateGroupActivity.class));
                finish();
                break;
            case R.id.ll_often_people://常用联系人
                if (rvOften.getVisibility() == View.VISIBLE) {
                    ivRight.setImageResource(R.mipmap.icon_address_book_create_right);
                    rvOften.setVisibility(View.GONE);
                    llSure.setVisibility(View.GONE);
                } else {
                    ivRight.setImageResource(R.mipmap.icon_right_down);
                    rvOften.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_sure://确定
                if (type == 4) {
                    Map<String, Object> map = new HashMap<>();
                    List<Integer> userArray = new ArrayList<>();
                    for (MyFriendBean item : oftenPeopleAdapter.getChooseFriend()) {
                        userArray.add(item.getUser_id());
                    }
                    map.put("userArray", new Gson().toJson(userArray));
                    map.put("groupId", groupId);
                    presenter.addGroupPeople(map);
                } else {
                    Intent intent = new Intent(this, SetGroupInfoActivity.class);
                    intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) oftenPeopleAdapter.getChooseFriend());
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.ll_team_people:
                MyApplication.addActivity(this);
                Intent intent = new Intent(this, AllTeamPeopleActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("sign", sign);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            MyFriendBean myFriendBean = data.getParcelableExtra("data");
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("data", (Parcelable) myFriendBean);
            setResult(6, intent);
            finish();
        } else if (requestCode == 1 && resultCode == 4) {
            List<MyFriendBean> friendList = data.getParcelableArrayListExtra("friendList");
            if (friendList.size() > 0) {
                Intent intent = new Intent(this, SetGroupInfoActivity.class);
                intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent);
                finish();
            }
        } else if (requestCode == 1 && resultCode == 5) {
            finish();
        }
    }


}
