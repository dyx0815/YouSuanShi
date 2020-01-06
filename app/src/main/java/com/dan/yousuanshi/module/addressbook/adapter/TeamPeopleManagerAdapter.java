package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.dan.yousuanshi.module.addressbook.activity.EditTeamPeopleActivity;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamPeopleManagerAdapter extends RecyclerView.Adapter<TeamPeopleManagerAdapter.TeamPeopleViewHolder>{

    private Context context;
    private List<TeamPeopleBean> data;
    private int type = 0; //2为批量操作
    private List<TeamPeopleBean> checkedPeople = new ArrayList<>();

    public TeamPeopleManagerAdapter(Context context, List<TeamPeopleBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TeamPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team_people_manager,null);
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
        holder.llUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onItemClick.onItemClick(teamPeopleBean);
                holder.llUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EditTeamPeopleActivity.class);
                        intent.putExtra("id",teamPeopleBean.getUser_id());
                        context.startActivity(intent);
                    }
                });
            }
        });
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbChoose.isChecked()){
                    checkedPeople.add(data.get(position));
                }else{
                    checkedPeople.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cbChoose.setChecked(!holder.cbChoose.isChecked());
                if(holder.cbChoose.isChecked()){
                    checkedPeople.add(data.get(position));
                }else{
                    checkedPeople.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
            }
        });
        if(type == 2){
            holder.cbChoose.setVisibility(View.VISIBLE);
            holder.llUpdate.setVisibility(View.GONE);
        }else{
            holder.cbChoose.setVisibility(View.GONE);
            holder.llUpdate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TeamPeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_choose)
        CheckBox cbChoose;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_position)
        TextView tvPosition;
        @BindView(R.id.ll_update)
        LinearLayout llUpdate;

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

    public List<TeamPeopleBean> getCheckedPeople() {
        return checkedPeople;
    }

    public void setType(int type) {
        this.type = type;
    }
}

