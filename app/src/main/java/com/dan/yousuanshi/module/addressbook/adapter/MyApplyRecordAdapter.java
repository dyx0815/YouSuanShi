package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.activity.ApplyInfoActivity;
import com.dan.yousuanshi.module.addressbook.bean.MyApplyRecordBean;
import com.dan.yousuanshi.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyApplyRecordAdapter extends RecyclerView.Adapter<MyApplyRecordAdapter.MyApplyRecordViewHolder>{

    private Context context;
    private List<MyApplyRecordBean.DataBean> data;

    public MyApplyRecordAdapter(Context context, List<MyApplyRecordBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyApplyRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_apply_record,null);
        return new MyApplyRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyApplyRecordViewHolder holder, int position) {
        MyApplyRecordBean.DataBean dataBean = data.get(position);
        holder.tvTeamName.setText(dataBean.getC_name());
        holder.tvDate.setText(dataBean.getSend_time());
        holder.tvPhone.setText(dataBean.getUser_tel());
        if(!StringUtils.isEmpty(dataBean.getSend_msg())){
            holder.tvApplyText.setText(dataBean.getSend_msg());
        }else{
            holder.tvApplyText.setText("无");
        }
        if(!StringUtils.isEmpty(dataBean.getExplain())){
            holder.tvLeaveMessage.setText(dataBean.getExplain());
        }else{
            holder.tvLeaveMessage.setText("无");
        }
        if(dataBean.getIs_pass() == 1){
            holder.ivAgree.setImageResource(R.mipmap.icon_agree);
        }else{
            holder.ivAgree.setImageResource(R.mipmap.icon_wait);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApplyInfoActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("data",dataBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyApplyRecordViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_team_name)
        TextView tvTeamName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_apply_text)
        TextView tvApplyText;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_leave_message)
        TextView tvLeaveMessage;
        @BindView(R.id.iv_agree)
        ImageView ivAgree;

        public MyApplyRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
