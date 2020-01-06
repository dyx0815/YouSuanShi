package com.dan.yousuanshi.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.bean.LocationAddress;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationAddressAdapter extends RecyclerView.Adapter<LocationAddressAdapter.LocationAddressHolder>{

    private Context context;
    private List<LocationAddress> data;

    public LocationAddressAdapter(Context context, List<LocationAddress> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public LocationAddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location_address,null);
        return new LocationAddressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAddressHolder holder, int position) {
        LocationAddress locationAddress  = data.get(position);
        holder.tvLocation.setText(locationAddress.getLocationName());
        holder.tvAddress.setText(locationAddress.getLocationAddess());
        if(locationAddress.isSelect()){
            holder.ivSelected.setVisibility(View.VISIBLE);
        }else {
            holder.ivSelected.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class LocationAddressHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_selected)
        ImageView ivSelected;

        public LocationAddressHolder(@NonNull View itemView) {
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
