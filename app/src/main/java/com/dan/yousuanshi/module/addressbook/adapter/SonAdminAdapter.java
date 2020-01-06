package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.activity.SettingSonAdmin2Activity;
import com.dan.yousuanshi.module.addressbook.bean.SonAdminBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SonAdminAdapter extends RecyclerView.Adapter<SonAdminAdapter.SonAdminViewHolder> {

    private Context context;
    private List<SonAdminBean> data;

    public SonAdminAdapter(Context context, List<SonAdminBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SonAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_son_admin, null);
        return new SonAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SonAdminViewHolder holder, int position) {
        SonAdminBean sonAdminBean = data.get(position);
        holder.tvName.setText(sonAdminBean.getReal_name());
        holder.tvPurview.setText(sonAdminBean.getIs_all_model() == 0 ? "部分权限" : "全部权限");
        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingSonAdmin2Activity.class);
                intent.putExtra("data",sonAdminBean.getUser_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SonAdminViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_purview)
        TextView tvPurview;
        @BindView(R.id.tv_remove)
        TextView tvRemove;

        public SonAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
