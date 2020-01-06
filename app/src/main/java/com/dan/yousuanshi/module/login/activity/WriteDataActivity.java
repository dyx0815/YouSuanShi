package com.dan.yousuanshi.module.login.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.addressbook.activity.ChooseCountryActivity;
import com.dan.yousuanshi.module.addressbook.bean.CountryBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.login.presenter.WriteDataActivityPresenter;
import com.dan.yousuanshi.module.login.view.WriteDataActivityView;
import com.dan.yousuanshi.module.main.activity.MainActivity;
import com.dan.yousuanshi.utils.DpToPxUtils;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.MD5Utils;
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

public class WriteDataActivity extends BaseActivity<WriteDataActivityPresenter> implements WriteDataActivityView {

    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.et_nick_name)
    EditText etNickName;
    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private String picPath;
    private String picKey;
    private Dialog genderDialog;
    private CountryBean country;
    private CountryBean city;
    private CountryBean area;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_data;
    }

    @Nullable
    @Override
    protected WriteDataActivityPresenter initPresenter() {
        return new WriteDataActivityPresenter();
    }

    @Override
    protected void initView() {
        etNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isSubmit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etRealName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isSubmit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.riv_head_icon, R.id.ll_choose_gender, R.id.ll_choose_birthday, R.id.ll_choose_address, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                startActivity(new Intent(this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
            case R.id.riv_head_icon:
                openSysAlbum();
                break;
            case R.id.ll_choose_gender:
                showgenderDialog();
                break;
            case R.id.ll_choose_birthday:
                showDatePickerDialog();
                break;
            case R.id.ll_choose_address:
                Intent countryIntent = new Intent(this, ChooseCountryActivity.class);
                startActivityForResult(countryIntent,1);
                break;
            case R.id.tv_submit:
                Map<String,String> map = new HashMap<>();
                map.put("nickName",etNickName.getText().toString());
                map.put("realName",etRealName.getText().toString());
                if(!StringUtils.isEmpty(picKey)){
                    map.put("avatar",picKey);
                }
                if(!"选择性别".equals(tvGender.getText().toString())){//判断是否为界面上的字
                    if("男".equals(tvGender.getText().toString())){
                        map.put("sex","1");
                    }else if("女".equals(tvGender.getText().toString())){
                        map.put("sex","2");
                    }
                }
                if(!"选择生日".equals(tvBirthday.getText().toString())){
                    map.put("birthday",tvBirthday.getText().toString());
                }
                if(!"选择地区".equals(tvAddress.getText().toString())){
                    if (country.getName().equals("北京市") || country.getName().equals("天津市") || country.getName().equals("重庆市") || country.getName().equals("上海市")) {
                        map.put("location",country.getId()+"-"+city.getId()+"-"+"0");
                        map.put("locationStr",country.getName()+"-"+city.getName());
                    } else {
                        map.put("location",country.getId()+"-"+city.getId()+"-"+area.getId());
                        map.put("locationStr",country.getName()+"-"+city.getName()+"-"+area.getName());
                    }
                }
                presenter.writeData(map);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(data != null){
                cropPic(data.getData());
            }
        }
        if (requestCode == 10) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap bitmap = bundle.getParcelable("data");
                    picPath = FileUtils.saveFile(this, getFilesDir().getAbsolutePath() + "/crop/pic/", UserUtils.getInstance().getUserBean().getUser_id() + System.currentTimeMillis() + ".jpg", bitmap);
                    Log.e("MyInfoActivity", "裁剪图片地址->" + picPath);
                    presenter.getQiniuToken();
                }
            }
        }
        if(requestCode == 1&&resultCode == 3){
            country = data.getParcelableExtra("country");
            city = data.getParcelableExtra("city");
            area = data.getParcelableExtra("area");
            if (country.getName().equals("北京市") || country.getName().equals("天津市") || country.getName().equals("重庆市") || country.getName().equals("上海市")) {
                tvAddress.setText(country.getName() + "-" + city.getName());
            } else {
                tvAddress.setText(country.getName() + "-" + city.getName() + "-" + area.getName());
            }
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
    public void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean) {
        Log.e("MyInfoActivity", "后缀名：" + FileUtils.getExtensionName(picPath));
        UploadManager uploadManager = UploadManagerUtil.getInstance();
        uploadManager.put(picPath, Constant.QINIU_AVATAR +MD5Utils.getMd5Code(""+ UserUtils.getInstance().getUserBean().getUser_id() + System.currentTimeMillis()) + "." + FileUtils.getExtensionName(picPath),
                qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("MyInfoActivity", "上传成功!!!!!!!");
                            Log.e("MyInfoActivity", "qiniu::::::::::key:" + key);
                            picKey = key;
                            Glide.with(getActivity()).load(picPath).into(rivHeadIcon);
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
    public void writeDataSuccess(int code, List list) {
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void writeDataFailed(int code, String msg) {
        ToastUtils.showToast(this,"完善个人信息失败："+code+msg);
        Log.e("MyInfoActivity", "完善个人信息失败：" + code + "\t" + msg);
    }

    private void showgenderDialog() {
        if (genderDialog == null) {
            genderDialog = new Dialog(this);
            genderDialog.setContentView(R.layout.dialog_choose_gender);
            RadioButton rb_man = genderDialog.findViewById(R.id.rb_man);
            RadioButton rb_woman = genderDialog.findViewById(R.id.rb_woman);
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(),15), DpToPxUtils.dip2px(getActivity(),15));//设置大小  ，分别表示x ,y 宽，高
            Drawable drawable2 = getActivity().getResources().getDrawable(R.drawable.selector_choose_gender);//checkbox点击效果selector
            drawable2.setBounds(0, 0, DpToPxUtils.dip2px(getActivity(),15), DpToPxUtils.dip2px(getActivity(),15));//设置大小  ，分别表示x ,y 宽，高
            rb_man.setCompoundDrawables(drawable, null, null, null);//选择位置上下左右
            rb_woman.setCompoundDrawables(drawable2, null, null, null);//选择位置上下左右
            TextView tv_dissmiss = genderDialog.findViewById(R.id.tv_dissmiss);
            rb_man.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvGender.setText("男");
                    genderDialog.dismiss();
                }
            });
            rb_woman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvGender.setText("女");
                    genderDialog.dismiss();
                }
            });
            tv_dissmiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    genderDialog.dismiss();
                }
            });
        }
        genderDialog.show();
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
                tvBirthday.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();
    }

    private void isSubmit(){
        if(StringUtils.isEmpty(etNickName.getText().toString())){
//            ToastUtils.showToast(this,"请输入昵称！");
            tvSubmit.setEnabled(false);
            return;
        }
        if(StringUtils.isEmpty(etRealName.getText().toString())){
//            ToastUtils.showToast(this,"请输入真实姓名！");
            tvSubmit.setEnabled(false);
            return;

        }
        if(etRealName.getText().toString().length()<2){
//            ToastUtils.showToast(this,"真实姓名最少两位！");
            tvSubmit.setEnabled(false);
            return;
        }
        tvSubmit.setEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return true;
    }
}
