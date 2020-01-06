package com.dan.yousuanshi.module.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.chat.adapter.FaceCreateGroupAdapter;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.FaceCreateGroupBean;
import com.dan.yousuanshi.module.chat.bean.JoinFaceCreateGroupBean;
import com.dan.yousuanshi.module.chat.presenter.FaceCreateGroupActivityPresenter;
import com.dan.yousuanshi.module.chat.utils.NumIn;
import com.dan.yousuanshi.module.chat.view.FaceCreateGroupActivityView;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class FaceCreateGroupActivity extends BaseActivity<FaceCreateGroupActivityPresenter> implements FaceCreateGroupActivityView, NumIn.InputCallBack {

    @BindView(R.id.numin)
    NumIn numin;
    @BindView(R.id.ll_create)
    LinearLayout llCreate;
    @BindView(R.id.ll_join)
    LinearLayout llJoin;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String latitude;
    private String longitude;
    private FaceCreateGroupBean faceCreateGroupBean;
    private String code;
    private List<FaceCreateGroupBean.UserListBean> userList = new ArrayList<>();
    private FaceCreateGroupAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_create_group;
    }

    @Nullable
    @Override
    protected FaceCreateGroupActivityPresenter initPresenter() {
        return new FaceCreateGroupActivityPresenter();
    }

    @Override
    protected void initView() {
        numin.setInputCallback(this);
        location();
        showInput(this, numin);
        recyclerView.setLayoutManager(new GridLayoutManager(this,6));
        adapter = new FaceCreateGroupAdapter(this,userList);
        recyclerView.setAdapter(adapter);
        presenter.showLoadingDialog();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        hideInput(this, numin);
    }

    @OnClick({R.id.ll_back, R.id.tv_join_face_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                Map<String,Object> map = new HashMap<>();
                map.put("faceGroupId",faceCreateGroupBean.getFaceGroupId());
                presenter.exitFaceCreateGroup(map);
                break;
            case R.id.tv_join_face_group:
                Map<String,Object> map1 = new HashMap<>();
                map1.put("faceGroupId",faceCreateGroupBean.getFaceGroupId());
                presenter.joinFaceCreateGroup(map1);
                break;
        }
    }

    @Override
    public void faceCreateGroupSuccess(int code, FaceCreateGroupBean data) {
        hideInput(this,numin);
        faceCreateGroupBean = data;
        userList.addAll(data.getUserList());
        userList.add(new FaceCreateGroupBean.UserListBean("0",""));
        adapter.notifyDataSetChanged();
        llCreate.setVisibility(View.GONE);
        llJoin.setVisibility(View.VISIBLE);
        tvNumber.setText(this.code.charAt(0)+"   "+this.code.charAt(1)+"   "+this.code.charAt(2)+"   "+this.code.charAt(3));
    }

    @Override
    public void faceCreateGroupFailed(int code, String msg) {
        ToastUtils.showToast(this, "面对面群聊失败：" + code + msg);
        Log.e("FaceCreateGroup", "面对面群聊失败：" + code + msg);
    }

    @Override
    public void exitFaceCreateGroupSuccess(int code, List list) {
        finish();
    }

    @Override
    public void exitFaceCreateGroupFailed(int code, String msg) {
        ToastUtils.showToast(this, "退出面对面群聊失败：" + code + msg);
        Log.e("FaceCreateGroup", "退出面对面群聊失败：" + code + msg);
    }

    @Override
    public void joinFaceCreateGroupSuccess(int code, JoinFaceCreateGroupBean data) {
        ChatBean chatBean = new ChatBean();
        chatBean.setPid(data.getId());
        chatBean.setName(data.getGroup_name());
        chatBean.setUserIconUrl(data.getGroup_avatar());
        chatBean.setType(Constant.CHAT_GROUP_TYPE);
        chatBean.setGroupType(data.getGroup_type());
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("user", chatBean);
        startActivity(intent);
    }

    @Override
    public void joinFaceCreateGroupFailed(int code, String msg) {
        ToastUtils.showToast(this, "加入面对面群聊失败：" + code + msg);
        Log.e("FaceCreateGroup", "加入面对面群聊失败：" + code + msg);
    }


    @Override
    public void onInputFinish(String str) {
        if (StringUtils.isEmpty(longitude) || StringUtils.isEmpty(latitude)) {
            ToastUtils.showToast(this, "经纬度为空！请重新打开界面定位！");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("mathNums", str);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        presenter.faceCreateGroup(map);
        code = str;
    }

    @Override
    public void onTextChanged(int inputNumber) {

    }

    private void location() {
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    presenter.dismissLoadingDialog();
                    if (aMapLocation != null) {
                        if (aMapLocation.getErrorCode() == 0) {
                            latitude = String.valueOf(aMapLocation.getLatitude());
                            longitude = String.valueOf(aMapLocation.getLongitude());
                        } else {
                            //定位失败
                            ToastUtils.showToast(getActivity(), "定位失败！");
                            Log.e("MyLocationActivity", "定位失败！" + "错误码：" + aMapLocation.getErrorCode());
                        }
                    }
                }
            });
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
            if (null != mLocationClient) {
                mLocationClient.setLocationOption(mLocationOption);
                //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                mLocationClient.stopLocation();
                mLocationClient.startLocation();
            }
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取最近3s内精度最高的一次定位结果：
            // 设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            mLocationOption.setOnceLocationLatest(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(20000);
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
        }
        //启动定位
        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
        }
        mLocationClient.startLocation();
    }

    private void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void joinOrExitPeople(ChatBean chatBean){
        if(chatBean.getFileype() == Constant.CHAT_FACE_CREATE_GROUP_JOIN||chatBean.getFileype() == Constant.CHAT_FACE_CREATE_GROUP_EXIT){
            if(faceCreateGroupBean.getFaceId().equals(chatBean.getStxt())){
                if(chatBean.getFileype() == Constant.CHAT_FACE_CREATE_GROUP_JOIN){
                    userList.add(userList.size()-1,new FaceCreateGroupBean.UserListBean(chatBean.getGroupinfo().getNickName(),chatBean.getGroupinfo().getAvatar()));
                    adapter.notifyItemInserted(userList.size());
                }else{
                    for(int i = 0;i<userList.size();i++){
                        if(userList.get(i).getUser_id().equals(chatBean.getGroupinfo().getNickName())){
                            userList.remove(i);
                            adapter.notifyItemRemoved(i);
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("faceGroupId",faceCreateGroupBean.getFaceGroupId());
        presenter.joinFaceCreateGroup(map1);
    }
}
