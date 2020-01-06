package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.activity.EditTeamPeopleActivity;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartmentPeopleAdapter extends RecyclerView.Adapter<DepartmentPeopleAdapter.DepartmentPeopleViewHolder>{

    private Context context;
    private List<DepartmentPeopleBean> data;
    private int type;//2为选择部门群群主

    public DepartmentPeopleAdapter(Context context, List<DepartmentPeopleBean> data,int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public DepartmentPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_department_people,null);
        return new DepartmentPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentPeopleViewHolder holder, int position) {
        DepartmentPeopleBean departmentPeopleBean = data.get(position);
        Glide.with(context).load(departmentPeopleBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(departmentPeopleBean.getReal_name());
        if(type == 2){
            holder.llUpdate.setVisibility(View.GONE);
            holder.tvPosition.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(departmentPeopleBean);
            }
        });
        holder.llUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTeamPeopleActivity.class);
                intent.putExtra("id",departmentPeopleBean.getUser_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DepartmentPeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_update)
        LinearLayout llUpdate;
        @BindView(R.id.tv_position)
        TextView tvPosition;

        public DepartmentPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onItemClick(DepartmentPeopleBean departmentPeopleBean);
    }

}
