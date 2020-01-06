package com.dan.yousuanshi.module.shared.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.shared.adapter.SortModelAdapter;
import com.dan.yousuanshi.module.shared.bean.AddWorkBenchBean;
import com.dan.yousuanshi.module.shared.presenter.SortModelActivityPresenter;
import com.dan.yousuanshi.module.shared.view.SortModelActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class SortModelActivity extends BaseActivity<SortModelActivityPresenter> implements SortModelActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<AddWorkBenchBean> modelList = new ArrayList<>();
    private SortModelAdapter adapter;
    private int companyId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort_model;
    }

    @Nullable
    @Override
    protected SortModelActivityPresenter initPresenter() {
        return new SortModelActivityPresenter();
    }

    @Override
    protected void initView() {
        companyId = getIntent().getIntExtra("companyId",0);
        List<AddWorkBenchBean> data = getIntent().getParcelableArrayListExtra("data");
        if(data != null){
            modelList.addAll(data);
        }
        for(AddWorkBenchBean item : modelList){
            item.getChildren().remove(item.getChildren().size()-1);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SortModelAdapter(this,modelList);
        recyclerView.setAdapter(adapter);
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
                        Collections.swap(modelList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(modelList, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition,toPosition);
//                adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
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
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void sortModelSuccess(int code, BaseBean data) {
        ToastUtils.showCenterToast(this,"排序成功~");
        finish();
    }

    @Override
    public void sortModelFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                Map<String,Object> map = new HashMap<>();
                Map<String,Object> sort = new HashMap<>();
                for(int i = 0;i<modelList.size();i++){
                    sort.put(String.valueOf(adapter.getData().get(i).getId()),i);
                    for(int j = 0;j<modelList.get(i).getChildren().size();j++){
                        sort.put(String.valueOf(adapter.getData().get(i).getChildren().get(j).getId()),j);
                    }
                }
                map.put("companyId",companyId);
                map.put("rankMap",new Gson().toJson(sort));
                presenter.sortModel(map);
                break;
        }
    }
}
