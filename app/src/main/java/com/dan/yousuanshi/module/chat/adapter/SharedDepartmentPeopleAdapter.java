package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.DepartmentPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SharedDepartmentPeopleAdapter extends RecyclerView.Adapter<SharedDepartmentPeopleAdapter.SharedDepartmentViewHolder>{

    private Context context;
    private List<DepartmentPeopleBean> data;
    private List<DepartmentPeopleBean> choosePeople = new ArrayList<>();
    private int type;

    public SharedDepartmentPeopleAdapter(Context context, List<DepartmentPeopleBean> data) {
        this.context = context;
        this.data = data;
    }

    public SharedDepartmentPeopleAdapter(Context context, List<DepartmentPeopleBean> data,int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public SharedDepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shared_department_people,null);
        return new SharedDepartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedDepartmentViewHolder holder, int position) {
        DepartmentPeopleBean departmentPeopleBean = data.get(position);
        Glide.with(context).load(departmentPeopleBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(departmentPeopleBean.getReal_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 2){

                }else{
                    onItemClick.onItemClick(departmentPeopleBean);
                }
            }
        });
        if(type == 2){
            holder.cbChoose.setChecked(departmentPeopleBean.isChecked());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SharedDepartmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.cb_choose)
        CheckBox cbChoose;

        public SharedDepartmentViewHolder(@NonNull View itemView) {
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

    public List<DepartmentPeopleBean> getChoosePeople() {
        return choosePeople;
    }
}
