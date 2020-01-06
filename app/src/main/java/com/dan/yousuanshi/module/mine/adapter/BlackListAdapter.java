package com.dan.yousuanshi.module.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.mine.bean.BlackListBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlackListAdapter extends RecyclerView.Adapter<BlackListAdapter.BlackListViewHolder>{

    private Context context;
    private List<BlackListBean.DataBean> data;

    public BlackListAdapter(Context context, List<BlackListBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public BlackListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_black_list,null);
        return new BlackListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlackListViewHolder holder, int position) {
        BlackListBean.DataBean dataBean = data.get(position);
        Glide.with(context).load(dataBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(dataBean.getNick_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(dataBean.getUser_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BlackListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;

        public BlackListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int userId);
    }
}
