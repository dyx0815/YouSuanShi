package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.FileUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupFileAdapter extends RecyclerView.Adapter<GroupFileAdapter.GroupFileViewHolder>{

    private Context context;
    private List<GroupFileBean.DataBean> data;
    private boolean isMaster;

    public GroupFileAdapter(Context context, List<GroupFileBean.DataBean> data,boolean isMaster) {
        this.context = context;
        this.data = data;
        this.isMaster = isMaster;
    }

    @NonNull
    @Override
    public GroupFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_file,null);
        return new GroupFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupFileViewHolder holder, int position) {
        GroupFileBean.DataBean dataBean = data.get(position);
        holder.tvFileName.setText(dataBean.getFile_name());
        holder.ivFileType.setImageResource(FileUtils.getFileIconByPath(dataBean.getFile_name()));
        holder.tvSize.setText(FileUtils.formatFileSize(dataBean.getFile_size()));
        holder.tvTime.setText(DateUtil.showTime(DateUtil.StringToDate(dataBean.getCreate_time()),new Date()));
        holder.llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.llMoreClick(position);
            }
        });
        if(isMaster){
            holder.llMore.setVisibility(View.VISIBLE);
        }else{
            holder.llMore.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.lookFile(dataBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class GroupFileViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_file_type)
        ImageView ivFileType;
        @BindView(R.id.tv_file_name)
        TextView tvFileName;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_more)
        LinearLayout llMore;


        public GroupFileViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void llMoreClick(int position);
        void lookFile(GroupFileBean.DataBean dataBean);
    }
}
