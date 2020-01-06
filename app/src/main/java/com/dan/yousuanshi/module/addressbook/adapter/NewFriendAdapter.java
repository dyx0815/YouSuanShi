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
import com.dan.yousuanshi.module.addressbook.bean.NewFriendBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewFriendAdapter extends RecyclerView.Adapter<NewFriendAdapter.NewFriendViewHolder> {

    private Context context;
    private List<NewFriendBean.DataBean> data;

    public NewFriendAdapter(Context context, List<NewFriendBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public NewFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_new_friend, null);
        return new NewFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewFriendViewHolder holder, final int position) {
        NewFriendBean.DataBean dataBean = data.get(position);
        Glide.with(context).load(dataBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(dataBean.getNick_name());
        holder.tvMessage.setText(dataBean.getSend_msg());
        if(dataBean.getIs_pass() == 0){
            holder.tvYes.setVisibility(View.VISIBLE);
            holder.tvYesNow.setVisibility(View.GONE);
        }else if(dataBean.getIs_pass() == 1){
            holder.tvYes.setVisibility(View.GONE);
            holder.tvYesNow.setVisibility(View.VISIBLE);
        }
        holder.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.yes(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewFriendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.tv_yes)
        TextView tvYes;
        @BindView(R.id.tv_yes_now)
        TextView tvYesNow;

        public NewFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void yes(View v,int position);
    }
}
