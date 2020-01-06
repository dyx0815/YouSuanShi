package com.dan.yousuanshi.module.mine.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartmentTitleAdapter extends RecyclerView.Adapter<DepartmentTitleAdapter.DepartmentTitleViewHolder>{

    private Context context;
    private List<DepartmentBean> data;

    public DepartmentTitleAdapter(Context context, List<DepartmentBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public DepartmentTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_department_title,null);
        return new DepartmentTitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentTitleViewHolder holder, int position) {
        holder.tvDepartment.setText(data.get(position).getD_name());
        if(position == data.size()-1){
            holder.tvDepartment.setTextColor(context.getColor(R.color.color_F99E05));
            holder.ivRight.setVisibility(View.GONE);
        }else{
            holder.tvDepartment.setTextColor(context.getColor(R.color.color_999999));
            holder.ivRight.setVisibility(View.VISIBLE);
        }
        holder.tvDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DepartmentTitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_department)
        TextView tvDepartment;
        @BindView(R.id.iv_right)
        ImageView ivRight;

        public DepartmentTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int position);
    }
}
