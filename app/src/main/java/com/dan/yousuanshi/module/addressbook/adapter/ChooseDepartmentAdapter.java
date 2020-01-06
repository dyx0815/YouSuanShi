package com.dan.yousuanshi.module.addressbook.adapter;

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

public class ChooseDepartmentAdapter extends RecyclerView.Adapter<ChooseDepartmentAdapter.ChooseDepartmentViewHolder>{

    private Context context;
    private List<DepartmentPeopleBean> data;
    private List<DepartmentPeopleBean> checkedPeople = new ArrayList<>();

    public ChooseDepartmentAdapter(Context context, List<DepartmentPeopleBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ChooseDepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_team_people,null);
        return new ChooseDepartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseDepartmentViewHolder holder, int position) {
        DepartmentPeopleBean departmentPeopleBean = data.get(position);
        Glide.with(context).load(departmentPeopleBean.getUser_portrait()).into(holder.rivHeadIcon);
        holder.tvName.setText(departmentPeopleBean.getReal_name());
        if(departmentPeopleBean.isChecked()){
            holder.cbChoose.setChecked(true);
            checkedPeople.add(departmentPeopleBean);
        }else{
            holder.cbChoose.setChecked(false);
        }
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbChoose.isChecked()){
                    checkedPeople.add(data.get(position));
                }else{
                    checkedPeople.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cbChoose.setChecked(!holder.cbChoose.isChecked());
                if(holder.cbChoose.isChecked()){
                    checkedPeople.add(data.get(position));
                }else{
                    checkedPeople.remove(data.get(position));
                }
                data.get(position).setChecked(holder.cbChoose.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChooseDepartmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_choose)
        CheckBox cbChoose;
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;

        public ChooseDepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public List<DepartmentPeopleBean> getCheckedPeople() {
        return checkedPeople;
    }


}
