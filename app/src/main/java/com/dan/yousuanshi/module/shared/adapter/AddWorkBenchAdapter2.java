package com.dan.yousuanshi.module.shared.adapter;

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
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddWorkBenchAdapter2 extends RecyclerView.Adapter<AddWorkBenchAdapter2.AddWorkBenchViewHolder2>{

    private Context context;
    private List<AddWorkBenchBean.ChildrenBean> data;

    public AddWorkBenchAdapter2(Context context, List<AddWorkBenchBean.ChildrenBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AddWorkBenchAdapter2.AddWorkBenchViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_work_bench2,null);
        return new AddWorkBenchViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddWorkBenchAdapter2.AddWorkBenchViewHolder2 holder, int position) {
        AddWorkBenchBean.ChildrenBean childrenBean = data.get(position);
        Glide.with(context).load(childrenBean.getModel_icon()).into(holder.ivModelIcon);
        holder.tvModelName.setText(childrenBean.getModel_name());
        if(childrenBean.getIs_offline() == 0){//等于0为已添加
            holder.tvAdded.setVisibility(View.VISIBLE);
            holder.tvAdd.setVisibility(View.GONE);
        }else{
            holder.tvAdded.setVisibility(View.GONE);
            holder.tvAdd.setVisibility(View.VISIBLE);
        }
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(childrenBean.getId(),childrenBean.getParent_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AddWorkBenchViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_model_icon)
        ImageView ivModelIcon;
        @BindView(R.id.tv_model_name)
        TextView tvModelName;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.tv_added)
        TextView tvAdded;

        public AddWorkBenchViewHolder2(@NonNull View itemView) {
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
    }
}
