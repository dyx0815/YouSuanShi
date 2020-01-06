package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupAdminAdapter extends RecyclerView.Adapter<GroupAdminAdapter.GroupAdminViewHolder>{

    private Context context;
    private List<MyFriendBean> data;
    private boolean isCreate;

    public GroupAdminAdapter(Context context, List<MyFriendBean> data,boolean isCreate) {
        this.context = context;
        this.data = data;
        this.isCreate = isCreate;
    }

    @NonNull
    @Override
    public GroupAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_admin,null);
        return new GroupAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdminViewHolder holder, int position) {
        MyFriendBean myFriendBean = data.get(position);
        Glide.with(context).load(myFriendBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(myFriendBean.getNick_name());
        if(isCreate){
            holder.tvRemove.setVisibility(View.VISIBLE);
        }else{
            holder.tvRemove.setVisibility(View.GONE);
        }
        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(myFriendBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class GroupAdminViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_remove)
        TextView tvRemove;

        public GroupAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(MyFriendBean myFriendBean);
    }
}
