package com.dan.yousuanshi.module.shared.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.shared.bean.WorkbenchBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkBenchAdapter extends RecyclerView.Adapter<WorkBenchAdapter.WorkBenchViewHolder> {

    private Context context;
    private List<WorkbenchBean.ModelListBean> data;

    public WorkBenchAdapter(Context context, List<WorkbenchBean.ModelListBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public WorkBenchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_bench, null);
        return new WorkBenchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkBenchViewHolder holder, int position) {
        WorkbenchBean.ModelListBean workbenchBean = data.get(position);
        holder.tvWorkBenchName.setText(workbenchBean.getModel_name());
        if(data.get(position).getChildren().size()<=4){
            holder.tvUtil.setVisibility(View.GONE);
        }else{
            holder.tvUtil.setVisibility(View.VISIBLE);
        }
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context,4));
        WorkBenchItemAdapter adapter = new WorkBenchItemAdapter(context,workbenchBean.getChildren());
        holder.recyclerView.setAdapter(adapter);
        List<WorkbenchBean.ModelListBean.ChildrenBean> childrenBeans = new ArrayList<>();
        childrenBeans.addAll(workbenchBean.getChildren());
        holder.tvUtil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean isAdd = false;
//                if("添加".equals(workbenchBean.getChildren().get(workbenchBean.getChildren().size()-1).getModel_name())){
//                    isAdd = true;
//                }else{
//                    isAdd =false;
//                }
                if(workbenchBean.getChildren().size() > 4){
                    holder.tvUtil.setText("展开");
                    for(int i = workbenchBean.getChildren().size()-1;i>=4;i--){
                        workbenchBean.getChildren().remove(i);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    holder.tvUtil.setText("收起");
                    workbenchBean.getChildren().clear();
                    workbenchBean.getChildren().addAll(childrenBeans);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class WorkBenchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_work_bench_name)
        TextView tvWorkBenchName;
        @BindView(R.id.tv_util)
        TextView tvUtil;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        public WorkBenchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
