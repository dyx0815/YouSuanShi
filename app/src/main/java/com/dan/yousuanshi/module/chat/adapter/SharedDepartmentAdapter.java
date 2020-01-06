package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SharedDepartmentAdapter extends RecyclerView.Adapter<SharedDepartmentAdapter.SharedDepartmentViewHolder>{

    private Context context;
    private List<DepartmentBean> data;

    public SharedDepartmentAdapter(Context context, List<DepartmentBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SharedDepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shared_department,null);
        return new SharedDepartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedDepartmentViewHolder holder, int position) {
        DepartmentBean departmentBean = data.get(position);
        holder.tvDepartment.setText(departmentBean.getD_name()+"（"+departmentBean.getNums()+"）");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class SharedDepartmentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_department)
        TextView tvDepartment;

        public SharedDepartmentViewHolder(@NonNull View itemView) {
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
