package com.dan.yousuanshi.module.chat.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.utils.ItemSlideHelper;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.ListUtil;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> implements ItemSlideHelper.Callback {

    private List<ChatBean> data;
    private Context context;
    private RecyclerView recyclerView;
    private OnItemClickListener itemClickListener;
    private int uId;

    public MessageAdapter(Context context, List<ChatBean> data) {
        this.context = context;
        this.data = data;
        uId = UserUtils.getInstance().getUserBean().getUser_id();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, final int position) {
        final ChatBean chatBean = data.get(position);
        holder.tvName.setText(chatBean.getName());//昵称
        holder.tvMessage.setText(chatBean.getStxt());//消息
        if (chatBean.getType() == Constant.CHAT_GROUP_TYPE) {//群
            Glide.with(context).load(chatBean.getGroupIcon()).into(holder.rivHeadIcon);//用户头像
        } else {//单聊
            Glide.with(context).load(chatBean.getUserIconUrl()).into(holder.rivHeadIcon);//用户头像
        }
        if (chatBean.isTop()) {//置顶标识
            holder.ivIsTop.setVisibility(View.VISIBLE);
            holder.llTop.setVisibility(View.GONE);
            holder.llRemoveTop.setVisibility(View.VISIBLE);
        } else {
            holder.ivIsTop.setVisibility(View.GONE);
            holder.llTop.setVisibility(View.VISIBLE);
            holder.llRemoveTop.setVisibility(View.GONE);
        }
        if (chatBean.getMessageCount() != 0) {//消息数量
            if (chatBean.getMessageCount() > 99) {
                holder.tvMessageCount.setText("99+");
            } else {
                holder.tvMessageCount.setText(String.valueOf(chatBean.getMessageCount()));
            }
            holder.tvMessageCount.setVisibility(View.VISIBLE);
        } else {
            holder.tvMessageCount.setVisibility(View.GONE);
        }
        holder.tvTime.setText(DateUtil.showTime(DateUtil.StringToDate(chatBean.getTime()), new Date()));//时间
        holder.llTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTop(position);
            }
        });
        holder.llRemoveTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTop(position);
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//删除
                data.remove(position);
                Integer position = chatBean.getMessageCount();
                EventBus.getDefault().post(position);
                notifyDataSetChanged();
                DataBaseHelper.deleteChatList(context, uId, chatBean.getPid());
                if (chatBean.getType() == Constant.CHAT_ONE_TYPE) {
                    if (DataBaseHelper.tabbleIsExist(context, DataBaseHelper.getChatRecordTableName(chatBean.getPid(), uId))) {
                        DataBaseHelper.deleteChatRecordTable(context, chatBean.getPid(), uId);
                    }
                } else if (chatBean.getType() == Constant.CHAT_GROUP_TYPE) {
                    if (DataBaseHelper.tabbleIsExist(context, DataBaseHelper.getGroupChatRecordTableName(chatBean.getPid(), uId))) {
                        DataBaseHelper.deleteGroupChatRecordTable(context, chatBean.getPid(), uId);
                    }
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, position);
                }
            }
        });
        if (chatBean.getType() == Constant.CHAT_GROUP_TYPE) {
            if (chatBean.getGroupType() == Constant.INTERNAL_GROUP) {
                holder.ivChatType.setImageResource(R.mipmap.icon_message_internal);
                holder.ivChatType.setVisibility(View.VISIBLE);
            } else if (chatBean.getGroupType() == Constant.DEPARTMENT_GROUP) {
                holder.ivChatType.setImageResource(R.mipmap.icon_message_department);
                holder.ivChatType.setVisibility(View.VISIBLE);
            } else if (chatBean.getGroupType() == Constant.ALL_PEOPLE_GROUP) {
                holder.ivChatType.setImageResource(R.mipmap.icon_message_all);
                holder.ivChatType.setVisibility(View.VISIBLE);
            }else{
                holder.ivChatType.setVisibility(View.GONE);
            }
        }else{
            holder.ivChatType.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.addOnItemTouchListener(new ItemSlideHelper(this.recyclerView.getContext(), this));
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            return viewGroup.getChildAt(1).getLayoutParams().width
                    + viewGroup.getChildAt(2).getLayoutParams().width;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return this.recyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return this.recyclerView.findChildViewUnder(x, y);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.riv_head_icon)
        RoundedImageView rivHeadIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.iv_is_top)
        ImageView ivIsTop;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_message_count)
        TextView tvMessageCount;
        @BindView(R.id.ll_top)
        LinearLayout llTop;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.ll_remove_top)
        LinearLayout llRemoveTop;
        @BindView(R.id.iv_chat_type)
        ImageView ivChatType;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private void addTop(int position) {
        ChatBean chatBean = data.get(position);
        data.remove(position);
        chatBean.setTop(true);
        data.add(0, chatBean);
        ContentValues values = new ContentValues();
        values.put("is_top", 1);
        DataBaseHelper.updateChatListById(context, uId, chatBean.getChatListId(), values);
        notifyDataSetChanged();
    }

    private void removeTop(int position) {
        ChatBean chatBean = data.get(position);
        data.remove(position);
        chatBean.setTop(false);
        data.add(chatBean);
        ContentValues values = new ContentValues();
        values.put("is_top", 0);
        DataBaseHelper.updateChatListById(context, uId, chatBean.getChatListId(), values);
        List<ChatBean> chatBeanList = new ArrayList<>();
        Iterator<ChatBean> iterator = data.iterator();
        while (iterator.hasNext()){
            ChatBean chatBean1 = iterator.next();
            if(chatBean1.isTop()){
                chatBeanList.add(chatBean1);
                iterator.remove();
            }
        }
        ListUtil.sortList(data);
        for(ChatBean chatBean1 : chatBeanList){
            data.add(0,chatBean1);
        }
        notifyDataSetChanged();
    }
}
