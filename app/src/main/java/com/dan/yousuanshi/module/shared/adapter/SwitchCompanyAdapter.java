package com.dan.yousuanshi.module.shared.adapter;

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

public class SwitchCompanyAdapter extends RecyclerView.Adapter<SwitchCompanyAdapter.SwitchCompanyViewHolder>{

    private Context context;
    private List<MyTeamBean> data;
    private MyTeamBean myTeamBean;

    public SwitchCompanyAdapter(Context context, List<MyTeamBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SwitchCompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_switch_company,null);
        return new SwitchCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwitchCompanyViewHolder holder, int position) {
        MyTeamBean myTeamBean = data.get(position);
        holder.tvTeamName.setText(myTeamBean.getCompanyName());
        if(myTeamBean.isChecked()){
            holder.tvTeamName.setTextColor(context.getColor(R.color.color_F99E05));
            holder.ivChoose.setVisibility(View.VISIBLE);
        }else{
            holder.tvTeamName.setTextColor(context.getColor(R.color.color_999999));
            holder.ivChoose.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(MyTeamBean item : data){
                    item.setChecked(false);
                }
                data.get(position).setChecked(true);
                notifyDataSetChanged();
                SwitchCompanyAdapter.this.myTeamBean = data.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SwitchCompanyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_team_name)
        TextView tvTeamName;
        @BindView(R.id.iv_team_proof)
        ImageView ivTeamProof;
        @BindView(R.id.iv_choose)
        ImageView ivChoose;

        public SwitchCompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public MyTeamBean getMyTeamBean() {
        return myTeamBean;
    }
}
