package com.dan.yousuanshi.module.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetFirstTeamAdapter extends RecyclerView.Adapter<SetFirstTeamAdapter.SetFirstTeamViewHolder>{

    private Context context;
    private List<MyTeamBean> data;

    public SetFirstTeamAdapter(Context context, List<MyTeamBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SetFirstTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_setting_first_team,null);
        return new SetFirstTeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetFirstTeamViewHolder holder, int position) {
        MyTeamBean myTeamBean = data.get(position);
        holder.tvTeamName.setText(myTeamBean.getCompanyName());
        holder.tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(myTeamBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SetFirstTeamViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_team_name)
        TextView tvTeamName;
        @BindView(R.id.tv_setting)
        TextView tvSetting;

        public SetFirstTeamViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(MyTeamBean myTeamBean);
    }
}
