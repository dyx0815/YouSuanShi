package com.dan.yousuanshi.module.shared.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.addressbook.activity.AllTeamPeopleActivity;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.chat.activity.SearchPeopleActivity;
import com.dan.yousuanshi.module.chat.activity.SharedDepartmentActivity;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SharedTeamPeopleActivity extends BaseActivity {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.iv_flag)
    ImageView ivFlag;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_team)
    LinearLayout llTeam;
    @BindView(R.id.ll_choose)
    LinearLayout llChoose;

    private UserBean userBean;
    private List<Integer> userList;
    private List<Integer> departmentList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shared_team_people;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        userBean = UserUtils.getInstance().getUserBean();
        userList = getIntent().getIntegerArrayListExtra("userList");
        departmentList = getIntent().getIntegerArrayListExtra("departmentList");
        Glide.with(this).load(userBean.getCompany_logo()).into(ivLogo);
        tvTeamName.setText(userBean.getC_name());
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_search, R.id.ll_team_people, R.id.ll_team, R.id.ll_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                startActivity(new Intent(this, SearchPeopleActivity.class));
                break;
            case R.id.ll_team_people:
                Intent intent = new Intent(this, AllTeamPeopleActivity.class);
                intent.putExtra("type", 2);
                intent.putIntegerArrayListExtra("userList", (ArrayList<Integer>) userList);
                startActivityForResult(intent,1);
                break;
            case R.id.ll_team:
                if(llChoose.getVisibility() == View.VISIBLE){
                    llChoose.setVisibility(View.GONE);
                    ivRight.setImageResource(R.mipmap.icon_address_book_create_right);
                }else{
                    llChoose.setVisibility(View.VISIBLE);
                    ivRight.setImageResource(R.mipmap.icon_right_down);
                }
                break;
            case R.id.ll_choose:
                Intent intent1 = new Intent(this, SharedDepartmentActivity.class);
                intent1.putExtra("data",new MyTeamBean(userBean.getUser_company(),userBean.getC_name(),userBean.getCompany_logo()));
                intent1.putExtra("type",2);
                intent1.putIntegerArrayListExtra("departmentList", (ArrayList<Integer>) departmentList);
                startActivityForResult(intent1, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            List<TeamPeopleBean> teamPeopleList = data.getParcelableArrayListExtra("data");
            Intent intent = new Intent(this,AddWorkBenchActivity.class);
            intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) teamPeopleList);
            setResult(1,intent);
            finish();
        }else if(requestCode == 2 && resultCode == 2){
            List<DepartmentBean> departmentList = data.getParcelableArrayListExtra("data");
            Intent intent = new Intent(this,AddWorkBenchActivity.class);
            intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) departmentList);
            setResult(2,intent);
            finish();
        }
    }
}
