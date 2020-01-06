package com.dan.yousuanshi.module.mine.activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.mine.bean.MyCollectBean;
import com.dan.yousuanshi.module.mine.presenter.CollectInfoActivityPresenter;
import com.dan.yousuanshi.module.mine.view.CollectInfoActivityView;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.download.DownloadInfo;
import per.goweii.rxhttp.download.RxDownload;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class CollectInfoActivity extends BaseActivity<CollectInfoActivityPresenter> implements CollectInfoActivityView {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.ll_voice)
    LinearLayout llVoice;
    @BindView(R.id.iv_voice)
    ImageView ivVoice;
    @BindView(R.id.gif_voice)
    GifImageView gifVoice;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.ll_ac_more)
    LinearLayout llAcMore;

    private MyCollectBean.DataBean data;
    private String pathName;
    private MediaPlayer mediaPlayer;
    private PopupWindow popupWindow;
    private Dialog deleteDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect_info;
    }

    @Nullable
    @Override
    protected CollectInfoActivityPresenter initPresenter() {
        return new CollectInfoActivityPresenter();
    }

    @Override
    protected void initView() {
        data = getIntent().getParcelableExtra("data");
        tvName.setText(data.getSend_nick_name());
        tvDate.setText(data.getCreate_time());
        if(data.getFile_type() == Constant.CHAT_TEXT){
            tvText.setText(data.getContent());
            tvText.setVisibility(View.VISIBLE);
        }else if(data.getFile_type() == Constant.CHAT_VOICE){
            tvSize.setText(data.getFile_size()+"秒");
            llVoice.setVisibility(View.VISIBLE);
        }else if(data.getFile_type() == Constant.CHAT_PIC){
            Glide.with(this).load(data.getContent()).into(ivPic);
            ivPic.setVisibility(View.VISIBLE);
        }else if(data.getFile_type() == Constant.CHAT_VIDEO){
            pathName = data.getContent().replace(Constant.DOWNLOAD_URL+Constant.QINIU_COLLECT_VIDEO,"");
            if(FileUtils.isExists(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/video/"+pathName)){
                videoView.setVideoPath(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/video/"+pathName);
                videoView.setVisibility(View.VISIBLE);
                videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        videoView.start();
                    }
                });
            }else{
                downLoadVideo().start();
            }
        }
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_ac_more,R.id.ll_voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_ac_more:
                showPopupwindow();
                break;
            case R.id.ll_voice:
                startVoice(gifVoice,ivVoice,data.getContent());
                break;
        }
    }

    private RxDownload downLoadVideo() {
        DownloadInfo downloadInfo = DownloadInfo.create(data.getContent()
                , getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/video/"
                , pathName);
        RxDownload rxDownload = RxDownload.create(downloadInfo).setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                Log.e("DownLoad", "下载开始：" + info.url);
                presenter.showLoadingDialog();
            }

            @Override
            public void onDownloading(DownloadInfo info) {
                Log.e("DownLoad", "正在下载");
            }

            @Override
            public void onStopped(DownloadInfo info) {
                Log.e("DownLoad", "下载停止");

            }

            @Override
            public void onCanceled(DownloadInfo info) {
                Log.e("DownLoad", "下载取消");
            }

            @Override
            public void onCompletion(DownloadInfo info) {
                Log.e("DownLoad", "下载完成");
                presenter.dismissLoadingDialog();
                videoView.setVideoPath(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + "/download/video/"+pathName);
                videoView.setVisibility(View.VISIBLE);
                videoView.start();
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                Log.e("DownLoad", "下载错误：" + e.toString());
                presenter.dismissLoadingDialog();
                ToastUtils.showToast(getActivity(),"视频资源丢失！");
                finish();
            }
        });
        return rxDownload;
    }

    private void startVoice(final GifImageView gifImageView, final ImageView imageView, String path) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        final GifDrawable gifDrawable = (GifDrawable) gifImageView.getDrawable();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e("ChatAdapter", e.toString());
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("ChatAdapter", "播放准备");
                gifImageView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                gifDrawable.start();
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("ChatAdapter", "gif播放完毕");
                gifDrawable.stop();
                gifDrawable.reset();
                gifImageView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e("ChatAdapter", "语音播放异常");
                gifDrawable.stop();
                gifDrawable.reset();
                return true;
            }
        });
    }

    @Override
    public void deleteCollectSuccess(int code, List list) {
        ToastUtils.showToast(this,"删除成功！");
        finish();
    }

    @Override
    public void deleteCollectFailed(int code, String msg) {
        ToastUtils.showToast(this,"删除收藏失败！"+code+msg);
    }

    private void showPopupwindow(){
        if(popupWindow == null){
            View view = LayoutInflater.from(this).inflate(R.layout.layout_collect_window,null);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setTouchable(true);//设置可以触摸
            popupWindow.setOutsideTouchable(true);//点击外面可以隐藏window
            ColorDrawable dw = new ColorDrawable(0x00000000);//设置背景为透明
            popupWindow.setBackgroundDrawable(dw);
            TextView tvForward = view.findViewById(R.id.tv_forward);
            tvForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            TextView tvDelete = view.findViewById(R.id.tv_delete);
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    showDeleteDialog();
                }
            });
        }
        popupWindow.showAsDropDown(llAcMore);
    }

    private void showDeleteDialog(){
        if(deleteDialog == null){
            deleteDialog = new Dialog(this);
            deleteDialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = deleteDialog.findViewById(R.id.tv_title);
            TextView tvSure = deleteDialog.findViewById(R.id.tv_sure);
            TextView tvCancel = deleteDialog.findViewById(R.id.tv_cancel);
            tvTitle.setText("确认删除此收藏？");
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("collectionId",data.getId());
                    presenter.deleteCollect(map);
                }
            });
        }
        deleteDialog.show();
    }
}
