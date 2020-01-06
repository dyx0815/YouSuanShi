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
import com.dan.yousuanshi.module.shared.bean.TemplateBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder>{

    private Context context;
    private List<TemplateBean.ChildrenBean> data;

    public TemplateAdapter(Context context, List<TemplateBean.ChildrenBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_template,null);
        return new TemplateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {
        TemplateBean.ChildrenBean childrenBean = data.get(position);
        Glide.with(context).load(childrenBean.getTemplate_icon()).into(holder.ivTemplate);
        holder.tvTemplateName.setText(childrenBean.getModel_name());
        holder.tvTemplateName2.setText(childrenBean.getModel_name()+"模板");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TemplateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_template_name)
        TextView tvTemplateName;
        @BindView(R.id.tv_template_name_2)
        TextView tvTemplateName2;
        @BindView(R.id.iv_template)
        ImageView ivTemplate;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
