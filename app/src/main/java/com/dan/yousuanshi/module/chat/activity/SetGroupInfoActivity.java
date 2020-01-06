package com.dan.yousuanshi.module.chat.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.addressbook.activity.FriendInfoActivity;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.chat.adapter.SetGroupInfoAdapter;
import com.dan.yousuanshi.module.chat.bean.CreateGroupBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.chat.presenter.SetGroupInfoActivityPrensenter;
import com.dan.yousuanshi.module.chat.view.SetGroupInfoActivityView;
import com.dan.yousuanshi.module.main.activity.MainActivity;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.MD5Utils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UploadManagerUtil;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SetGroupInfoActivity extends BaseActivity<SetGroupInfoActivityPrensenter> implements SetGroupInfoActivityView {

    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.et_group_name)
    EditText etGroupName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_group_type)
    TextView tvGroupType;
    @BindView(R.id.tv_count)
    TextView tvCount;

    private String picPath;
    private List<MyFriendBean> friendList = new ArrayList<>();
    private String headIconPath = "";
    private SetGroupInfoAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_group_info;
    }

    @Nullable
    @Override
    protected SetGroupInfoActivityPrensenter initPresenter() {
        return new SetGroupInfoActivityPrensenter();
    }

    @Override
    protected void initView() {
        List<MyFriendBean> data = getIntent().getParcelableArrayListExtra("friendList");
        if (data != null && data.size() > 0) {
            friendList.add(0, new MyFriendBean(UserUtils.getInstance().getUserBean().getUser_id(),UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait()));
            friendList.addAll(data);
            tvCount.setText(friendList.size() + "人");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SetGroupInfoAdapter(this, friendList);
        adapter.setOnItemClick(new SetGroupInfoAdapter.OnItemClick() {
            @Override
            public void onItemClick(int id) {
                Intent intent = new Intent(SetGroupInfoActivity.this, FriendInfoActivity.class);
                intent.putExtra("data", id);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void createGroupSuccess(int code, CreateGroupBean data) {
        ToastUtils.showToast(this, "创建群成功！");
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void createGroupFailed(int code, String msg) {
        ToastUtils.showToast(this, "创建群聊失败；" + code + "\t" + msg);
        Log.e("SetGroupInfo", "创建群聊失败；" + code + "\t" + msg);
    }

    @Override
    public void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean) {
        ToastUtils.showToast(this, "请求七牛Token成功");
        Log.e("MyInfoActivity", "后缀名：" + FileUtils.getExtensionName(picPath));
        UploadManager uploadManager = UploadManagerUtil.getInstance();
        uploadManager.put(picPath, Constant.QINIU_AVATAR +MD5Utils.getMd5Code(""+ UserUtils.getInstance().getUserBean().getUser_id() + System.currentTimeMillis()) + "." + FileUtils.getExtensionName(picPath),
                qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("MyInfoActivity", "上传成功!!!!!!!");
                            Log.e("MyInfoActivity", "qiniu::::::::::key:" + key);
                            headIconPath = key;
                            Glide.with(SetGroupInfoActivity.this).load(picPath).into(rivHeadIcon);
                        } else {
                            Log.e("MyInfoActivity", "上传失败!!!!!!!");
                            Log.e("MyInfoActivity", "json:" + response + "\t" + info.error);
                        }
                    }
                }, null);
    }

    @Override
    public void getQiniuTokenFailed(int code, String message) {
        ToastUtils.showToast(this, "请求七牛token失败；" + code + "\t" + message);
        Log.e("SetGroupInfo", "请求七牛token失败；" + code + "\t" + message);
    }

    @OnClick({R.id.ll_back, R.id.rl_head_icon, R.id.ll_group_type, R.id.tv_create, R.id.iv_add_people, R.id.iv_remove_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_head_icon:
                openSysAlbum();
                break;
            case R.id.ll_group_type:
                break;
            case R.id.tv_create:
                Map<String, String> map = new HashMap<>();
                List<Integer> userIdList = new ArrayList<>();
                if (friendList.get(0).getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()){//去掉自己
                    friendList.remove(0);
                }
                for (MyFriendBean item : friendList){
                    userIdList.add(item.getUser_id());
                }
                map.put("userArray", new Gson().toJson(userIdList));
                map.put("groupType", String.valueOf(Constant.ORDINARY_GROUP));
                map.put("groupName", etGroupName.getText().toString());
                map.put("groupAvatar", headIconPath);
                presenter.createGroup(map);
                break;
            case R.id.iv_add_people:
                Intent intent = new Intent(this, SharedBusinessCardActivity.class);
                intent.putExtra("type", 3);
                if(friendList.get(0).getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()){
                    friendList.remove(0);
                }
                intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent);
                break;
            case R.id.iv_remove_people:
                Intent intent1 = new Intent(this, ChoosePeopleActivity.class);
                if(friendList.get(0).getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()){
                    friendList.remove(0);
                }
                intent1.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivityForResult(intent1, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data.getData() != null) {
                cropPic(data.getData());
            }
        } else if (requestCode == 10) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap bitmap = bundle.getParcelable("data");
//                    rivHeadIcon.setImageBitmap(bitmap);
                    picPath = FileUtils.saveFile(this, getFilesDir().getAbsolutePath() + "/crop/pic/", UserUtils.getInstance().getUserBean().getUser_id() + System.currentTimeMillis() + ".jpg", bitmap);
                    Log.e("MyInfoActivity", "裁剪图片地址->" + picPath);
                    presenter.getQiniuToken();
                }
            }
        } else if (requestCode == 2 && resultCode == 2) {
            List<MyFriendBean> friendList = data.getParcelableArrayListExtra("friendList");
            Iterator<MyFriendBean> iterator = this.friendList.iterator();
            while (iterator.hasNext()){
                for(MyFriendBean friend:friendList){
                    MyFriendBean myFriendBean = iterator.next();
                    if(friend.getUser_id() == myFriendBean.getUser_id()){
                        iterator.remove();
                    }
                }
            }
            this.friendList.add(0,new MyFriendBean(UserUtils.getInstance().getUserBean().getUser_id(),UserUtils.getInstance().getUserBean().getNick_name(),UserUtils.getInstance().getUserBean().getUser_portrait()));
            adapter.notifyDataSetChanged();
            tvCount.setText(this.friendList.size()+"人");
        }
    }

    /**
     * 打开系统相册
     */
    private void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, 1);
    }

    /**
     * 裁剪图片
     *
     * @param data
     */
    private void cropPic(Uri data) {
        if (data == null) {
            return;
        }
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(data, "image/*");

        // 开启裁剪：打开的Intent所显示的View可裁剪
        cropIntent.putExtra("crop", "true");
        // 裁剪宽高比
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // 裁剪输出大小
        cropIntent.putExtra("outputX", 320);
        cropIntent.putExtra("outputY", 320);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, 10);
    }

}
