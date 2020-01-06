package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.SearchPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchPeopleAdapter extends RecyclerView.Adapter<SearchPeopleAdapter.SearchPeopleViewHolder>{
    private Context context;
    private List<SearchPeopleBean.DataBean> data;

    public SearchPeopleAdapter(Context context, List<SearchPeopleBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SearchPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_people, null);
        return new SearchPeopleAdapter.SearchPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPeopleViewHolder holder, int position) {
        SearchPeopleBean.DataBean dataBean = data.get(position);
        Glide.with(context).load(dataBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvNickName.setText(dataBean.getNick_name());
        holder.tvRealName.setText(dataBean.getReal_name());
        holder.tvTeamName.setText(dataBean.getC_name());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SearchPeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_nick_name)
        TextView tvNickName;
        @BindView(R.id.tv_real_name)
        TextView tvRealName;
        @BindView(R.id.tv_team_name)
        TextView tvTeamName;


        public SearchPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
