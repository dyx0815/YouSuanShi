package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.PartPurviewBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartPurviewAdapter extends RecyclerView.Adapter<PartPurviewAdapter.PartPurviewViewHolder>{

    private Context context;
    private List<PartPurviewBean.PowerListBean> data;

    public PartPurviewAdapter(Context context, List<PartPurviewBean.PowerListBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PartPurviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_part_purview,null);
        return new PartPurviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartPurviewViewHolder holder, int position) {
        PartPurviewBean.PowerListBean powerListBean = data.get(position);
        holder.tvPartPurview.setText(powerListBean.getModel_name());
        holder.switchPartPurview.setChecked(powerListBean.getIs_had() == 1?true:false);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        PurviewAdapter adapter = new PurviewAdapter(context,powerListBean.getChildren());
        adapter.setOnItemClick(new PurviewAdapter.OnItemClick() {
            @Override
            public void onItemClick(int pos,boolean isCheck) {
                data.get(position).getChildren().get(pos).setIs_had(isCheck?1:0);
                for(PartPurviewBean.PowerListBean.ChildrenBean item : data.get(position).getChildren()){
                    if(item.getIs_had() == 1){
                        holder.switchPartPurview.setChecked(true);
                        return;
                    }
                }
                holder.switchPartPurview.setChecked(false);
            }
        });
        holder.recyclerView.setAdapter(adapter);
        holder.switchPartPurview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.switchPartPurview.isChecked()){
                    for(PartPurviewBean.PowerListBean.ChildrenBean item : data.get(position).getChildren()){
                        item.setIs_had(1);
                    }
                }else{
                    for(PartPurviewBean.PowerListBean.ChildrenBean item : data.get(position).getChildren()){
                        item.setIs_had(0);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        holder.llPartPurview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.recyclerView.getVisibility() == View.GONE){
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    holder.ivPartPurView.setImageResource(R.mipmap.icon_part_purview_remove);
                }else{
                    holder.recyclerView.setVisibility(View.GONE);
                    holder.ivPartPurView.setImageResource(R.mipmap.icon_part_purview_add);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PartPurviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_part_purview)
        LinearLayout llPartPurview;
        @BindView(R.id.iv_part_purview)
        ImageView ivPartPurView;
        @BindView(R.id.tv_part_purview)
        TextView tvPartPurview;
        @BindView(R.id.switch_part_purview)
        Switch switchPartPurview;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        public PartPurviewViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public List<PartPurviewBean.PowerListBean> getData() {
        return data;
    }
}
