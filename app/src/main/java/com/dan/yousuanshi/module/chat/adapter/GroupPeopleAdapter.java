package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class GroupPeopleAdapter extends RecyclerView.Adapter<GroupPeopleAdapter.GroupPeopleViewHolder>{

    private Context context;
    private List<MyFriendBean> data;

    public GroupPeopleAdapter(Context context, List<MyFriendBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public GroupPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_people_info,null);
        return new GroupPeopleViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull GroupPeopleViewHolder holder, int position) {
        MyFriendBean myFriendBean = data.get(position);
        Glide.with(context).load(myFriendBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(myFriendBean.getNick_name());
        if (myFriendBean.isShowLetter()) {
            holder.tvPinyin.setText(String.valueOf(myFriendBean.getHeadLetter()));
            holder.llPinyin.setVisibility(View.VISIBLE);
        } else {
            holder.llPinyin.setVisibility(View.GONE);
        }
        if(myFriendBean.getIsCreate() == 1){
            holder.tvPosition.setText("群主");
        }else if(myFriendBean.getIsMaster() == 1){
            holder.tvPosition.setText("管理员");
        }else{
            holder.tvPosition.setText("");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setChecked(true);
                for(MyFriendBean item : data){
                    if(!item.equals(myFriendBean)){
                        item.setChecked(false);
                    }
                }
                notifyDataSetChanged();
                onItemClick.onItemClick(myFriendBean);
            }
        });
    }

    class GroupPeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_pinyin)
        LinearLayout llPinyin;
        @BindView(R.id.tv_pinyin)
        TextView tvPinyin;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_position)
        TextView tvPosition;

        public GroupPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(MyFriendBean myFriendBean);
    }

    public int getFirstPositionByChar(char sign) {
        if (sign == '#') {
            return 0;
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getHeadLetter() == sign) {
                return i;
            }
        }
        return -1;
    }
}
