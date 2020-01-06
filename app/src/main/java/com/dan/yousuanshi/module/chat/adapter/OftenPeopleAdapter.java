package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OftenPeopleAdapter extends RecyclerView.Adapter<OftenPeopleAdapter.OftenPeopleViewHolder>{

    private Context context;
    private List<MyFriendBean> data;
    private List<MyFriendBean> chooseFriend = new ArrayList<>();
    private int type;

    public OftenPeopleAdapter(Context context, List<MyFriendBean> data,int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public OftenPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_often_people,null);
        return new OftenPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OftenPeopleViewHolder holder, int position) {
        MyFriendBean myFriendBean = data.get(position);
        holder.cbChoose.setChecked(myFriendBean.isChecked());
        if(myFriendBean.isChecked()){
            if(type == 4){
                holder.cbChoose.setEnabled(false);
                holder.itemView.setEnabled(false);
            }else{
                chooseFriend.add(myFriendBean);
            }
        }
        Glide.with(context).load(myFriendBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(myFriendBean.getNick_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cbChoose.setChecked(!holder.cbChoose.isChecked());
                if(holder.cbChoose.isChecked()){
                    chooseFriend.add(data.get(position));
                }else{
                    chooseFriend.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
                onItemClick.onItemClick(chooseFriend.size());
            }
        });
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbChoose.isChecked()){
                    chooseFriend.add(data.get(position));
                }else{
                    chooseFriend.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
                onItemClick.onItemClick(chooseFriend.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OftenPeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_choose)
        CheckBox cbChoose;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;

        public OftenPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int count);
    }

    public List<MyFriendBean> getChooseFriend() {
        return chooseFriend;
    }
}
