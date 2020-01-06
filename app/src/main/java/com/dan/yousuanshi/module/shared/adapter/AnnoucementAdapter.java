package com.dan.yousuanshi.module.shared.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.utils.ItemSlideHelper;
import com.dan.yousuanshi.module.shared.bean.AnnouncementLisBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnnoucementAdapter extends RecyclerView.Adapter<AnnoucementAdapter.AnnoucementViewHolder> implements ItemSlideHelper.Callback{

    private Context context;
    private List<AnnouncementLisBean.DataBean> data;
    private boolean isMaster;
    private RecyclerView recyclerView;

    public AnnoucementAdapter(Context context, List<AnnouncementLisBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    public AnnoucementAdapter(Context context, List<AnnouncementLisBean.DataBean> data,boolean isMaster) {
        this.context = context;
        this.data = data;
        this.isMaster = isMaster;
    }

    @NonNull
    @Override
    public AnnoucementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shared_annoucement,null);
        return new AnnoucementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnoucementViewHolder holder, int position) {
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
        holder.llRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.remove(position);
            }
        });
//        if(isMaster){
//            ViewGroup.LayoutParams layoutParams =  holder.llRemove.getLayoutParams();
//            layoutParams.width = DpToPxUtils.dip2px(context,70);
//            holder.llRemove.setLayoutParams(layoutParams);
//        }else{
//            ViewGroup.LayoutParams layoutParams =  holder.llRemove.getLayoutParams();
//            layoutParams.width = DpToPxUtils.dip2px(context,0);
//            holder.llRemove.setLayoutParams(layoutParams);
//        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.addOnItemTouchListener(new ItemSlideHelper(this.recyclerView.getContext(), this));
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

    class AnnoucementViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.ll_remove)
        LinearLayout llRemove;

        public AnnoucementViewHolder(@NonNull View itemView) {
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
        void remove(int position);
    }
}
