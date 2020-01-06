package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.module.addressbook.activity.FriendInfoActivity;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.chat.adapter.SetGroupInfoAdapter;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.MyGroupInfoBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.chat.presenter.GroupChatSetActivityPresenter;
import com.dan.yousuanshi.module.chat.view.GroupChatSetActivityView;
import com.dan.yousuanshi.module.main.activity.MainActivity;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.MD5Utils;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UploadManagerUtil;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupChatSetActivity extends BaseActivity<GroupChatSetActivityPresenter> implements GroupChatSetActivityView {

    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.tv_group_name)
    TextView tvGroupName;
    @BindView(R.id.tv_group_announcement)
    TextView tvGroupAnnouncement;
    @BindView(R.id.tv_picture)
    TextView tvPicture;
    @BindView(R.id.tv_group_file)
    TextView tvGroupFile;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_add_people)
    ImageView ivAddPeople;
    @BindView(R.id.iv_remove_people)
    ImageView ivRemovePeople;
    @BindView(R.id.ll_group_admin)
    LinearLayout llGroupAdmin;
    @BindView(R.id.ll_group_name)
    LinearLayout llGroupName;
    @BindView(R.id.tv_group_nick_name)
    TextView tvGroupNickName;
    @BindView(R.id.ll_group_name_by_me)
    LinearLayout llGroupNameByMe;
    @BindView(R.id.s_top)
    Switch sTop;
    @BindView(R.id.s_disturb)
    Switch sDisturb;
    @BindView(R.id.s_translation)
    Switch sTranslation;
    @BindView(R.id.tv_exit_group)
    TextView tvExitGroup;
    @BindView(R.id.tv_disband_group)
    TextView tvDisbandGroup;

    private ChatBean pUser;
    private MyGroupInfoBean myGroupInfoBean;
    private SetGroupInfoAdapter adapter;
    private List<MyFriendBean> friendList = new ArrayList<>();
    private String picPath;
    private int updateType;
    private String groupName;
    private String myGroupNickName;
    private boolean isMaster = false;//是否有管理权限
    private boolean isCreate = false;
    private Dialog clearDialog;
    private Dialog updateGroupNameDialog;//修改群名称dialog
    private Dialog updateMyNameByGroupDialog;//修改我在本群的昵称
    private Dialog exitDialog;
    private Dialog disbandDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_chat_set;
    }

    @Nullable
    @Override
    protected GroupChatSetActivityPresenter initPresenter() {
        return new GroupChatSetActivityPresenter();
    }

    @Override
    protected void initView() {
        pUser = (ChatBean) getIntent().getSerializableExtra("data");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SetGroupInfoAdapter(this, friendList);
        adapter.setOnItemClick(new SetGroupInfoAdapter.OnItemClick() {
            @Override
            public void onItemClick(int id) {
                Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
                intent.putExtra("data", id);
                startActivity(intent);
            }
        });
        if(SPUtils.getInstance().get(Constant.IS_SEND_NOTIFICATION+Constant.CHAT_GROUP_TYPE+pUser.getPid(),0) == 0){//0为打扰，1为免打扰
            sDisturb.setChecked(false);
        }else{
            sDisturb.setChecked(true);
        }
        recyclerView.setAdapter(adapter);
        sTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues values = new ContentValues();
                if(isChecked){
                    values.put("is_top",1);
                    pUser.setTop(true);
                }else{
                    values.put("is_top",0);
                    pUser.setTop(false);
                }
                DataBaseHelper.updateChatListById(getActivity(),UserUtils.getInstance().getUserBean().getUser_id(),pUser.getChatListId(),values);
            }
        });
        sDisturb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SPUtils.getInstance().save(Constant.IS_SEND_NOTIFICATION+Constant.CHAT_GROUP_TYPE+pUser.getPid(),1);
                }else{
                    SPUtils.getInstance().save(Constant.IS_SEND_NOTIFICATION+Constant.CHAT_GROUP_TYPE+pUser.getPid(),0);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        pUser = (ChatBean) getIntent().getSerializableExtra("data");
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", pUser.getPid());
        presenter.getGroupInfo(map);
    }

    @Override
    public void getGroupInfoSuccess(int code, MyGroupInfoBean data) {
        friendList.clear();
        myGroupInfoBean = data;
        Glide.with(this).load(data.getGroupInfo().getGroup_avatar()).into(rivHeadIcon);
        tvGroupName.setText(data.getGroupInfo().getGroup_name());
        tvCount.setText(data.getGroupUser().size() + "人");
        for (MyGroupInfoBean.GroupUserBean item : data.getGroupUser()) {
            friendList.add(new MyFriendBean(item.getUser_id(), item.getReal_name(), item.getUser_portrait(),item.getIs_creater(),item.getIs_master()));
            if (item.getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()) {
                tvGroupNickName.setText(item.getReal_name());
                if (item.getIs_creater() == 1) {//如果是群主 群管理 群名称 解散该群展示
                    isMaster = true;
                    isCreate = true;
                    llGroupAdmin.setVisibility(View.VISIBLE);
                    llGroupName.setVisibility(View.VISIBLE);
                    tvDisbandGroup.setVisibility(View.VISIBLE);
                    ivAddPeople.setVisibility(View.VISIBLE);
                    ivRemovePeople.setVisibility(View.VISIBLE);
                } else if (item.getIs_master() == 1) {//如果是群管理员  群管理 群名称展示
                    isMaster = true;
                    llGroupAdmin.setVisibility(View.VISIBLE);
                    llGroupName.setVisibility(View.VISIBLE);
                    ivAddPeople.setVisibility(View.VISIBLE);
                    ivRemovePeople.setVisibility(View.VISIBLE);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getGroupInfoFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取群信息失败：" + code + msg);
        Log.e("GroupChatSet", "获取群信息失败：" + code + msg);
    }

    @Override
    public void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean) {
        ToastUtils.showToast(this, "请求七牛Token成功");
        Log.e("MyInfoActivity", "后缀名：" + FileUtils.getExtensionName(picPath));
        UploadManager uploadManager = UploadManagerUtil.getInstance();
        uploadManager.put(picPath, Constant.QINIU_AVATAR +MD5Utils.getMd5Code(""+ pUser.getPid() + System.currentTimeMillis()) + "." + FileUtils.getExtensionName(picPath),
                qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("MyInfoActivity", "上传成功!!!!!!!");
                            Log.e("MyInfoActivity", "qiniu::::::::::key:" + key);
                            Map<String, Object> map = new HashMap<>();
                            updateType = 2;
                            map.put("setType", updateType);//1,群名称修改,2,群头像修改
                            map.put("groupId", pUser.getPid());
                            map.put("groupSet", picPath);
                            presenter.updateGroupInfo(map);
                        } else {
                            Log.e("MyInfoActivity", "上传失败!!!!!!!");
                            Log.e("MyInfoActivity", "json:" + response + "\t" + info.error);
                        }
                    }
                }, null);
    }

    @Override
    public void getQiniuTokenFailed(int code, String message) {
        ToastUtils.showToast(this, "获取七牛token失败：" + code + message);
        Log.e("GroupChatSet", "获取七牛token失败：" + code + message);
    }

    @Override
    public void updateUserInfoSuccess(int code, List list) {
        if (updateType == 1) {
            tvGroupName.setText(groupName);
        } else if (updateType == 2) {
            Glide.with(this).load(picPath).into(rivHeadIcon);
        }
    }

    @Override
    public void updateUserInfoFailed(int code, String msg) {
        ToastUtils.showToast(this, "更改群信息失败：" + code + msg);
        Log.e("GroupChatSet", "更改群信息失败：" + code + msg);
    }

    @Override
    public void exitGroupSuccess(int code, List list) {
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void exitGroupFailed(int code, String msg) {
        ToastUtils.showToast(this, "退出群聊失败：" + code + msg);
        Log.e("GroupChatSet", "退出群聊失败：" + code + msg);
    }

    @Override
    public void disbandGroupSuccess(int code, List list) {
        ToastUtils.showToast(this,"解散群聊成功！");
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void disbandGroupFailed(int code, String msg) {
        ToastUtils.showToast(this, "解散群聊失败：" + code + msg);
        Log.e("GroupChatSet", "解散群聊失败：" + code + msg);
    }

    @Override
    public void updateMyGroupNickNameSuccess(int code, List list) {
        tvGroupNickName.setText(myGroupNickName);
    }

    @Override
    public void updateMyGroupNickNameFailed(int code, String msg) {
        ToastUtils.showToast(this, "更改我在本群的昵称失败：" + code + msg);
        Log.e("GroupChatSet", "更改我在本群的昵称失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.riv_head_icon, R.id.tv_group_announcement, R.id.tv_picture, R.id.tv_group_file, R.id.iv_add_people
            , R.id.iv_remove_people, R.id.ll_group_admin, R.id.ll_group_name, R.id.tv_group_nick_name, R.id.ll_group_name_by_me
            , R.id.ll_clear_chat_record, R.id.tv_exit_group, R.id.tv_disband_group,R.id.ll_group_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.riv_head_icon://群头像
                openSysAlbum();
                break;
            case R.id.tv_group_announcement://群公告
                Intent intent = new Intent(this, GroupAnnouncementActivity.class);
                intent.putExtra("data", pUser.getPid());
                intent.putExtra("isMaster", isMaster);
                startActivity(intent);
                break;
            case R.id.tv_picture://群图片
                Intent intent1 = new Intent(this, GroupPictureActivity.class);
                intent1.putExtra("data", pUser.getPid());
                intent1.putExtra("isMaster", isMaster);
                startActivity(intent1);
                break;
            case R.id.tv_group_file://群文件
                Intent intent2 = new Intent(this, GroupFileActivity.class);
                intent2.putExtra("data", pUser.getPid());
                intent2.putExtra("isMaster", isMaster);
                startActivity(intent2);
                break;
            case R.id.ll_group_people://群成员
                Intent intent5 = new Intent(this,GroupPeopleActivity.class);
                intent5.putExtra("groupId",pUser.getPid());
                intent5.putExtra("groupType",myGroupInfoBean.getGroupInfo().getGroup_type());
                intent5.putExtra("team",new MyTeamBean(myGroupInfoBean.getGroupInfo().getGroup_company(),myGroupInfoBean.getGroupInfo().getGroup_company_name(),myGroupInfoBean.getGroupInfo().getGroup_company_logo()));
                intent5.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent5);
                break;
            case R.id.iv_add_people://增加群成员
                Intent intent3 = new Intent(this, SharedBusinessCardActivity.class);
                intent3.putExtra("type", 4);
                intent3.putExtra("groupId", pUser.getPid());
                intent3.putExtra("groupType", myGroupInfoBean.getGroupInfo().getGroup_type());
                for(int i = 0;i<friendList.size();i++){
                    if(friendList.get(i).getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()){
                        friendList.remove(i);
                    }
                }
                intent3.putExtra("team",new MyTeamBean(myGroupInfoBean.getGroupInfo().getGroup_company(),myGroupInfoBean.getGroupInfo().getGroup_company_name(),myGroupInfoBean.getGroupInfo().getGroup_company_logo()));
                intent3.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent3);
                break;
            case R.id.iv_remove_people://减少群成员
                Intent intent4 = new Intent(this, ChoosePeopleActivity.class);
                for(int i = 0;i<friendList.size();i++){
                    if(friendList.get(i).getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()){
                        friendList.remove(i);
                    }
                }
                intent4.putExtra("type", 4);
                intent4.putExtra("groupId", pUser.getPid());
                intent4.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivityForResult(intent4, 2);
                break;
            case R.id.ll_group_admin://群管理
                Intent intent6 = new Intent(this,GroupMasterActivity.class);
                intent6.putExtra("groupId",pUser.getPid());
                intent6.putExtra("isCreate",isCreate);
                intent6.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                startActivity(intent6);
                break;
            case R.id.ll_group_name://群名称
                showUpdateGroupNameDialog();
                break;
            case R.id.tv_group_nick_name://群名称
                showUpdateGroupNameDialog();
                break;
            case R.id.ll_group_name_by_me://我在群里的昵称
                showUpdateMyNameByGroupDialog();
                break;
            case R.id.ll_clear_chat_record://清空聊天记录
                showClearDialog();
                break;
            case R.id.tv_exit_group://退出群组
                showExitDialog();
                break;
            case R.id.tv_disband_group://解散群组
                showDisbandDialog();
                break;
        }
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

    /**
     * 打开系统相册
     */
    private void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {//相册选择完直接出来裁剪
            if (data != null) {
                cropPic(data.getData());
            }
        } else if (requestCode == 10) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap bitmap = bundle.getParcelable("data");
//                    rivHeadIcon.setImageBitmap(bitmap);
                    picPath = FileUtils.saveFile(this, getFilesDir().getAbsolutePath() + "/crop/pic/", pUser.getPid() + System.currentTimeMillis() + ".jpg", bitmap);
                    Log.e("MyInfoActivity", "裁剪图片地址->" + picPath);
                    presenter.getQiniuToken();
                }
            }
        }
    }
    private void showClearDialog() {
        if (clearDialog == null) {
            clearDialog = new Dialog(this);
            clearDialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = clearDialog.findViewById(R.id.tv_title);
            tvTitle.setText("确认清除聊天记录？");
            TextView tvSure = clearDialog.findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearDialog.dismiss();
                    DataBaseHelper.deleteGroupChatRecordTable(getActivity(),pUser.getPid(), UserUtils.getInstance().getUserBean().getUser_id());
                    ContentValues values = new ContentValues();
                    values.put("last_message","");
                    DataBaseHelper.updateChatListById(getActivity(),UserUtils.getInstance().getUserBean().getUser_id(),pUser.getChatListId(),values);
                    ToastUtils.showToast(getActivity(),"清空成功！");
                }
            });
            TextView tvCancel = clearDialog.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearDialog.dismiss();
                }
            });
        }
        clearDialog.show();
    }

    private void showUpdateGroupNameDialog(){
        if(updateGroupNameDialog == null){
            updateGroupNameDialog = new Dialog(this);
            updateGroupNameDialog.setContentView(R.layout.dialog_update_group_info);
            EditText etInput = updateGroupNameDialog.findViewById(R.id.et_input);
            etInput.setFocusable(true);
            etInput.requestFocus();
            showInput(this,etInput);
            TextView tvCancel = updateGroupNameDialog.findViewById(R.id.tv_cancel);
            TextView tvSure = updateGroupNameDialog.findViewById(R.id.tv_sure);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateGroupNameDialog.dismiss();
                    hideInput(getActivity(),etInput);
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateGroupNameDialog.dismiss();
                    hideInput(getActivity(),etInput);
                    if(StringUtils.isEmpty(etInput.getText().toString())){
                        ToastUtils.showToast(getActivity(),"群名称不能为空！");
                        return;
                    }
                    Map<String,Object> map = new HashMap<>();
                    GroupChatSetActivity.this.updateType = 1;
                    map.put("setType",1);
                    map.put("groupId",pUser.getPid());
                    map.put("groupSet",etInput.getText().toString());
                    groupName = etInput.getText().toString();
                    presenter.updateGroupInfo(map);
                }
            });
        }
        EditText etInput = updateGroupNameDialog.findViewById(R.id.et_input);
        etInput.setText("");
        updateGroupNameDialog.show();
    }

    private void showUpdateMyNameByGroupDialog(){
        if(updateMyNameByGroupDialog == null){
            updateMyNameByGroupDialog = new Dialog(this);
            updateMyNameByGroupDialog.setContentView(R.layout.dialog_update_group_info);
            TextView tvTitle = updateMyNameByGroupDialog.findViewById(R.id.tv_title);
            EditText etInput = updateMyNameByGroupDialog.findViewById(R.id.et_input);
            etInput.setFocusable(true);
            etInput.requestFocus();
            showInput(this,etInput);
            TextView tvCancel = updateMyNameByGroupDialog.findViewById(R.id.tv_cancel);
            TextView tvSure = updateMyNameByGroupDialog.findViewById(R.id.tv_sure);
            tvTitle.setText("我在本群的昵称");
            etInput.setHint("请输入昵称");
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateMyNameByGroupDialog.dismiss();
                    hideInput(getActivity(),etInput);
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateMyNameByGroupDialog.dismiss();
                    hideInput(getActivity(),etInput);
                    if(StringUtils.isEmpty(etInput.getText().toString())){
                        ToastUtils.showToast(getActivity(),"请输入昵称！");
                        return;
                    }
                    myGroupNickName = etInput.getText().toString();
                    Map<String,Object> map = new HashMap<>();
                    map.put("groupId",pUser.getPid());
                    map.put("nickName",etInput.getText().toString());
                    presenter.updateMyGroupNickName(map);
                }
            });
        }
        EditText etInput = updateMyNameByGroupDialog.findViewById(R.id.et_input);
        etInput.setText("");
        updateMyNameByGroupDialog.show();
    }

    private void showExitDialog() {
        if (exitDialog == null) {
            exitDialog = new Dialog(this);
            exitDialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = exitDialog.findViewById(R.id.tv_title);
            tvTitle.setText("确认退出群聊？");
            TextView tvSure = exitDialog.findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exitDialog.dismiss();
                    if(isCreate){//如果是群主退出群聊要移交群主
                        Intent intent = new Intent(getActivity(),TransferActivity.class);
                        intent.putExtra("groupId",pUser.getPid());
                        for(int i = 0;i<friendList.size();i++){
                            if(friendList.get(i).getUser_id() == UserUtils.getInstance().getUserBean().getUser_id()){
                                friendList.remove(i);
                            }
                        }
                        intent.putParcelableArrayListExtra("friendList", (ArrayList<? extends Parcelable>) friendList);
                        startActivity(intent);
                    }else{
                        Map<String,Object> map = new HashMap<>();
                        map.put("groupId",pUser.getPid());
                        presenter.exitGroup(map);
                    }
                }
            });
            TextView tvCancel = exitDialog.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exitDialog.dismiss();
                }
            });
        }
        exitDialog.show();
    }

    private void showDisbandDialog() {
        if (disbandDialog == null) {
            disbandDialog = new Dialog(this);
            disbandDialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = disbandDialog.findViewById(R.id.tv_title);
            tvTitle.setText("确认解散此群聊？");
            TextView tvSure = disbandDialog.findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disbandDialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("groupId",pUser.getPid());
                    presenter.disbandGroup(map);
                }
            });
            TextView tvCancel = disbandDialog.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disbandDialog.dismiss();
                }
            });
        }
        disbandDialog.show();
    }

    private void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
