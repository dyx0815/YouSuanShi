package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.IndustryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndustrySearchAdapter extends RecyclerView.Adapter<IndustrySearchAdapter.IndustySearchViewHolder>{

    private Context context;
    private List<IndustryBean> data;

    public IndustrySearchAdapter(Context context, List<IndustryBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public IndustySearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_industry_search,null);
        return new IndustySearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndustySearchViewHolder holder, final int position) {
        holder.tvIndustry.setText(data.get(position).getIndustry());
        holder.tvIndustryTwo.setText(data.get(position).getChild().get(0).getIndustry());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.chooseIndustry(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class IndustySearchViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_industry)
        TextView tvIndustry;
        @BindView(R.id.tv_industry_two)
        TextView tvIndustryTwo;

        public IndustySearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void chooseIndustry(View v,int position);
    }
}
