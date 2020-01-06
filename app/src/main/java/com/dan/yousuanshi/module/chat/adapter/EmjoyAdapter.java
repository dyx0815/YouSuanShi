package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.EmojiBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmjoyAdapter extends RecyclerView.Adapter<EmjoyAdapter.EmjoyViewHolder>{

    private Context context;
    private List<EmojiBean> data;

    public EmjoyAdapter(Context context, List<EmojiBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public EmjoyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_emjoy,null);
        return new EmjoyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmjoyViewHolder holder, int position) {
        EmojiBean emojiBean = data.get(position);
        holder.ivEmjoy.setImageResource(emojiBean.getResId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(emojiBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class EmjoyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_emjoy)
        ImageView ivEmjoy;

        public EmjoyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setData(List<EmojiBean> data) {
        this.data = data;
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(EmojiBean emojiBean);
    }
}
