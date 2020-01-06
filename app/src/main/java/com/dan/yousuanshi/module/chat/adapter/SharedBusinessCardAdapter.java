package com.dan.yousuanshi.module.chat.adapter;

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
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SharedBusinessCardAdapter extends RecyclerView.Adapter<SharedBusinessCardAdapter.SharedBusinessCardViewHolder>{

    private Context context;
    private List<MyTeamBean> data;

    public SharedBusinessCardAdapter(Context context, List<MyTeamBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SharedBusinessCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shared_business_card_team,null);
        return new SharedBusinessCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedBusinessCardViewHolder holder, int position) {
        MyTeamBean myTeamBean = data.get(position);
        Glide.with(context).load(myTeamBean.getCompanyLogo()).into(holder.ivLogo);
        holder.tvTeamName.setText(myTeamBean.getCompanyName());
        holder.llTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.llChoose.getVisibility() == View.VISIBLE){
                    holder.llChoose.setVisibility(View.GONE);
                    holder.ivRight.setImageResource(R.mipmap.icon_address_book_create_right);
                    holder.vUtil.setVisibility(View.VISIBLE);
                    holder.vLine.setVisibility(View.GONE);
                }else{
                    holder.llChoose.setVisibility(View.VISIBLE);
                    holder.ivRight.setImageResource(R.mipmap.icon_right_down);
                    holder.vUtil.setVisibility(View.GONE);
                    holder.vLine.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.llChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SharedBusinessCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_team)
        LinearLayout llTeam;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_team_name)
        TextView tvTeamName;
        @BindView(R.id.ll_choose)
        LinearLayout llChoose;
        @BindView(R.id.iv_flag)
        ImageView ivFlag;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.v_util)
        View vUtil;
        @BindView(R.id.v_line)
        View vLine;

        public SharedBusinessCardViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int position);
    }

}
