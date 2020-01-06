package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.DiyApplicationSettingBean;
import com.dan.yousuanshi.module.chat.utils.ItemSlideHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingApplicationAdapter extends RecyclerView.Adapter<SettingApplicationAdapter.SettingApplicationViewHolder> implements ItemSlideHelper.Callback{

    private Context context;
    private List<DiyApplicationSettingBean> data;
    private RecyclerView recyclerView;

    public SettingApplicationAdapter(Context context, List<DiyApplicationSettingBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SettingApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_diy_setting,null);
        return new SettingApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingApplicationViewHolder holder, int position) {
        DiyApplicationSettingBean diyApplicationSettingBean = data.get(position);
        holder.tvName.setText(diyApplicationSettingBean.getLabel());
        if(diyApplicationSettingBean.getIsRequired() == 1){
            holder.tvFlag.setVisibility(View.GONE);
        }else{
            holder.tvFlag.setVisibility(View.VISIBLE);
        }
        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.addOnItemTouchListener(new ItemSlideHelper(this.recyclerView.getContext(), this));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            return viewGroup.getChildAt(1).getLayoutParams().width;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return this.recyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return this.recyclerView.findChildViewUnder(x, y);
    }

    class SettingApplicationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_flag)
        TextView tvFlag;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;

        public SettingApplicationViewHolder(@NonNull View itemView) {
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
