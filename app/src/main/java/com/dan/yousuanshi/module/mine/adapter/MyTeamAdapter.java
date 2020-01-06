package com.dan.yousuanshi.module.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyTeamViewHolder>{

    private Context context;
    private List<MyTeamBean> data;

    public MyTeamAdapter(Context context, List<MyTeamBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_team,null);
        return new MyTeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTeamViewHolder holder, int position) {
        MyTeamBean myTeamBean = data.get(position);
        holder.tvTeamName.setText(myTeamBean.getCompanyName());
        if(myTeamBean.getCompanyMaster() == 0){
            holder.tvSetting.setVisibility(View.GONE);
        }else{
            holder.tvSetting.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });
        holder.tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyTeamViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_team_name)
        TextView tvTeamName;
        @BindView(R.id.iv_team_proof)
        ImageView ivTeamProof;
        @BindView(R.id.tv_setting)
        TextView tvSetting;

        public MyTeamViewHolder(@NonNull View itemView) {
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
        void setting(int position);
    }
}
