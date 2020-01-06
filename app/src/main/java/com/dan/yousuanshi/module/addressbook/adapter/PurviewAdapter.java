package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.PartPurviewBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurviewAdapter extends RecyclerView.Adapter<PurviewAdapter.PurviewViewHolder>{

    private Context context;
    private List<PartPurviewBean.PowerListBean.ChildrenBean> data;

    public PurviewAdapter(Context context, List<PartPurviewBean.PowerListBean.ChildrenBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PurviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_purview,null);
        return new PurviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurviewViewHolder holder, int position) {
        PartPurviewBean.PowerListBean.ChildrenBean childrenBean = data.get(position);
        Glide.with(context).load(childrenBean.getModel_icon()).into(holder.ivPurview);
        holder.tvPurview.setText(childrenBean.getModel_name());
        holder.switchPurview.setChecked(childrenBean.getIs_had() == 1?true:false);
        holder.switchPurview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position,holder.switchPurview.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PurviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_purview)
        ImageView ivPurview;
        @BindView(R.id.tv_purview)
        TextView tvPurview;
        @BindView(R.id.switch_purview)
        Switch switchPurview;

        public PurviewViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(int position,boolean isCheck);
    }
}
