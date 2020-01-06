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
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamPeopleAdapter extends RecyclerView.Adapter<TeamPeopleAdapter.TeamPeopleViewHolder>{

    private Context context;
    private List<TeamPeopleBean> data;

    public TeamPeopleAdapter(Context context, List<TeamPeopleBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TeamPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team_people,null);
        return new TeamPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamPeopleViewHolder holder, int position) {
        TeamPeopleBean teamPeopleBean = data.get(position);
        Glide.with(context).load(teamPeopleBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(teamPeopleBean.getReal_name());
        if(teamPeopleBean.getIs_create() == 1){
            holder.tvPosition.setText("主管理员");
            holder.tvPosition.setVisibility(View.VISIBLE);
        } else if (teamPeopleBean.getIs_master() == 1) {
            holder.tvPosition.setText("管理员");
            holder.tvPosition.setVisibility(View.VISIBLE);
        }else{
            holder.tvPosition.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(teamPeopleBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TeamPeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_position)
        TextView tvPosition;

        public TeamPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(TeamPeopleBean teamPeopleBean);
    }
}
