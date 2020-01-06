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

public class IndustryTwoAdapter extends RecyclerView.Adapter<IndustryTwoAdapter.IndustryTwoViewHolder> {

    private Context context;
    private List<IndustryBean.ChildBean> data;

    public IndustryTwoAdapter(Context context, List<IndustryBean.ChildBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public IndustryTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_industry_two, null);
        return new IndustryTwoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndustryTwoViewHolder holder, final int position) {
        IndustryBean.ChildBean childBean = data.get(position);
        holder.tvIndustry.setText(childBean.getIndustry());
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

    class IndustryTwoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_industry)
        TextView tvIndustry;
        @BindView(R.id.v_util)
        View vUtil;

        public IndustryTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void chooseIndustry(View view,int position);
    }
}
