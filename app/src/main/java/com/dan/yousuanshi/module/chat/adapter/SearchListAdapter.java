package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchViewHolder> {

    private List<String> data;
    private Context context;

    public SearchListAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search,null);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.tvTitle.setText(data.get(position));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(new SearchInfoListAdapter(data,context));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
