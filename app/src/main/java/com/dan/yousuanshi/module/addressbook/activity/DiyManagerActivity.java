package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.addressbook.presenter.DiyManagerActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.DiyManagerActivityView;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.MD5Utils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UploadManagerUtil;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DiyManagerActivity extends BaseActivity<DiyManagerActivityPresenter> implements DiyManagerActivityView {

    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;

    String picPath;
    private int updateType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_diy_manager;
    }

    @Nullable
    @Override
    protected DiyManagerActivityPresenter initPresenter() {
        return new DiyManagerActivityPresenter();
    }

    @Override
    protected void initView() {
        Glide.with(this).load(UserUtils.getInstance().getUserBean().getCompany_logo()).into(rivHeadIcon);
        tvTeamName.setText(UserUtils.getInstance().getUserBean().getC_name());
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void updateTeamInfoSuccess(int code, List list) {
        if(updateType == 2){
            Glide.with(this).load(picPath).into(rivHeadIcon);
        }
    }

    @Override
    public void updateTeamInfoFailed(int code, String msg) {
        ToastUtils.showToast(this,"修改公司信息失败；"+code+msg);
        Log.e("DiyManager","修改公司信息失败；"+code+msg);
    }

    @Override
    public void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean) {
        ToastUtils.showToast(this, "请求七牛Token成功");
        Log.e("MyInfoActivity", "后缀名：" + FileUtils.getExtensionName(picPath));
        UploadManager uploadManager = UploadManagerUtil.getInstance();
        uploadManager.put(picPath, MD5Utils.getMd5Code(Constant.QINIU_PIC + UserUtils.getInstance().getUserBean().getUser_company() + System.currentTimeMillis()) + "." + FileUtils.getExtensionName(picPath),
                qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("MyInfoActivity", "上传成功!!!!!!!");
                            Log.e("MyInfoActivity", "qiniu::::::::::key:" + key);
                            Map<String, Object> map = new HashMap<>();
                            map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
                            map.put("changeType", 2);
                            map.put("changeValue",key);
                            presenter.updateTeamInfo(map);
                            updateType = 2;
                        } else {
                            Log.e("MyInfoActivity", "上传失败!!!!!!!");
                            Log.e("MyInfoActivity", "json:" + response + "\t" + info.error);
                        }
                    }
                }, null);
    }

    @Override
    public void getQiniuTokenFailed(int code, String message) {
        ToastUtils.showToast(this,"获取七牛token失败；"+code+message);
        Log.e("DiyManager","获取七牛token失败；"+code+message);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.ll_head_icon,R.id.ll_team_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                break;
            case R.id.ll_head_icon:
                openSysAlbum();
                break;
            case R.id.ll_team_name:
                startActivityForResult(new Intent(this,UpdateTeamNameActivity.class),4);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(data !=null){
                cropPic(data.getData());
            }
        }
        if (requestCode == 10) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap bitmap = bundle.getParcelable("data");
//                    rivHeadIcon.setImageBitmap(bitmap);
                    picPath = FileUtils.saveFile(this, getFilesDir().getAbsolutePath() + "/crop/pic/", UserUtils.getInstance().getUserBean().getUser_company() + System.currentTimeMillis() + ".jpg", bitmap);
                    Log.e("MyInfoActivity", "裁剪图片地址->" + picPath);
                    presenter.getQiniuToken();
                }
            }
        }
        if(requestCode ==4&&resultCode == 1){
            tvTeamName.setText(data.getStringExtra("name"));
            Map<String, Object> map = new HashMap<>();
            map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
            map.put("changeType", 1);
            map.put("changeValue",tvTeamName.getText().toString());
            presenter.updateTeamInfo(map);
            updateType = 1;
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
}
