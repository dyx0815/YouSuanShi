package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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

public class ChoosePeopleAdapter extends RecyclerView.Adapter<ChoosePeopleAdapter.ChoosePeopleViewHolder>{

    private Context context;
    private List<MyFriendBean> data;
    private List<MyFriendBean> checkedFriend = new ArrayList<>();

    public ChoosePeopleAdapter(Context context, List<MyFriendBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ChoosePeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_people,null);
        return new ChoosePeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoosePeopleViewHolder holder, int position) {
        MyFriendBean myFriendBean = data.get(position);
        Glide.with(context).load(myFriendBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(myFriendBean.getNick_name());
        if (myFriendBean.isShowLetter()) {
            holder.tvPinyin.setText(String.valueOf(myFriendBean.getHeadLetter()));
            holder.llPinyin.setVisibility(View.VISIBLE);
        } else {
            holder.llPinyin.setVisibility(View.GONE);
        }
        if(myFriendBean.getIsCreate() == 1 || myFriendBean.getIsMaster() == 1){
            holder.cbChoose.setChecked(true);
            holder.cbChoose.setEnabled(false);
            holder.itemView.setEnabled(false);
        }
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbChoose.isChecked()){
                    checkedFriend.add(data.get(position));
                }else{
                    checkedFriend.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
                onItemClick.onItemClick(checkedFriend.size());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cbChoose.setChecked(!holder.cbChoose.isChecked());
                if(holder.cbChoose.isChecked()){
                    checkedFriend.add(data.get(position));
                }else{
                    checkedFriend.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
                onItemClick.onItemClick(checkedFriend.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChoosePeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_pinyin)
        LinearLayout llPinyin;
        @BindView(R.id.tv_pinyin)
        TextView tvPinyin;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.cb_choose)
        CheckBox cbChoose;

        public ChoosePeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public List<MyFriendBean> getCheckedFriend() {
        return checkedFriend;
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int count);
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
