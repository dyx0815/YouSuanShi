package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.AddressBookBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamStructureAdapter extends RecyclerView.Adapter<TeamStructureAdapter.TeamStructureViewHolder>{

    private Context context;
    private List<AddressBookBean.DepartmentListBean> data;

    public TeamStructureAdapter(Context context, List<AddressBookBean.DepartmentListBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TeamStructureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team_structure,null);
        return new TeamStructureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamStructureViewHolder holder, int position) {
        AddressBookBean.DepartmentListBean departmentListBean = data.get(position);
        holder.tvTeamStructureName.setText(departmentListBean.getD_name());
        holder.tvGroup.setOnClickListener(new View.OnClickListener() {
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


    class TeamStructureViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_team_structure_name)
        TextView tvTeamStructureName;
        @BindView(R.id.tv_group)
        TextView tvGroup;

        public TeamStructureViewHolder(@NonNull View itemView) {
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
