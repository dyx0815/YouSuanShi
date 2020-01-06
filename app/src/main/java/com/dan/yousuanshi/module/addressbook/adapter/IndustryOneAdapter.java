package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.IndustryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndustryOneAdapter extends RecyclerView.Adapter<IndustryOneAdapter.IndustryOneViewHolder> {

    private Context context;
    private List<IndustryBean> data;

    public IndustryOneAdapter(Context context, List<IndustryBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public IndustryOneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_industry_one, null);
        return new IndustryOneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndustryOneViewHolder holder, final int position) {
        IndustryBean industryBean = data.get(position);
        holder.tvIndustry.setText(industryBean.getIndustry());
        if (industryBean.isSelect()) {
            holder.vChoose.setVisibility(View.VISIBLE);
            Glide.with(context).load(industryBean.getIcon_select()).into(holder.ivIcon);
            holder.tvIndustry.setTextColor(context.getColor(R.color.color_F99E05));
            holder.rlAll.setBackgroundColor(context.getColor(R.color.white));
            holder.vBottom.setVisibility(View.GONE);
        } else {
            holder.vChoose.setVisibility(View.INVISIBLE);
            Glide.with(context).load(industryBean.getIcon_unselect()).into(holder.ivIcon);
            holder.tvIndustry.setTextColor(context.getColor(R.color.color_999999));
            holder.rlAll.setBackgroundColor(context.getColor(R.color.color_F2F2F2));
            holder.vBottom.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class IndustryOneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.v_choose)
        View vChoose;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_industry)
        TextView tvIndustry;
        @BindView(R.id.v_bottom)
        View vBottom;
        @BindView(R.id.rl_all)
        RelativeLayout rlAll;

        public IndustryOneViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(View view,int position);
    }
}
