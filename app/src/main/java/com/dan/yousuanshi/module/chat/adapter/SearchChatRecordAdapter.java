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

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.utils.DateUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchChatRecordAdapter extends RecyclerView.Adapter<SearchChatRecordAdapter.SearchChatRecordViewHolder>{

    private Context context;
    private List<ChatBean> data;

    public SearchChatRecordAdapter(Context context, List<ChatBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SearchChatRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message,null);
        return new SearchChatRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchChatRecordViewHolder holder, int position) {
        ChatBean chatBean = data.get(position);
        holder.tvName.setText(chatBean.getName());//昵称
        holder.tvMessage.setText(chatBean.getStxt());//消息
        Glide.with(context).load(chatBean.getUserIconUrl()).into(holder.rivHeadIcon);//用户头像
        holder.tvTime.setText(DateUtil.showTime(DateUtil.StringToDate(chatBean.getTime()), new Date()));//时间
        holder.tvTime.setTextColor(context.getColor(R.color.color_F99E05));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(chatBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SearchChatRecordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.iv_is_top)
        ImageView ivIsTop;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_message_count)
        TextView tvMessageCount;
        @BindView(R.id.ll_top)
        LinearLayout llTop;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.ll_remove_top)
        LinearLayout llRemoveTop;
        @BindView(R.id.iv_chat_type)
        ImageView ivChatType;

        public SearchChatRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(ChatBean chatBean);
    }
}
