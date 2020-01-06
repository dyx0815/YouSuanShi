package com.dan.yousuanshi.module.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.addressbook.activity.ChooseCountryActivity;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.module.mine.presenter.MyInfoActivityPresenter;
import com.dan.yousuanshi.module.mine.view.MyInfoActivityView;
import com.dan.yousuanshi.utils.DpToPxUtils;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.MD5Utils;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UploadManagerUtil;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class MyInfoActivity extends BaseActivity<MyInfoActivityPresenter> implements MyInfoActivityView {

    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_you_number)
    TextView tvYouNumber;
    @BindView(R.id.tv_we_chat)
    TextView tvWeChat;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;

    private UserBean userBean;
    private String picPath;
    private Dialog chooseGenderDialog;
    private RadioButton rb_man = null;
    private RadioButton rb_woman = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Nullable
    @Override
    protected MyInfoActivityPresenter initPresenter() {
        return new MyInfoActivityPresenter();
    }

    @Override
    protected void initView() {
        userBean = UserUtils.getInstance().getUserBean();
        initUser();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getUser();
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this, getColor(R.color.white), 0);
    }


    @OnClick({R.id.ll_back, R.id.ll_head_icon, R.id.ll_nick_name, R.id.ll_name, R.id.ll_phone, R.id.ll_birthday, R.id.ll_gender
            , R.id.ll_country, R.id.ll_address, R.id.ll_you_number, R.id.ll_we_chat, R.id.ll_alipay, R.id.tv_phone})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, UpdateUserInfoActivity.class);
        Intent phoneIntent = new Intent(this, UpdateUserPhoneActivity.class);
        phoneIntent.putExtra("phone", tvPhone.getText().toString());
        switch (view.getId()) {
            case R.id.ll_back://返回
                finish();
                break;
            case R.id.ll_head_icon://头像
                openSysAlbum();
                break;
            case R.id.ll_nick_name://昵称
                intent.putExtra("type", "2");
                intent.putExtra("text", tvNickName.getText().toString());
                startActivityForResult(intent, 2);
                break;
            case R.id.ll_name://姓名
                intent.putExtra("type", "3");
                intent.putExtra("text", tvName.getText().toString());
                startActivityForResult(intent, 3);
                break;
            case R.id.ll_phone://电话
                startActivity(phoneIntent);
                break;
            case R.id.tv_phone:
                startActivity(phoneIntent);
                break;
            case R.id.ll_birthday://生日
                showDatePickerDialog();
                break;
            case R.id.ll_gender://性别
                showChooseGenderDialog();
                break;
            case R.id.ll_country://地区
                Intent countryIntent = new Intent(this, ChooseCountryActivity.class);
                countryIntent.putExtra("type", 1);
                startActivity(countryIntent);
                break;
            case R.id.ll_address://地址
                break;
            case R.id.ll_you_number://优号
                if (userBean.getIs_set_account() != 1) {
                    intent.putExtra("type", "6");
                    intent.putExtra("text", tvYouNumber.getText().toString());
                    startActivityForResult(intent, 6);
                } else {
                    ToastUtils.showToast(this, "优号只能改一次哦~");
                }
                break;
            case R.id.ll_we_chat://微信
                break;
            case R.id.ll_alipay://支付宝
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
                    picPath = FileUtils.saveFile(this, getFilesDir().getAbsolutePath() + "/crop/pic/", userBean.getUser_id() + System.currentTimeMillis() + ".jpg", bitmap);
                    Log.e("MyInfoActivity", "裁剪图片地址->" + picPath);
                    presenter.getQiniuToken();
                }
            }
        }
        if (requestCode == 2 && resultCode == 10) {//昵称
            String text = data.getStringExtra("text");
            tvNickName.setText(text);
        } else if (requestCode == 3 && resultCode == 10) {//姓名
            String text = data.getStringExtra("text");
            tvName.setText(text);
        } else if (requestCode == 6 && resultCode == 10) {//优号
            String text = data.getStringExtra("text");
            tvYouNumber.setText(text);
        }
    }

    @Override
    public void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean) {
        ToastUtils.showToast(this, "请求七牛Token成功");
        Log.e("MyInfoActivity", "后缀名：" + FileUtils.getExtensionName(picPath));
        UploadManager uploadManager = UploadManagerUtil.getInstance();
        uploadManager.put(picPath, Constant.QINIU_AVATAR +MD5Utils.getMd5Code(""+ userBean.getUser_id() + System.currentTimeMillis()) + "." + FileUtils.getExtensionName(picPath),
                qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("MyInfoActivity", "上传成功!!!!!!!");
                            Log.e("MyInfoActivity", "qiniu::::::::::key:" + key);
                            Map<String, String> map = new HashMap<>();
                            map.put("setType", "1");
                            map.put("setValue", key);
                            presenter.updateUserInfo(map);
                        } else {
                            Log.e("MyInfoActivity", "上传失败!!!!!!!");
                            Log.e("MyInfoActivity", "json:" + response + "\t" + info.error);
                        }
                    }
                }, null);
    }

    @Override
    public void getQiniuTokenFailed(int code, String message) {
        Log.e("MyInfoActivity", "获取七牛token失败：" + code + "\t" + message);
    }

    @Override
    public void updateUserInfoSuccess(int code, List list) {
        if (!StringUtils.isEmpty(picPath)) {
            Glide.with(this).load(picPath).into(rivHeadIcon);
        }else{
            presenter.getUser();
        }
    }

    @Override
    public void updateUserInfoFailed(int code, String msg) {
        Log.e("MyInfoActivity", "更新用户信息失败：" + code + "\t" + msg);
    }

    @Override
    public void getUserSuccess(int code, UserBean userBean) {
        ToastUtils.showToast(this, "刷新用户信息成功");
        this.userBean = userBean;
        initUser();
    }

    @Override
    public void getUserFailed(int code, String msg) {
        Log.e("MyInfoActivity", "获取用户信息失败：" + code + "\t" + msg);
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

    private void initUser() {
        Glide.with(this).load(userBean.getUser_portrait()).into(rivHeadIcon);
        tvNickName.setText(userBean.getNick_name());
        tvName.setText(userBean.getReal_name());
        tvPhone.setText(userBean.getUser_tel());
        tvBirthday.setText(userBean.getBirthday());
        switch (userBean.getSex()) {
            case 0:
                tvGender.setText("未知");
                break;
            case 1:
                tvGender.setText("男");
                break;
            case 2:
                tvGender.setText("女");
                break;
        }
        if (StringUtils.isEmpty(userBean.getLocation())) {
            tvCountry.setText("请选择地区");
        } else {
            tvCountry.setText(userBean.getLocation());
        }
        tvAddress.setText(userBean.getAddress());
        tvYouNumber.setText(userBean.getUser_account());
        if ("0".equals(userBean.getUser_openid())) {
            tvWeChat.setText("未绑定");
        } else {

        }
        if ("0".equals(userBean.getUser_alipay_id())) {
            tvAlipay.setText("未绑定");
        }
    }

    private void showChooseGenderDialog() {
        if (chooseGenderDialog == null) {
            chooseGenderDialog = new Dialog(this);
            chooseGenderDialog.setContentView(R.layout.dialog_choose_gender);
            rb_man = chooseGenderDialog.findViewById(R.id.rb_man);
            rb_woman = chooseGenderDialog.findViewById(R.id.rb_woman);
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(),15), DpToPxUtils.dip2px(getActivity(),15));//设置大小  ，分别表示x ,y 宽，高
            Drawable drawable2 = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable2.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(),15), DpToPxUtils.dip2px(getActivity(),15));//设置大小  ，分别表示x ,y 宽，高
            rb_man.setCompoundDrawables(drawable, null, null, null);//选择位置上下左右
            rb_woman.setCompoundDrawables(drawable2, null, null, null);//选择位置上下左右
            TextView tv_dissmiss = chooseGenderDialog.findViewById(R.id.tv_dissmiss);
            rb_man.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<>();
                    map.put("setType", "5");
                    map.put("setValue", "1");
                    tvGender.setText("男");
                    presenter.updateUserInfo(map);
                    chooseGenderDialog.dismiss();
                }
            });
            rb_woman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<>();
                    map.put("setType", "5");
                    map.put("setValue", "2");
                    tvGender.setText("女");
                    presenter.updateUserInfo(map);
                    chooseGenderDialog.dismiss();
                }
            });
            tv_dissmiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseGenderDialog.dismiss();
                }
            });
        }
        if (userBean.getSex() == 1) {
            rb_man.setChecked(true);
        } else if (userBean.getSex() == 2) {
            rb_woman.setChecked(true);
        }else{
            rb_man.setChecked(false);
            rb_woman.setChecked(false);
        }
        chooseGenderDialog.show();
    }

    private void showDatePickerDialog() {
        DatePicker picker = new DatePicker(this);
        picker.setTextColor(getColor(R.color.color_F99E05));
        picker.setLabelTextColor(getColor(R.color.color_F99E05));
        picker.setCancelTextColor(getColor(R.color.color_F99E05));
        picker.setSubmitTextColor(getColor(R.color.color_F99E05));
        picker.setDividerColor(getColor(R.color.color_F99E05));
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setCycleDisable(false);
        picker.setTopLineVisible(false);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        Calendar calendar = Calendar.getInstance();
        picker.setRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setRangeStart(1949, 1, 1);
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                Map<String, String> map = new HashMap<>();
                map.put("setType", "4");
                map.put("setValue", year + "-" + month + "-" + day);
                presenter.updateUserInfo(map);
            }
        });
//        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
//            @Override
//            public void onYearWheeled(int index, String year) {
//                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
//            }
//
//            @Override
//            public void onMonthWheeled(int index, String month) {
//                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
//            }
//
//            @Override
//            public void onDayWheeled(int index, String day) {
//                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
//            }
//        });
        picker.show();
    }
}
