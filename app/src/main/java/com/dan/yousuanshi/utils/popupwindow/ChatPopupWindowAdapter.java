package com.dan.yousuanshi.utils.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;

import java.util.ArrayList;

public class ChatPopupWindowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**上下文*/
    private Context myContext;
    /**自定义列表项标题集合*/
    private ArrayList<PopUpMenuBean> itemList;

    /**
     * 构造函数
     */
    public ChatPopupWindowAdapter(Context context, ArrayList<PopUpMenuBean> itemlist) {
        myContext = context;
        this.itemList = itemlist;
    }

    /**
     * 获取总的条目数
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.item_chat_popupwindow, parent, false);
        ChatPopupWindowAdapter.ItemViewHolder itemViewHolder = new ChatPopupWindowAdapter.ItemViewHolder(view);
        return itemViewHolder;
    }

    /**
     * 声明grid列表项ViewHolder*/
    static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ItemViewHolder(View view)
        {
            super(view);
            itemText = (TextView) view.findViewById(R.id.tv_pop_name);
            itemImg = (ImageView) view.findViewById(R.id.iv_pop_icon);
            item= view.findViewById(R.id.ll_item);
        }

        ImageView itemImg;
        TextView itemText;
        LinearLayout item;
    }

    /**
     * 将数据绑定至ViewHolder
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int index) {

        //判断属于列表项还是上拉加载区域
        if(viewHolder instanceof ChatPopupWindowAdapter.ItemViewHolder){
            final ChatPopupWindowAdapter.ItemViewHolder itemViewHold = ((ChatPopupWindowAdapter.ItemViewHolder)viewHolder);

            if(itemList.get(index).getImgResId() != 0){
                itemViewHold.itemImg.setImageResource(itemList.get(index).getImgResId());//赋值图标
            }else{
                itemViewHold.itemImg.setVisibility(View.GONE);//如果没有res的id值，则隐藏图标
            }

            itemViewHold.itemText.setText(itemList.get(index).getItemStr());//赋值文本

            //如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null)
            {
                itemViewHold.item.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int position = itemViewHold.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了
                        mOnItemClickLitener.onItemClick(itemList.get(index).getItemStr());
                    }
                });
            }
        }
    }

    /*=====================添加OnItemClickListener回调================================*/
    public interface OnItemClickLitener
    {
        void onItemClick(String text);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
