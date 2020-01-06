package com.dan.yousuanshi.module.addressbook.adapter;

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

public class AddTeamPeopleAdapter extends RecyclerView.Adapter<AddTeamPeopleAdapter.AddTeamPeopleViewHolder>{

    private List<MyFriendBean> data;
    private Context context;

    public AddTeamPeopleAdapter(List<MyFriendBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AddTeamPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_team_people,null);
        return new AddTeamPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddTeamPeopleViewHolder holder, int position) {
        MyFriendBean myFriendBean = data.get(position);
        Glide.with(context).load(myFriendBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(myFriendBean.getNick_name());
        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.deletePeople(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AddTeamPeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;

        public AddTeamPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void deletePeople(int position);
    }
}
