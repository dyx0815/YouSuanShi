package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.DepartmentBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder>{

    private Context context;
    private List<DepartmentBean> data;
    private int type;
    private List<DepartmentBean> chooseDepartment = new ArrayList<>();

    public DepartmentAdapter(Context context, List<DepartmentBean> data) {
        this.context = context;
        this.data = data;
    }

    public DepartmentAdapter(Context context, List<DepartmentBean> data,int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_department,null);
        return new DepartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        DepartmentBean departmentBean = data.get(position);
        holder.tvTeamStructureName.setText(departmentBean.getD_name()+"（"+departmentBean.getNums()+"）");
        holder.rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 2){
                    holder.cbChoose.setChecked(!holder.cbChoose.isChecked());
                    data.get(position).setCheck(holder.cbChoose.isChecked());
                    if(holder.cbChoose.isChecked()){
                        chooseDepartment.add(departmentBean);
                    }else{
                        chooseDepartment.remove(departmentBean);
                    }
                    onItemClick.chooseDepartment(chooseDepartment.size());
                }else{
                    onItemClick.department(departmentBean);
                }
            }
        });
        holder.tvGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.subordinate(departmentBean);
            }
        });
        if(type == 2){
            holder.cbChoose.setChecked(departmentBean.isCheck());
            holder.cbChoose.setVisibility(View.VISIBLE);
            holder.cbChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.get(position).setCheck(holder.cbChoose.isChecked());
                    if(holder.cbChoose.isChecked()){
                        chooseDepartment.add(departmentBean);
                    }else{
                        chooseDepartment.remove(departmentBean);
                    }
                    onItemClick.chooseDepartment(chooseDepartment.size());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DepartmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_all)
        RelativeLayout rlAll;
        @BindView(R.id.tv_team_structure_name)
        TextView tvTeamStructureName;
        @BindView(R.id.tv_group)
        TextView tvGroup;
        @BindView(R.id.cb_choose)
        CheckBox cbChoose;

        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void subordinate(DepartmentBean parent);
        void department(DepartmentBean departmentBean);
        void chooseDepartment(int count);
    }

    public List<DepartmentBean> getChooseDepartment() {
        return chooseDepartment;
    }
}
