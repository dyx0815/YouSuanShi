package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentFriendAdapter extends RecyclerView.Adapter<RecentFriendAdapter.RecentFriendViewHolder> {

    private Context context;
    private List<ChatBean> data;

    public RecentFriendAdapter(Context context,List<ChatBean> data){
        this.context =context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecentFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address_book_recent_people,null);
        return new RecentFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentFriendViewHolder holder, int position) {
        ChatBean chatBean = data.get(position);
        Glide.with(context).load(chatBean.getUserIconUrl()).into(holder.rivHeadIcon);
        holder.tvName.setText(chatBean.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RecentFriendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;

        public RecentFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
