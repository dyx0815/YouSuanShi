package com.dan.yousuanshi.utils.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.utils.DpToPxUtils;

import java.util.ArrayList;

public class ChatPopupWindow  {
    private static final String TAG = PopupWindowMenuUtil.class.getSimpleName();
    /**菜单的弹出窗口*/
    private static PopupWindow popupWindow = null;

    /**显示popupWindow弹出框*/
    public static void showPopupWindows(final Context context, final View spinnerview, ArrayList<PopUpMenuBean> mArrayList
            , final ChatPopupWindow.OnListItemClickLitener mOnListItemClickLitener, int chatPosition,int flag,TextView textView){

        if(popupWindow != null){
            if(popupWindow.isShowing()){
                popupWindow.dismiss();
                popupWindow = null;
            }
        }
        //一个自定义的布局，作为显示的内容
        View popupWindowView = LayoutInflater.from(context).inflate(R.layout.layout_chat_pop, null);

        /**在初始化contentView的时候，强制绘制contentView，并且马上初始化contentView的尺寸。
         * 另外一个点需要注意：popwindow_layout.xml的根Layout必须为LinearLayout；如果为RelativeLayout的话，会导致程序崩溃。*/
        popupWindowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //用于获取单个列表项的高度【用于计算大于n个列表项的时候，列表的总高度值n * listitemView.getMeasuredHeight()】
        View listitemView = LayoutInflater.from(context).inflate(R.layout.item_chat_popupwindow, null);
        listitemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        //列表
        RecyclerView mListView = (RecyclerView) popupWindowView.findViewById(R.id.rv_pop);
        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,4);
        mListView.setLayoutManager(gridLayoutManager);

        ChatPopupWindowAdapter adapter = new ChatPopupWindowAdapter(context,mArrayList);

        mListView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new ChatPopupWindowAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(String text) {
                ChatPopupWindow.closePopupWindows();//关闭列表对话框，注意会执行onDismiss()方法
                mOnListItemClickLitener.onListItemClick(text,chatPosition,textView);
            }
        });
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //实例化PopupWindow【宽度为自身宽度，高度为自身高度】
        //popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置当大于6个列表项的时候，设置列表总高度值为6个列表项的高度值
        popupWindow.setTouchable(true);//设置可以触摸
        popupWindow.setFocusable(true);//代表可以允许获取焦点的，如果有输入框的话，可以聚焦

        //监听popWindow的隐藏时执行的操作--这个不错
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //执行还原原始状态的操作，比如选中状态颜色高亮显示[去除],不能使用notifyDataSetInvalidated()，否则会出现popwindow显示错位的情况
                Log.e(TAG,"onDismiss");
            }
        });

        //下面两个参数是实现点击点击外面隐藏popupwindow的
        //这里设置显示PopuWindow之后在外面点击是否有效。如果为false的话，那么点击PopuWindow外面并不会关闭PopuWindow。当然这里很明显只能在Touchable下才能使用。不设置此项则下面的捕获window外touch事件就无法触发。
        popupWindow.setOutsideTouchable(true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        //方式一
        ColorDrawable dw = new ColorDrawable(0x00000000);//设置背景为透明
        popupWindow.setBackgroundDrawable(dw);

        //int xPos = - popupWindow.getWidth() / 2 + view.getWidth() / 2;//X轴的偏移值:xoff表示x轴的偏移，正值表示向右，负值表示向左；
        int xPos = 0;//X轴的偏移值:xoff表示x轴的偏移，正值表示向左，负值表示向右；
        int yPos = 0;//Y轴的偏移值相对某个控件的位置，有偏移;yoff表示相对y轴的偏移，正值是向下，负值是向上；
        popupWindowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
       int  popupHeight = popupWindowView.getMeasuredHeight();
       int  popupWidth = popupWindowView.getMeasuredWidth();
        //=======展现在控件的下方
        //相对于当前view进行位置设置
        int[] location = new int[2];
        spinnerview.getLocationOnScreen(location);
        //在控件上方显示
        if(location[1] - popupHeight > 0){
            if(flag == 0){//收到的消息
                mListView.setBackgroundResource(R.mipmap.icon_popupwindow_bg_left);
                mListView.setPadding(0,0,0, DpToPxUtils.dip2px(context,25));
                popupWindow.showAtLocation(spinnerview, Gravity.NO_GRAVITY, -DpToPxUtils.dip2px(context,50)+(location[0] + spinnerview.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
            }else{
                mListView.setBackgroundResource(R.mipmap.icon_popupwindow_bg);
                mListView.setPadding(0,0,0, DpToPxUtils.dip2px(context,25));
                popupWindow.showAtLocation(spinnerview, Gravity.NO_GRAVITY, (location[0] + spinnerview.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
            }
        }else{//在控件下方显示
            if(flag == 0){//收到的消息
                mListView.setBackgroundResource(R.mipmap.icon_popupwindow_bg_top_left);
                mListView.setPadding(0,DpToPxUtils.dip2px(context,15),0, DpToPxUtils.dip2px(context,10));
                popupWindow.showAsDropDown(spinnerview,-DpToPxUtils.dip2px(context,50),0);
            }else{
                mListView.setBackgroundResource(R.mipmap.icon_popupwindow_bg_top_right);
                mListView.setPadding(0,DpToPxUtils.dip2px(context,15),0, DpToPxUtils.dip2px(context,10));
                popupWindow.showAsDropDown(spinnerview);
            }
        }
    }

    /**关闭菜单弹出框*/
    public static void closePopupWindows(){
        if(popupWindow != null){
            if(popupWindow.isShowing()){
                popupWindow.dismiss();
                popupWindow = null;
            }
        }
    }

    /*=====================添加OnListItemClickLitener回调================================*/
    public interface OnListItemClickLitener
    {
        void onListItemClick(String text, int chatPosition, TextView textView);
    }

    private ChatPopupWindow.OnListItemClickLitener mOnListItemClickLitener;

    public void setOnItemClickLitener(ChatPopupWindow.OnListItemClickLitener mOnListItemClickLitener)
    {
        this.mOnListItemClickLitener = mOnListItemClickLitener;
    }

    /**Android中Popwindow使用以及背景色变灰【不好用】
     * 【有问题，首先在DialogFragment中无法实现这个效果，只能在activity和fragment中调用
     * 其次，调用这个代码后，打开其他的对话框的时候灰色背景会出现突然闪现的问题】
     * https://blog.csdn.net/zz6880817/article/details/52189699
     * https://blog.csdn.net/chenbing81/article/details/52059979*/

    /**
     * 改变背景颜色
     * @param  bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    private static void setBackgroundAlpha(Context context, Float bgAlpha){
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = bgAlpha;

        ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity)context).getWindow().setAttributes(lp);

    }
}
