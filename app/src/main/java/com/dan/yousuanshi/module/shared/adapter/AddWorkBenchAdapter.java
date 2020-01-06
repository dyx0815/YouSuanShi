package com.dan.yousuanshi.module.shared.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddWorkBenchAdapter extends RecyclerView.Adapter<AddWorkBenchAdapter.AddWorkBenchViewHolder>{

    private Context context;
    private List<AddWorkBenchBean> data;

    public AddWorkBenchAdapter(Context context, List<AddWorkBenchBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AddWorkBenchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_work_bench,null);
        return new AddWorkBenchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddWorkBenchViewHolder holder, int position) {
        AddWorkBenchBean modelListBean = data.get(position);
        Glide.with(context).load(modelListBean.getModel_icon()).into(holder.ivModelIcon);
        holder.tvModelName.setText(modelListBean.getModel_name());
        if(modelListBean.getIs_offline() == 0){//等于0为已添加
            holder.tvAdded.setVisibility(View.VISIBLE);
            holder.tvAdd.setVisibility(View.GONE);
        }else{
            holder.tvAdded.setVisibility(View.GONE);
            holder.tvAdd.setVisibility(View.VISIBLE);
        }
        if(modelListBean.getChildren().size() > 0){//有子项
            holder.ivOpen.setVisibility(View.VISIBLE);
        }else{
            holder.ivOpen.setVisibility(View.INVISIBLE);
        }
        holder.ivOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.recyclerView.getVisibility() == View.GONE){
                    holder.ivOpen.setImageResource(R.mipmap.icon_shared_add_work_bench_close);
                    holder.recyclerView.setVisibility(View.VISIBLE);
                }else{
                    holder.ivOpen.setImageResource(R.mipmap.icon_shared_add_work_bench_open);
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });
        AddWorkBenchAdapter2 adapter = new AddWorkBenchAdapter2(context,data.get(position).getChildren());
        adapter.setOnItemClick(new AddWorkBenchAdapter2.OnItemClick() {
            @Override
            public void onItemClick(int modelId, int parentId) {
                onItemClick.addModel2(modelId,parentId);
            }
        });
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(adapter);
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(modelListBean.getId(),modelListBean.getParent_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AddWorkBenchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_open)
        ImageView ivOpen;
        @BindView(R.id.iv_model_icon)
        ImageView ivModelIcon;
        @BindView(R.id.tv_model_name)
        TextView tvModelName;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.tv_added)
        TextView tvAdded;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        public AddWorkBenchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int modelId,int parentId);
        void addModel2(int modelId,int parentId);
    }
}
