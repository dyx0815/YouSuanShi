package com.dan.yousuanshi.module.addressbook.adapter;

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
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFriendAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<MyFriendBean> data;

    public MyFriendAdapter(Context context, List<MyFriendBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.item_my_friend_function,null);
            return new MyFriendFunctionViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_my_friend,null);
            return new MyFriendViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyFriendBean myFriendBean = data.get(position);
        if(holder instanceof MyFriendViewHolder){
            Glide.with(context).load(myFriendBean.getUser_portrait()).into(((MyFriendViewHolder)holder).rivHeadIcon);
            ((MyFriendViewHolder)holder).tvName.setText(myFriendBean.getNick_name());
            ((MyFriendViewHolder)holder).tvMessage.setText(myFriendBean.getC_name());
            if(myFriendBean.isShowLetter()){
                ((MyFriendViewHolder)holder).tvPinyin.setText(String.valueOf(myFriendBean.getHeadLetter()));
                ((MyFriendViewHolder)holder).llPinyin.setVisibility(View.VISIBLE);
            }else{
                ((MyFriendViewHolder)holder).llPinyin.setVisibility(View.GONE);
            }
            ((MyFriendViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(position);
                }
            });
        }else if(holder instanceof MyFriendFunctionViewHolder){
            ((MyFriendFunctionViewHolder)holder).ivAddressBook.setImageResource(myFriendBean.getUser_id());
            ((MyFriendFunctionViewHolder)holder).tvAddressBook.setText(myFriendBean.getNick_name());
            ((MyFriendFunctionViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.phoneAddressBook();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyFriendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_pinyin)
        LinearLayout llPinyin;
        @BindView(R.id.tv_pinyin)
        TextView tvPinyin;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_message)
        TextView tvMessage;


        public MyFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class MyFriendFunctionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_address_book)
        ImageView ivAddressBook;
        @BindView(R.id.tv_address_book)
        TextView tvAddressBook;


        public MyFriendFunctionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
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

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {

        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int position);
        void phoneAddressBook();
    }
}
