package com.dan.yousuanshi.module.addressbook.adapter;

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
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseTeamPeopleAdapter extends RecyclerView.Adapter<ChooseTeamPeopleAdapter.ChooseTeamPeopleViewHolder> {

    private Context context;
    private List<TeamPeopleBean> data;
    private List<TeamPeopleBean> checkedPeople = new ArrayList<>();
    private int type = 0;//2为批量删除人员

    public ChooseTeamPeopleAdapter(Context context, List<TeamPeopleBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ChooseTeamPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_team_people, null);
        return new ChooseTeamPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseTeamPeopleViewHolder holder, int position) {
        TeamPeopleBean teamPeopleBean = data.get(position);
        Glide.with(context).load(teamPeopleBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(teamPeopleBean.getReal_name());
        if (teamPeopleBean.isChecked()) {
            holder.cbChoose.setChecked(true);
            checkedPeople.add(teamPeopleBean);
        } else {
            holder.cbChoose.setChecked(false);
        }
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbChoose.isChecked()) {
                    checkedPeople.add(data.get(position));
                } else {
                    checkedPeople.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
                if(onItemClick!=null){
                    onItemClick.onItemClick(checkedPeople.size());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cbChoose.setChecked(!holder.cbChoose.isChecked());
                if (holder.cbChoose.isChecked()) {
                    checkedPeople.add(data.get(position));
                } else {
                    checkedPeople.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
                if(onItemClick!=null){
                    onItemClick.onItemClick(checkedPeople.size());
                }
            }
        });
        if(type == 2){
            if(teamPeopleBean.getIs_create() == 1){
                holder.tvPosition.setText("主管理员");
                holder.tvPosition.setVisibility(View.VISIBLE);
            } else if (teamPeopleBean.getIs_master() == 1) {
                holder.tvPosition.setText("管理员");
                holder.tvPosition.setVisibility(View.VISIBLE);
            }else{
                holder.tvPosition.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChooseTeamPeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_choose)
        CheckBox cbChoose;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_position)
        TextView tvPosition;

        public ChooseTeamPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<TeamPeopleBean> getCheckedPeople() {
        return checkedPeople;
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(int count);
    }

    public void setType(int type) {
        this.type = type;
    }
}
