package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.SearchTeamBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTeamAdapter extends RecyclerView.Adapter<SearchTeamAdapter.SearchTeamViewHolder>{

    private Context context;
    private List<SearchTeamBean.DataBean> data;

    public SearchTeamAdapter(Context context, List<SearchTeamBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SearchTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_join_team,null);
        return new SearchTeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTeamViewHolder holder, int position) {
        SearchTeamBean.DataBean dataBean = data.get(position);
        Glide.with(context).load(dataBean.getCompany_logo()).into(holder.rivHeadIcon);
        holder.tvTeamName.setText(dataBean.getC_name());
        holder.tvAddress.setText(dataBean.getProvince()+dataBean.getCity()+dataBean.getDistrict());
        holder.tvIndustry.setText(dataBean.getIndustry2());
        holder.tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(dataBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SearchTeamViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_team_name)
        TextView tvTeamName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_industry)
        TextView tvIndustry;
        @BindView(R.id.tv_join)
        TextView tvJoin;
        @BindView(R.id.iv_team_proof)
        ImageView ivTeamProof;

        public SearchTeamViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(SearchTeamBean.DataBean dataBean);
    }
}
