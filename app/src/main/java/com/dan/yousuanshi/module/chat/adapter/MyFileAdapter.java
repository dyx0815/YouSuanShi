package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.utils.FileItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFileAdapter extends RecyclerView.Adapter<MyFileAdapter.MyFileViewHolder> {

    private Context context;
    private List<FileItem> data;
    private List<FileItem> checkedFile = new ArrayList<>();

    public MyFileAdapter(Context context, List<FileItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_file, null);
        return new MyFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyFileViewHolder holder, final int position) {
        final FileItem fileItem = data.get(position);
        holder.ivFileType.setImageResource(fileItem.getFilePic());
        holder.tvFileName.setText(fileItem.getFileName());
        holder.tvSize.setText(formatFileSize(fileItem.getSize()));
        holder.tvTime.setText(fileItem.getFileModifiedTime());
        holder.cbChoose.setChecked(fileItem.isChecked());
        holder.llCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cbChoose.setChecked(holder.cbChoose.isChecked());
                if(holder.cbChoose.isChecked()){
                    checkedFile.add(fileItem);
                }else{
                    checkedFile.remove(fileItem);
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(v, position);
            }
        });
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbChoose.isChecked()){
                    checkedFile.add(fileItem);
                }else{
                    checkedFile.remove(fileItem);
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyFileViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_file_type)
        ImageView ivFileType;
        @BindView(R.id.tv_file_name)
        TextView tvFileName;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.cb_choose)
        CheckBox cbChoose;
        @BindView(R.id.rl_all)
        RelativeLayout itemView;
        @BindView(R.id.ll_cb_box)
        LinearLayout llCheckBox;


        public MyFileViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    private OnItemClick onItemClick;

    public interface OnItemClick {
        void onItemClick(View view, int position);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public List<FileItem> getCheckedFile() {
        return checkedFile;
    }
}
