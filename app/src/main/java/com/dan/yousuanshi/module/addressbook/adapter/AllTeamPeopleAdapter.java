package com.dan.yousuanshi.module.addressbook.adapter;

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
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllTeamPeopleAdapter extends RecyclerView.Adapter<AllTeamPeopleAdapter.AllTeamPeopleViewHolder>{

    private Context context;
    private List<TeamPeopleBean> data;
    private List<TeamPeopleBean> checkedFriend = new ArrayList<>();
    private int type = 0;

    public AllTeamPeopleAdapter(Context context, List<TeamPeopleBean> data,int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public AllTeamPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_team_people,null);
        return new AllTeamPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTeamPeopleViewHolder holder, int position) {
        TeamPeopleBean teamPeopleBean = data.get(position);
        Glide.with(context).load(teamPeopleBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(teamPeopleBean.getReal_name());
        if (teamPeopleBean.isShowLetter()) {
            holder.tvPinyin.setText(String.valueOf(teamPeopleBean.getHeadLetter()));
            holder.llPinyin.setVisibility(View.VISIBLE);
        } else {
            holder.llPinyin.setVisibility(View.GONE);
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
                onItemClick.choosePeople(checkedFriend.size());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 2){
                    holder.cbChoose.setChecked(!holder.cbChoose.isChecked());
                    if(holder.cbChoose.isChecked()){
                        checkedFriend.add(data.get(position));
                    }else{
                        checkedFriend.remove(data.get(position));
                    }
                    data.get(position).setChecked(holder.cbChoose.isChecked());
                    onItemClick.choosePeople(checkedFriend.size());
                }else{
                    onItemClick.onItemClick(position);
                }
            }
        });
        if(type == 2){
            holder.cbChoose.setChecked(teamPeopleBean.isChecked());
            holder.cbChoose.setVisibility(View.VISIBLE);
        }
        if(teamPeopleBean.getIs_create() == 1){
            holder.tvPosition.setText("主管理员");
            holder.tvPosition.setVisibility(View.VISIBLE);
        } else if (teamPeopleBean.getIs_master() == 1) {
            holder.tvPosition.setText("管理员");
            holder.tvPosition.setVisibility(View.VISIBLE);
        }else if(teamPeopleBean.getIs_supervisor() == 1){
            holder.tvPosition.setText("主管");
            holder.tvPosition.setVisibility(View.VISIBLE);
        }else{
            holder.tvPosition.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AllTeamPeopleViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.tv_position)
        TextView tvPosition;

        public AllTeamPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public List<TeamPeopleBean> getCheckedFriend() {
        return checkedFriend;
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int position);
        void choosePeople(int count);
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
