package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.PeopleSizeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoosePeopleSizeAdapter extends RecyclerView.Adapter<ChoosePeopleSizeAdapter.ChoosePeopleSizeViewHolder> {

    private Context context;
    private List<PeopleSizeBean> data;

    public ChoosePeopleSizeAdapter(Context context, List<PeopleSizeBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ChoosePeopleSizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_create_team_people_size,null);
        return new ChoosePeopleSizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoosePeopleSizeViewHolder holder, final int position) {
        holder.tvPeopleSize.setText(data.get(position).getPersonnel_str());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.choosePeopleSize(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChoosePeopleSizeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_people_size)
        TextView tvPeopleSize;

        public ChoosePeopleSizeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void choosePeopleSize(View v,int position);
    }
}
