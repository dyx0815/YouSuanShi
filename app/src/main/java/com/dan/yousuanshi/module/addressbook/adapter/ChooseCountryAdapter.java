package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.CountryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseCountryAdapter extends RecyclerView.Adapter<ChooseCountryAdapter.ChooseCountryViewHolder>{
    private Context context;
    private List<CountryBean> data;

    public ChooseCountryAdapter(Context context, List<CountryBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ChooseCountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_country,null);
        return new ChooseCountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseCountryViewHolder holder, final int position) {
        holder.tvCountry.setText(data.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.chooseCountry(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChooseCountryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_country)
        TextView tvCountry;

        public ChooseCountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void chooseCountry(View v,int position);
    }
}
