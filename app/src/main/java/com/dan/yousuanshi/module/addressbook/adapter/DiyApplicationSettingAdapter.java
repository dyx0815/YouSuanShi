package com.dan.yousuanshi.module.addressbook.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.addressbook.bean.DiyApplicationSettingBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiyApplicationSettingAdapter extends RecyclerView.Adapter<DiyApplicationSettingAdapter.DiyApplicationSettingViewHolder>{

    private Context context;
    private List<DiyApplicationSettingBean> data;
    private int type = 0; //2.可输入input 3.查看详情

    public DiyApplicationSettingAdapter(Context context, List<DiyApplicationSettingBean> data) {
        this.context = context;
        this.data = data;
    }

    public DiyApplicationSettingAdapter(Context context, List<DiyApplicationSettingBean> data,int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public DiyApplicationSettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_application_list,null);
        return new DiyApplicationSettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiyApplicationSettingViewHolder holder, int position) {
        DiyApplicationSettingBean diyApplicationSettingBean = data.get(position);
        holder.tvLable.setText(diyApplicationSettingBean.getLabel()+(diyApplicationSettingBean.getIsRequired() == 0?"（选填）":""));
        holder.etInput.setHint("请输入"+diyApplicationSettingBean.getLabel()+(diyApplicationSettingBean.getIsRequired() == 0?"（选填）":""));
        if(type == 2){
            holder.etInput.setEnabled(true);
            holder.etInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    onItemClick.inputText(position,s.toString());
                }
            });
        }else if(type == 3){
            holder.etInput.setText(diyApplicationSettingBean.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DiyApplicationSettingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_lable)
        TextView tvLable;
        @BindView(R.id.et_input)
        EditText etInput;

        public DiyApplicationSettingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void inputText(int position,String text);
    }
}
