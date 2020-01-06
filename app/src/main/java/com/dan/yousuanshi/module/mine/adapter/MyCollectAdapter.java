package com.dan.yousuanshi.module.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.chat.activity.MyLocationActivity;
import com.dan.yousuanshi.module.chat.bean.LocationAddress;
import com.dan.yousuanshi.module.mine.activity.CollectInfoActivity;
import com.dan.yousuanshi.module.mine.bean.MyCollectBean;
import com.dan.yousuanshi.utils.FileItem;
import com.dan.yousuanshi.utils.FileUtils;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.rxhttp.download.DownloadInfo;
import per.goweii.rxhttp.download.RxDownload;
import pl.droidsonroids.gif.GifImageView;

public class MyCollectAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<MyCollectBean.DataBean> data;

    public MyCollectAdapter(Context context, List<MyCollectBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == Constant.CHAT_LOCATION){//位置
            View view = LayoutInflater.from(context).inflate(R.layout.item_collect_location,null);
            return new CollectLocationViewHolder(view);
        }else if(viewType == Constant.CHAT_VOICE){//语音
            View view = LayoutInflater.from(context).inflate(R.layout.item_collect_voice,null);
            return new CollectVoiceViewHolder(view);
        }else if(viewType == Constant.CHAT_PIC){
            View view = LayoutInflater.from(context).inflate(R.layout.item_collect_pic,null);
            return new CollectPicViewHolder(view);
        }else if(viewType == Constant.CHAT_VIDEO){
            View view = LayoutInflater.from(context).inflate(R.layout.item_collect_pic,null);
            return new CollectVideoViewHolder(view);
        }else if(viewType == Constant.CHAT_FILE){
            View view = LayoutInflater.from(context).inflate(R.layout.item_collect_file,null);
            return new CollectFileViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_collect_text,null);
            return new CollectTextViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyCollectBean.DataBean dataBean = data.get(position);
        if(holder instanceof CollectTextViewHolder){
            ((CollectTextViewHolder)holder).tvText.setText(dataBean.getContent());
            ((CollectTextViewHolder)holder).tvName.setText(dataBean.getSend_nick_name());
            ((CollectTextViewHolder)holder).tvDate.setText(dataBean.getCreate_time());
        }else if(holder instanceof CollectVoiceViewHolder){
            ((CollectVoiceViewHolder)holder).tvSize.setText(dataBean.getFile_size()+"秒");
            ((CollectVoiceViewHolder)holder).tvName.setText(dataBean.getSend_nick_name());
            ((CollectVoiceViewHolder)holder).tvDate.setText(dataBean.getCreate_time());
        }else if(holder instanceof CollectFileViewHolder){
            FileItem fileItem = new Gson().fromJson(dataBean.getContent(),FileItem.class);
            ((CollectFileViewHolder)holder).tvFileName.setText(fileItem.getFileName());
            ((CollectFileViewHolder)holder).ivFile.setImageResource(FileUtils.getFileIconByPath(fileItem.getFilePath()));
            ((CollectFileViewHolder)holder).tvSize.setText(FileUtils.formatFileSize(dataBean.getFile_size()));
            ((CollectFileViewHolder)holder).tvName.setText(dataBean.getSend_nick_name());
            ((CollectFileViewHolder)holder).tvDate.setText(dataBean.getCreate_time());
        }else if(holder instanceof CollectPicViewHolder){
            Glide.with(context).load(dataBean.getContent()).into(((CollectPicViewHolder)holder).ivPic);
            ((CollectPicViewHolder)holder).tvName.setText(dataBean.getSend_nick_name());
            ((CollectPicViewHolder)holder).tvDate.setText(dataBean.getCreate_time());
        }else if(holder instanceof CollectVideoViewHolder){
            String pathName = dataBean.getContent().replace(Constant.DOWNLOAD_URL+Constant.QINIU_COLLECT_VIDEO,"");
            if(FileUtils.isExists(context.getApplicationContext().getFilesDir().getAbsolutePath() + "/download/video/"+pathName)){
                Glide.with(context).load(context.getApplicationContext().getFilesDir().getAbsolutePath() + "/download/video/"+pathName).into(((CollectVideoViewHolder)holder).ivPic);
            }else {
                Glide.with(context).load(dataBean.getContent()).into(((CollectVideoViewHolder)holder).ivPic);
            }
            ((CollectVideoViewHolder)holder).ivStart.setVisibility(View.VISIBLE);
            ((CollectVideoViewHolder)holder).tvVideoTime.setText(dataBean.getFile_size()>10?"0:"+dataBean.getFile_size():"0:0"+dataBean.getFile_size());
            ((CollectVideoViewHolder)holder).tvVideoTime.setVisibility(View.VISIBLE);
            ((CollectVideoViewHolder)holder).tvName.setText(dataBean.getSend_nick_name());
            ((CollectVideoViewHolder)holder).tvDate.setText(dataBean.getCreate_time());
        }else if(holder instanceof CollectLocationViewHolder){
            LocationAddress locationAddress = new Gson().fromJson(dataBean.getContent(), LocationAddress.class);
            ((CollectLocationViewHolder)holder).tvLocation.setText(locationAddress.getLocationName());
            ((CollectLocationViewHolder)holder).tvAddress.setText(locationAddress.getLocationAddess());
            ((CollectLocationViewHolder)holder).tvName.setText(dataBean.getSend_nick_name());
            ((CollectLocationViewHolder)holder).tvDate.setText(dataBean.getCreate_time());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataBean.getFile_type() == Constant.CHAT_LOCATION){
                    Intent intent = new Intent(context, MyLocationActivity.class);
                    intent.putExtra("locationAddress",new Gson().fromJson(dataBean.getContent(),LocationAddress.class));
                    context.startActivity(intent);
                }else if(dataBean.getFile_type() == Constant.CHAT_FILE){
                    FileItem fileItem = new Gson().fromJson(dataBean.getContent(), FileItem.class);
                    if(FileUtils.isExists(context.getApplicationContext().getFilesDir().getAbsolutePath() + "/download/file/"+fileItem.getFileName())){
                        startFile(fileItem);
                    }else{
                        downLoadFile(fileItem);
                    }
                }else{
                    Intent intent = new Intent(context, CollectInfoActivity.class);
                    intent.putExtra("data",dataBean);
                    context.startActivity(intent);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClick.onItemClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CollectTextViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public CollectTextViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class CollectLocationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public CollectLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class CollectVoiceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_voice)
        ImageView ivVoice;
        @BindView(R.id.gif_voice)
        GifImageView gifVoice;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public CollectVoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class CollectPicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_img)
        RelativeLayout rlImage;
        @BindView(R.id.iv_pic)
        RoundedImageView ivPic;
        @BindView(R.id.iv_start)
        ImageView ivStart;
        @BindView(R.id.tv_video_time)
        TextView tvVideoTime;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public CollectPicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class CollectVideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_img)
        RelativeLayout rlImage;
        @BindView(R.id.iv_pic)
        RoundedImageView ivPic;
        @BindView(R.id.iv_start)
        ImageView ivStart;
        @BindView(R.id.tv_video_time)
        TextView tvVideoTime;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public CollectVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class CollectFileViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_file)
        ImageView ivFile;
        @BindView(R.id.tv_file_name)
        TextView tvFileName;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public CollectFileViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return data.get(position).getFile_type();
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int position);
    }

    private RxDownload downLoadFile(FileItem fileItem) {
        DownloadInfo downloadInfo = DownloadInfo.create(fileItem.getUrl()
                , context.getApplicationContext().getFilesDir().getAbsolutePath() + "/download/file/"
                , fileItem.getFileName());
        RxDownload rxDownload = RxDownload.create(downloadInfo).setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                Log.e("DownLoad", "下载开始：" + info.url);
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
                startFile(fileItem);
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                Log.e("DownLoad", "下载错误：" + e.toString());
            }
        });
        return rxDownload;
    }

    private void startFile(FileItem fileItem){
        if (fileItem.getFileType() == 1) {//文本文件
            context.startActivity(FileUtils.getTextFileIntent(fileItem.getFilePath()));
        } else if (fileItem.getFileType() == 2) {//doc
            context.startActivity(FileUtils.getWordFileIntent(fileItem.getFilePath()));
        } else if (fileItem.getFileType() == 3) {//xls
            context.startActivity(FileUtils.getExcelFileIntent(fileItem.getFilePath()));
        } else if (fileItem.getFileType() == 4) {//ppt
            context.startActivity(FileUtils.getPptFileIntent(fileItem.getFilePath()));
        }
    }
}
