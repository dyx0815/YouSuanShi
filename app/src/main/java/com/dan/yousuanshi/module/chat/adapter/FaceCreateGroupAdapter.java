package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.FaceCreateGroupBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class FaceCreateGroupAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<FaceCreateGroupBean.UserListBean> data;

    public FaceCreateGroupAdapter(Context context, List<FaceCreateGroupBean.UserListBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType == 0){
           View view = LayoutInflater.from(context).inflate(R.layout.item_face_create_end,null);
           return new FaceCreateEndViewHolder(view);
       }else{
           View view = LayoutInflater.from(context).inflate(R.layout.item_face_create_group,null);
           return new FaceCreateGroupViewHolder(view);
       }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FaceCreateEndViewHolder){
            GifDrawable gifDrawable = (GifDrawable) ((FaceCreateEndViewHolder)holder).gifImageView.getDrawable();
            gifDrawable.start();
        }else if(holder instanceof FaceCreateGroupViewHolder){
            Glide.with(context).load(data.get(position).getAvatar()).into(((FaceCreateGroupViewHolder)holder).rivHeadIcon);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FaceCreateGroupViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;

        public FaceCreateGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class FaceCreateEndViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.gifView)
        GifImageView gifImageView;

        public FaceCreateEndViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(data.get(position).getUser_id());
    }
}
