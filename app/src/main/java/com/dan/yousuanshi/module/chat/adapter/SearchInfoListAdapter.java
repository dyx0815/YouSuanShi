package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchInfoListAdapter extends RecyclerView.Adapter<SearchInfoListAdapter.SearchInfoViewHolder>{

    private List<String> data;
    private Context context;

    public SearchInfoListAdapter(List<String> data,Context context){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_info,null);
        return new SearchInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchInfoViewHolder holder, int position) {
        holder.tvName.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SearchInfoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView headIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_info)
        TextView tvInfo;

        public SearchInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
