package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.GroupAnnouncementBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupAnnouncementAdapter extends RecyclerView.Adapter<GroupAnnouncementAdapter.GroupAnnouncementViewHolder>{

    private Context context;
    private List<GroupAnnouncementBean.DataBean> data;
    private PopupWindow popupWindow;
    private boolean isMaster;

    public GroupAnnouncementAdapter(Context context, List<GroupAnnouncementBean.DataBean> data,boolean isMaster) {
        this.context = context;
        this.data = data;
        this.isMaster = isMaster;
    }

    @NonNull
    @Override
    public GroupAnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_announcement,null);
        return new GroupAnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAnnouncementViewHolder holder, int position) {
        GroupAnnouncementBean.DataBean dataBean = data.get(position);
        Glide.with(context).load(dataBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(dataBean.getNick_name());
        holder.tvDate.setText(dataBean.getCreate_time());
        holder.tvAnnouncement.setText(dataBean.getContent());
        if(isMaster){
            holder.llMore.setVisibility(View.VISIBLE);
            holder.llMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupwindow(holder.ivMore,dataBean,position);
                }
            });
        }else{
            holder.llMore.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class GroupAnnouncementViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_announcement)
        TextView tvAnnouncement;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.ll_more)
        LinearLayout llMore;

        public GroupAnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void showPopupwindow(ImageView imageView,GroupAnnouncementBean.DataBean dataBean,int position){
        if(popupWindow == null){
            View view = LayoutInflater.from(context).inflate(R.layout.layout_announcement_window,null);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setTouchable(true);//设置可以触摸
            popupWindow.setOutsideTouchable(true);//点击外面可以隐藏window
            ColorDrawable dw = new ColorDrawable(0x00000000);//设置背景为透明
            popupWindow.setBackgroundDrawable(dw);
            TextView tvUpdate = view.findViewById(R.id.tv_update);
            TextView tvDelete = view.findViewById(R.id.tv_delete);
            tvUpdate.setOnClickListener(new View.OnClickListener() {//修改公告
                @Override
                public void onClick(View v) {
                    onItemClick.updateAnnouncement(dataBean);
                    popupWindow.dismiss();
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {//删除公告
                @Override
                public void onClick(View v) {
                    onItemClick.deleteAnnouncement(dataBean.getId(),position);
                    popupWindow.dismiss();
                }
            });
        }
        popupWindow.showAsDropDown(imageView);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void updateAnnouncement(GroupAnnouncementBean.DataBean dataBean);
        void deleteAnnouncement(int id,int position);
    }
}
