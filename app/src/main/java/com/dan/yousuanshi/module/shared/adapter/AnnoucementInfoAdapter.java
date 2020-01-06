package com.dan.yousuanshi.module.shared.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.shared.bean.AnnouncementLisBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnnoucementInfoAdapter extends RecyclerView.Adapter<AnnoucementInfoAdapter.AnnoucementInfoViewHolder>{

    private Context context;
    private List<AnnouncementLisBean.DataBean> data;

    public AnnoucementInfoAdapter(Context context, List<AnnouncementLisBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AnnoucementInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shared_annoucement,null);
        return new AnnoucementInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnoucementInfoViewHolder holder, int position) {
        AnnouncementLisBean.DataBean dataBean = data.get(position);
        holder.tvTitle.setText(dataBean.getTitle());
        holder.tvDate.setText(dataBean.getCreate_time());
        holder.tvName.setText(dataBean.getReal_name()+"发布");
        holder.tvContent.setText(dataBean.getContent());
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

    class AnnoucementInfoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public AnnoucementInfoViewHolder(@NonNull View itemView) {
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
