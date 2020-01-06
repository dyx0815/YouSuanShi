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
import com.dan.yousuanshi.module.addressbook.bean.TeamNewApplyBean;
import com.dan.yousuanshi.utils.StringUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamNewPeopleAdapter extends RecyclerView.Adapter<TeamNewPeopleAdapter.TeamNewPeopleViewHolder>{

    private Context context;
    private List<TeamNewApplyBean.DataBean> data;

    public TeamNewPeopleAdapter(Context context, List<TeamNewApplyBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TeamNewPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team_apply,null);
        return new TeamNewPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamNewPeopleViewHolder holder, int position) {
        TeamNewApplyBean.DataBean dataBean = data.get(position);
        Glide.with(context).load(dataBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvRealName.setText(dataBean.getReal_name());
        holder.tvDate.setText(dataBean.getSend_time());
        holder.tvPhone.setText(dataBean.getUser_tel());
        if(!StringUtils.isEmpty(dataBean.getSend_msg())){
            holder.tvApplyText.setText(dataBean.getSend_msg());
        }else{
            holder.tvApplyText.setText("无");
        }
        if(!StringUtils.isEmpty(dataBean.getExplain())){
            holder.tvLeaveMessage.setText(dataBean.getExplain());
        }else{
            holder.tvLeaveMessage.setText("无");
        }
        if(dataBean.getIs_pass() == 1){
            holder.tvApproved.setText(dataBean.getAgree_real_name()+"已同意");
            holder.tvSure.setVisibility(View.GONE);
            holder.tvApproved.setVisibility(View.VISIBLE);
        }else{
            holder.tvSure.setVisibility(View.VISIBLE);
            holder.tvApproved.setVisibility(View.GONE);
        }
        holder.tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(dataBean.getId(),position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.lookInfo(dataBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TeamNewPeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_real_name)
        TextView tvRealName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_apply_text)
        TextView tvApplyText;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_leave_message)
        TextView tvLeaveMessage;
        @BindView(R.id.tv_sure)
        TextView tvSure;
        @BindView(R.id.tv_approved)
        TextView tvApproved;

        public TeamNewPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int id,int position);
        void lookInfo(TeamNewApplyBean.DataBean data);
    }
}
