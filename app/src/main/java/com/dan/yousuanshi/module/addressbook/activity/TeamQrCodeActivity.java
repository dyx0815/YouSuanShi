package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.TeamQrCodeBean;
import com.dan.yousuanshi.module.addressbook.presenter.TeamQrCodeActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.TeamQrCodeActivityView;
import com.dan.yousuanshi.module.login.bean.UserBean;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamQrCodeActivity extends BaseActivity<TeamQrCodeActivityPresenter> implements TeamQrCodeActivityView {

    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;

    private Bitmap logo;
    private Bitmap qrCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_qr_code;
    }

    @Nullable
    @Override
    protected TeamQrCodeActivityPresenter initPresenter() {
        return new TeamQrCodeActivityPresenter();
    }

    @Override
    protected void initView() {
        UserBean userBean = UserUtils.getInstance().getUserBean();
        Glide.with(this).load(userBean.getCompany_logo()).into(rivHeadIcon);
        tvNickName.setText(userBean.getC_name());
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",userBean.getUser_company());
        presenter.getTeamQrcode(map);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getTeamQrCodeSuccess(int code, TeamQrCodeBean data) {
        ivQrCode.setImageBitmap(CodeCreator.createQRCode(data.getUrl(), 400, 400,null));
    }

    @Override
    public void getTeamQrCodeFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_save_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_save_phone:
                if(FileUtils.isFolderExists(Environment.getExternalStorageDirectory().getPath()+"/yousuanshi")){
                    Bitmap bitmap = getViewBitmap(ivQrCode);
                    File imageFile = new File(Environment.getExternalStorageDirectory().getPath()+"/yousuanshi", UserUtils.getInstance().getUserBean().getC_name() + ".png");
                    FileOutputStream outStream;
                    try {
                        outStream = new FileOutputStream(imageFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                        outStream.flush();
                        outStream.close();
                        ToastUtils.showToast(this,"保存路径为："+Environment.getExternalStorageDirectory().getPath()+"/yousuanshi");
                        // 通知相册有新图片
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(imageFile);
                        intent.setData(uri);
                        sendBroadcast(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 某些机型直接获取会为null,在这里处理一下防止国内某些机型返回null
     */
    private Bitmap getViewBitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
//    public Bitmap loadQRCode() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                URL imageurl = null;
//                try {
//                    imageurl = new URL(UserUtils.getInstance().getUserBean().getUser_portrait());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
//                    conn.setDoInput(true);
//                    conn.connect();
//                    InputStream is = conn.getInputStream();
//                    logo = BitmapFactory.decodeStream(is);
//                    is.close();
//                    qrCode = CodeCreator.createQRCode(UserUtils.getInstance().getUserBean().getUser_account(), 400, 400, logo);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ivQrCode.setImageBitmap(qrCode);
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        return logo;
//    }
}
