package com.dan.yousuanshi.module.shared.adapter;

import android.content.ContentValues;
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
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.dao.bean.OftenModelBean;
import com.dan.yousuanshi.module.shared.activity.AddWorkBenchActivity;
import com.dan.yousuanshi.module.shared.activity.AnnouncementActivity;
import com.dan.yousuanshi.module.shared.activity.TemplateManagerActivity;
import com.dan.yousuanshi.module.shared.activity.WorkBenchManagerActivity;
import com.dan.yousuanshi.module.shared.bean.WorkbenchBean;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkBenchItemAdapter extends RecyclerView.Adapter<WorkBenchItemAdapter.WorkBenchItemViewHolder> {

    private Context context;
    private List<WorkbenchBean.ModelListBean.ChildrenBean> data;

    public WorkBenchItemAdapter(Context context, List<WorkbenchBean.ModelListBean.ChildrenBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public WorkBenchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_bench_item, null);
        return new WorkBenchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkBenchItemViewHolder holder, int position) {
        WorkbenchBean.ModelListBean.ChildrenBean parentIdBean = data.get(position);
        Glide.with(context).load(parentIdBean.getModel_icon()).into(holder.ivWorkBenchIcon);
        holder.tvWorkBench.setText(parentIdBean.getModel_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parentIdBean.getModel_id() == 2) {//管理工作台
                    Intent intent = new Intent(context, WorkBenchManagerActivity.class);
                    context.startActivity(intent);
                } else if (parentIdBean.getModel_id() == 3) {//公告管理
                    Intent intent = new Intent(context, AnnouncementActivity.class);
                    intent.putExtra("isMaster", true);
                    context.startActivity(intent);
                } else if("模板管理".equals(parentIdBean.getModel_name())){//模板管理
                    Intent intent = new Intent(context, TemplateManagerActivity.class);
                    context.startActivity(intent);
                }
                if ("添加".equals(parentIdBean.getModel_name())) {
                    Intent intent = new Intent(context, AddWorkBenchActivity.class);
                    intent.putExtra("parentId", parentIdBean.getParent_id());
                    context.startActivity(intent);
                } else {
                    OftenModelBean oftenModelBean = DataBaseHelper.queryOftenModelById(context, UserUtils.getInstance().getUserBean().getUser_id(), parentIdBean.getModel_id());
                    if (oftenModelBean == null) {
                        ContentValues values = new ContentValues();
                        values.put("model_id", parentIdBean.getModel_id());
                        values.put("click_count", 1);
                        DataBaseHelper.insertOftenModel(context, UserUtils.getInstance().getUserBean().getUser_id(), values);
                    } else {
                        ContentValues values = new ContentValues();
                        values.put("click_count", oftenModelBean.getClickCount() + 1);
                        DataBaseHelper.updateOftenModel(context, UserUtils.getInstance().getUserBean().getUser_id(), parentIdBean.getModel_id(), values);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class WorkBenchItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_work_bench_icon)
        ImageView ivWorkBenchIcon;
        @BindView(R.id.tv_work_bench)
        TextView tvWorkBench;

        public WorkBenchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
