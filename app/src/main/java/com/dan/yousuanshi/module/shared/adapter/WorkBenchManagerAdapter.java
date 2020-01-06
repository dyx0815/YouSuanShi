package com.dan.yousuanshi.module.shared.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkBenchManagerAdapter extends RecyclerView.Adapter<WorkBenchManagerAdapter.WorkBenchManagerViewHolder>{

    private Context context;
    private List<AddWorkBenchBean> data;

    public WorkBenchManagerAdapter(Context context, List<AddWorkBenchBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public WorkBenchManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_bench_manager,null);
        return new WorkBenchManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkBenchManagerViewHolder holder, int position) {
        AddWorkBenchBean addWorkBenchBean = data.get(position);
        holder.tvWorkBenchName.setText(addWorkBenchBean.getModel_name());
        WorkBenchManagerItemAdapter adapter = new WorkBenchManagerItemAdapter(context,data.get(position).getChildren());
        adapter.setOnItemClick(new WorkBenchManagerItemAdapter.OnItemClick() {
            @Override
            public void removeModel(AddWorkBenchBean.ChildrenBean childrenBean) {
                onItemClick.removeModel(childrenBean);
            }
        });
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context,4));
        holder.recyclerView.setAdapter(adapter);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.removeWorkBench(addWorkBenchBean);
            }
        });
        holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.updateModelName(addWorkBenchBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class WorkBenchManagerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_work_bench_name)
        TextView tvWorkBenchName;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.iv_update)
        ImageView ivUpdate;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        public WorkBenchManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void removeModel(AddWorkBenchBean.ChildrenBean childrenBean);
        void removeWorkBench(AddWorkBenchBean addWorkBenchBean);
        void updateModelName(AddWorkBenchBean addWorkBenchBean);
    }
}
