package com.dan.yousuanshi.module.shared.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
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
import com.dan.yousuanshi.dao.bean.OftenModelBean;
import com.dan.yousuanshi.module.addressbook.activity.AddTeamActivity;
import com.dan.yousuanshi.module.addressbook.activity.CreateTeamActivity;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.shared.activity.AnnouncementInfoActivity;
import com.dan.yousuanshi.module.shared.activity.SwitchCompanyActivity;
import com.dan.yousuanshi.module.shared.adapter.WorkBenchAdapter;
import com.dan.yousuanshi.module.shared.bean.AnnouncementBean;
import com.dan.yousuanshi.module.shared.bean.BannerBean;
import com.dan.yousuanshi.module.shared.bean.WorkbenchBean;
import com.dan.yousuanshi.module.shared.presenter.SharedFragmentPresenter;
import com.dan.yousuanshi.module.shared.view.SharedFragmentView;
import com.dan.yousuanshi.utils.MyHanlder;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SharedFragment extends BaseFragment<SharedFragmentPresenter> implements SharedFragmentView {

    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.tv_announcement_title)
    TextView tvAnnouncementTitle;
    @BindView(R.id.tv_announcement_user)
    TextView tvAnnouncementUser;
    @BindView(R.id.tv_announcement)
    TextView tvAnnouncement;
    @BindView(R.id.rl_announcement)
    RelativeLayout rlAnnouncement;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_shared)
    LinearLayout llShared;
    @BindView(R.id.tv_join_team)
    TextView tvJoinTeam;
    @BindView(R.id.tv_create_team)
    TextView tvCreateTeam;
    @BindView(R.id.ll_no_team)
    LinearLayout llNoTeam;

    private AnnouncementBean announcement;
    private UserBean userBean;
    private WorkBenchAdapter adapter;
    private List<WorkbenchBean.ModelListBean> workbenchList = new ArrayList<>();
    private MyTeamBean myTeam;//共享选择的myTeam

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shared;
    }

    @Nullable
    @Override
    protected SharedFragmentPresenter initPresenter() {
        return new SharedFragmentPresenter();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MyHanlder.getInstance().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StatusBarUtil.setColor(getActivity(), getActivity().getColor(R.color.white), 0);
                    myTeam = new Gson().fromJson(SPUtils.getInstance().get(Constant.SHARED_COMPANY,""),MyTeamBean.class);
                    if(userBean.getUser_company() == 0){//没有公司
                        llShared.setVisibility(View.GONE);
                        llNoTeam.setVisibility(View.VISIBLE);
                    }else{
                        presenter.getNewAnnouncement(myTeam.getCompanyId());//公告
                        presenter.getWorkbench(myTeam.getCompanyId());//工作台
                        presenter.getBanner(myTeam.getCompanyId());//banner
                        tvTeamName.setText(myTeam.getCompanyName());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter = new WorkBenchAdapter(getActivity(), workbenchList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }, 10);
        }
    }

    @Override
    protected void initView() {
        userBean = UserUtils.getInstance().getUserBean();
        if(StringUtils.isEmpty(SPUtils.getInstance().get(Constant.SHARED_COMPANY,""))){
            SPUtils.getInstance().save(Constant.SHARED_COMPANY,new Gson().toJson(new MyTeamBean(userBean.getUser_company(),userBean.getC_name(),userBean.getCompany_logo())));
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        myTeam = new Gson().fromJson(SPUtils.getInstance().get(Constant.SHARED_COMPANY,""),MyTeamBean.class);
        if(userBean.getUser_company() == 0){//没有公司
            llShared.setVisibility(View.GONE);
            llNoTeam.setVisibility(View.VISIBLE);
        }else{
            presenter.getNewAnnouncement(myTeam.getCompanyId());//公告
            presenter.getWorkbench(myTeam.getCompanyId());//工作台
            presenter.getBanner(myTeam.getCompanyId());//banner
            tvTeamName.setText(myTeam.getCompanyName());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new WorkBenchAdapter(getActivity(), workbenchList);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void getNewAnnouncementSuccess(int code, AnnouncementBean data) {
        if(StringUtils.isEmpty(data.getTitle())){
            announcement = null;
            tvAnnouncementTitle.setText("公告");
            tvAnnouncement.setText("暂无公告");
            tvAnnouncementUser.setText("系统消息");
        }else{
            announcement = data;
            tvAnnouncementTitle.setText(data.getTitle());
            tvAnnouncement.setText(data.getContent());
            tvAnnouncementUser.setText(data.getReal_name());
        }
    }

    @Override
    public void getNewAnnouncementFailed(int code, String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    @Override
    public void getWorkbenchSuccess(int code, WorkbenchBean data) {
        workbenchList.clear();
        if (data.getIs_creater() == 1) {//如果是公司主管理员 则显示模板管理和流程管理
            for (int i = 0; i < data.getModelList().size(); i++) {
                if (data.getModelList().get(i).getModel_id() == 1) {//管理模块
                    data.getModelList().get(i).getChildren().add(new WorkbenchBean.ModelListBean.ChildrenBean(666666,"模板管理", 1, "https://dzcdn.zixuezhilu.com/icon_shared_muban.png"));
                    data.getModelList().get(i).getChildren().add(new WorkbenchBean.ModelListBean.ChildrenBean(666667,"流程管理", 1, "https://dzcdn.zixuezhilu.com/icon_shared_liucheng.png"));
                    break;
                }
            }
        }
        if (data.getIs_show_add() == 1) {//如果有添加权限，所有项都加添加
            for (int i = 0; i < data.getModelList().size(); i++) {
                if(data.getModelList().get(i).getModel_id() != 1){
                    data.getModelList().get(i).getChildren().add(new WorkbenchBean.ModelListBean.ChildrenBean(999999,"添加", data.getModelList().get(i).getId(), "https://dzcdn.zixuezhilu.com/icon_shared_add.png"));
                }
            }
        }
        if (userBean != null && !DataBaseHelper.tabbleIsExist(getContext(), DataBaseHelper.getOftenModelTableName(userBean.getUser_id()))) {
            DataBaseHelper.createOftenModelTable(getContext(), userBean.getUser_id());
        }
        List<OftenModelBean> oftenModelList = DataBaseHelper.queryOftenModel(getActivity(),userBean.getUser_id());
        if(oftenModelList.size()>0){
            WorkbenchBean.ModelListBean modelListBean = new WorkbenchBean.ModelListBean("常用组件");
            for(OftenModelBean item : oftenModelList){
                for(WorkbenchBean.ModelListBean model : data.getModelList() ){
                    for(WorkbenchBean.ModelListBean.ChildrenBean childrenBean : model.getChildren()){
                        if(item.getModelId() == childrenBean.getModel_id()){
                            modelListBean.getChildren().add(childrenBean);
                        }
                    }
                }
            }
            if(data.getModelList().get(0).getModel_id() == 1){
                data.getModelList().add(1,modelListBean);
            }else{
                data.getModelList().add(0,modelListBean);
            }
        }
        workbenchList.addAll(data.getModelList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getWorkbenchFailed(int code, String msg) {
        ToastUtils.showToast(getActivity(), "获取工作台：" + msg);
    }

    @Override
    public void getBannerSuccess(int code, List<BannerBean> data) {
        banner.setPages(data, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.setIndicatorVisible(false);
    }

    @Override
    public void getBannerFailed(int code, String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    @OnClick({R.id.tv_join_team, R.id.tv_create_team,R.id.rl_announcement,R.id.ll_team})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_join_team:
                startActivity(new Intent(getActivity(), AddTeamActivity.class));
                break;
            case R.id.tv_create_team:
                startActivity(new Intent(getActivity(), CreateTeamActivity.class));
                break;
            case R.id.rl_announcement:
                if(announcement != null){
                    Intent intent = new Intent(getActivity(), AnnouncementInfoActivity.class);
                    intent.putExtra("data",announcement);
                    startActivity(intent);
                }
                break;
            case R.id.ll_team:
                Intent intent = new Intent(getActivity(), SwitchCompanyActivity.class);
                intent.putExtra("companyId",myTeam.getCompanyId());
                startActivity(intent);
                break;
        }
    }

    /**
     * banner
     */
    class BannerViewHolder implements MZViewHolder<BannerBean> {

        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_shared_home_viewpager, null);
            mImageView = view.findViewById(R.id.iv_banner);
            return view;
        }

        @Override
        public void onBind(Context context, int i, BannerBean bannerBean) {
            Glide.with(getActivity()).load(bannerBean.getBanner_img()).into(mImageView);
        }
    }
}
