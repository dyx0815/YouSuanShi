package com.dan.yousuanshi.module.shared.adapter;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortModelAdapter extends RecyclerView.Adapter<SortModelAdapter.SortModelViewHolder>{

    private Context context;
    private List<AddWorkBenchBean> data;

    public SortModelAdapter(Context context, List<AddWorkBenchBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SortModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sort_model,null);
        return new SortModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortModelViewHolder holder, int position) {
        holder.tvModelName.setText(data.get(position).getModel_name()+"");
        SortModel2Adapter adapter = new SortModel2Adapter(context,data.get(position).getChildren());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(adapter);
        holder.ivOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.recyclerView.getVisibility() == View.GONE){
                    holder.ivOpen.setImageResource(R.mipmap.icon_shared_add_work_bench_close);
                    holder.recyclerView.setVisibility(View.VISIBLE);
                }else{
                    holder.ivOpen.setImageResource(R.mipmap.icon_shared_add_work_bench_open);
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
                vib.vibrate(70);
                return false;
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(data.get(position).getChildren(), i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(data.get(position).getChildren(), i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });
        itemTouchHelper.attachToRecyclerView(holder.recyclerView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SortModelViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_open)
        ImageView ivOpen;
        @BindView(R.id.tv_model_name)
        TextView tvModelName;
        @BindView(R.id.iv_sort)
        ImageView ivSort;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        public SortModelViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void move(int fromPosition,int toPosition){
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition,toPosition);
    }

    public List<AddWorkBenchBean> getData() {
        return data;
    }
}
