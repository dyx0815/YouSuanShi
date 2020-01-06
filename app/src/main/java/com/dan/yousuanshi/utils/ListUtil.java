package com.dan.yousuanshi.utils;

import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListUtil {
    /**
     * 对list时间排序
     * @param list
     */
    public static void sortList(List<ChatBean> list){
        Collections.sort(list, new Comparator<ChatBean>() {
            @Override
            public int compare(ChatBean o1, ChatBean o2) {
                return DateUtil.StringToDate(o2.getTime()).compareTo(DateUtil.StringToDate(o1.getTime()));
            }
        });
    }

    /**
     * 对list时间排序
     * @param list
     */
    public static void sortGroupFile(List<GroupFileBean.DataBean> list){
        Collections.sort(list, new Comparator<GroupFileBean.DataBean>() {
            @Override
            public int compare(GroupFileBean.DataBean o1, GroupFileBean.DataBean o2) {
                return DateUtil.StringToDate(o2.getCreate_time()).compareTo(DateUtil.StringToDate(o1.getCreate_time()));
            }
        });
    }
}
