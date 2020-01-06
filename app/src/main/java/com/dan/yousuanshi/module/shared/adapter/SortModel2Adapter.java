package com.dan.yousuanshi.module.shared.adapter;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortModel2Adapter extends RecyclerView.Adapter<SortModel2Adapter.SortModel2ViewHolder>{

    private Context context;
    private List<AddWorkBenchBean.ChildrenBean> data;

    public SortModel2Adapter(Context context, List<AddWorkBenchBean.ChildrenBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SortModel2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sort_model_2,null);
        return new SortModel2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortModel2ViewHolder holder, int position) {
        AddWorkBenchBean.ChildrenBean childrenBean = data.get(position);
        holder.tvModelName.setText(childrenBean.getModel_name());
        Glide.with(context).load(childrenBean.getModel_icon()).into(holder.ivModelIcon);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
                vib.vibrate(70);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SortModel2ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_model_icon)
        ImageView ivModelIcon;
        @BindView(R.id.tv_model_name)
        TextView tvModelName;
        @BindView(R.id.iv_sort)
        ImageView ivSort;

        public SortModel2ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void move(int fromPosition,int toPosition){
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public List<AddWorkBenchBean.ChildrenBean> getData() {
        return data;
    }
}
