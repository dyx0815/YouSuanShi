package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.addressbook.bean.MyGroupBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGroupAdapter extends RecyclerView.Adapter<MyGroupAdapter.MyGroupViewHolder> {

    private Context context;
    private List<MyGroupBean> data;

    public MyGroupAdapter(Context context, List<MyGroupBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_group, null);
        return new MyGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGroupViewHolder holder, int position) {
        MyGroupBean myGroupBean = data.get(position);
        Glide.with(context).load(myGroupBean.getGroup_avatar()).into(holder.rivHeadIcon);
        holder.tvName.setText(myGroupBean.getGroup_name());
        if (Constant.ORDINARY_GROUP == myGroupBean.getGroup_type()) {//普通群
            holder.ivChatType.setVisibility(View.GONE);
        } else if (Constant.ALL_PEOPLE_GROUP == myGroupBean.getGroup_type()) {//全员群
            holder.ivChatType.setImageResource(R.mipmap.icon_message_all);
            holder.ivChatType.setVisibility(View.VISIBLE);
        } else if (Constant.DEPARTMENT_GROUP == myGroupBean.getGroup_type()) {//部门群
            holder.ivChatType.setImageResource(R.mipmap.icon_message_department);
            holder.ivChatType.setVisibility(View.VISIBLE);
        } else if (Constant.INTERNAL_GROUP == myGroupBean.getGroup_type()) {//内部群
            holder.ivChatType.setImageResource(R.mipmap.icon_message_internal);
            holder.ivChatType.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyGroupViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_chat_type)
        ImageView ivChatType;

        public MyGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(int position);
    }
}
