package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupPictureAdapter extends RecyclerView.Adapter<GroupPictureAdapter.GroupPictureViewHolder>{

    private Context context;
    private List<GroupFileBean.DataBean> data;
    private int type = 0; //2.出现选择按钮
    private List<GroupFileBean.DataBean> choosePic = new ArrayList<>();

    public GroupPictureAdapter(Context context, List<GroupFileBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public GroupPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_picture,null);
        return new GroupPictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupPictureViewHolder holder, int position) {
        GroupFileBean.DataBean dataBean = data.get(position);
        Glide.with(context).load(dataBean.getFile_url()).into(holder.ivPicture);
        if(type == 2){
            holder.cbChoose.setChecked(dataBean.isChecked());
            holder.cbChoose.setVisibility(View.VISIBLE);
            holder.cbChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        data.get(position).setChecked(true);
                        choosePic.add(dataBean);
                    }else{
                        data.get(position).setChecked(false);
                        choosePic.remove(dataBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class GroupPictureViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_picture)
        ImageView ivPicture;
        @BindView(R.id.cb_choose)
        CheckBox cbChoose;

        public GroupPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<GroupFileBean.DataBean> getChoosePic() {
        return choosePic;
    }
}
