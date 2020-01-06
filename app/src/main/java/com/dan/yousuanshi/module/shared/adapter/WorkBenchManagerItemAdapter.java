package com.dan.yousuanshi.module.shared.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.shared.activity.AddWorkBenchActivity;
import com.dan.yousuanshi.module.shared.activity.SettingWorkBenchIsLookActivity;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkBenchManagerItemAdapter extends RecyclerView.Adapter<WorkBenchManagerItemAdapter.WorkBenchManagerItemViewHolder>{

    private Context context;
    private List<AddWorkBenchBean.ChildrenBean> data;

    public WorkBenchManagerItemAdapter(Context context, List<AddWorkBenchBean.ChildrenBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public WorkBenchManagerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_bench_maneger_item,null);
        return new WorkBenchManagerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkBenchManagerItemViewHolder holder, int position) {
        AddWorkBenchBean.ChildrenBean childrenBean = data.get(position);
        Glide.with(context).load(childrenBean.getModel_icon()).into(holder.ivWorkBenchIcon);
        holder.tvWorkBench.setText(childrenBean.getModel_name());
        if(childrenBean.getMin_power() == 0){
            holder.tvIsLook.setText("(全员可见)");
        }else if(childrenBean.getMin_power() == 1){
            holder.tvIsLook.setText("(管理员可见)");
        }else if(childrenBean.getMin_power() == 2){
            holder.tvIsLook.setText("(部分可见)");
        }
        if("添加".equals(childrenBean.getModel_name())){
            holder.tvIsLook.setVisibility(View.GONE);
            holder.ivDelete.setVisibility(View.GONE);
        }
        holder.tvIsLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingWorkBenchIsLookActivity.class);
                intent.putExtra("modelId",childrenBean.getId());
                intent.putIntegerArrayListExtra("userList", (ArrayList<Integer>) childrenBean.getUser_list());
                intent.putIntegerArrayListExtra("departmentList", (ArrayList<Integer>) childrenBean.getDepartment_list());
                context.startActivity(intent);
            }
        });
        holder.tvWorkBench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingWorkBenchIsLookActivity.class);
                intent.putExtra("modelId",childrenBean.getId());
                intent.putIntegerArrayListExtra("userList", (ArrayList<Integer>) childrenBean.getUser_list());
                intent.putIntegerArrayListExtra("departmentList", (ArrayList<Integer>) childrenBean.getDepartment_list());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("添加".equals(childrenBean.getModel_name())){
                    Intent intent =new Intent(context, AddWorkBenchActivity.class);
                    intent.putExtra("parentId",childrenBean.getParent_id());
                    context.startActivity(intent);
                }
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.removeModel(childrenBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class WorkBenchManagerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_work_bench_icon)
        ImageView ivWorkBenchIcon;
        @BindView(R.id.tv_work_bench)
        TextView tvWorkBench;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_isLook)
        TextView tvIsLook;

        public WorkBenchManagerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void removeModel(AddWorkBenchBean.ChildrenBean childrenBean);
    }
}
