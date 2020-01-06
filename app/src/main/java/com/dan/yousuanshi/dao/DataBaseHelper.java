package com.dan.yousuanshi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.bean.OftenModelBean;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper dataBaseHelper = null;
    public final static String CHAT_RECORD_TABLE_NAME = "chat_record_";
    public final static String GROUP_CHAT_RECORD_TABLE_NAME = "group_chat_record_";
    public static final String DATABASE_NAME = "yousuanshi_chat_record";
    private static final String CHAT_LIST_TABLE_NAME = "chat_list_";
    private static final String OFTEN_MODEL = "often_model_";

    private DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * 数据库升级操作
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 获取sqlite对象
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getSQLite(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, 1);
        }
        return dataBaseHelper.getWritableDatabase();
    }

    /**
     * 创建聊天记录表
     *
     * @param pId 好友id
     */
    public static void createChatTable(Context context, Integer pId, Integer uId) {
        String sql = "create table " + getChatRecordTableName(pId, uId) + "(" +
                "id integer primary key autoincrement," +
                "message_type integer not null," +
                "message_txt text not null," +
                "send_time text not null," +
                "is_read INTEGER not null," +
                "send_flag integer not null," +
                "path text," +
                "msgid text," +
                "head_icon text," +
                "message_temp text)";
        getSQLite(context).execSQL(sql);
    }

    /**
     * 创建群聊天记录表
     *
     * @param pId 好友id
     */
    public static void createGroupChatTable(Context context, Integer pId, Integer uId) {
        String sql = "create table " + getGroupChatRecordTableName(pId, uId) + "(" +
                "id integer primary key autoincrement," +
                "message_type integer not null," +
                "message_txt text not null," +
                "send_time text not null," +
                "is_read INTEGER not null," +
                "send_flag integer not null," +
                "path text," +
                "msgid text," +
                "head_icon text," +
                "nick_name text," +
                "s_id integer," +
                "message_temp text)";
        getSQLite(context).execSQL(sql);
    }

    /**
     * 新增聊天记录
     *
     * @param context
     * @param pId
     * @param values
     */
    public static long insertChatRecord(Context context, Integer pId, Integer uId, ContentValues values) {
        long id = getSQLite(context).insert(getChatRecordTableName(pId, uId), null, values);
        Log.e("DataBase",id+"");
        return id;
    }

    /**
     * 新增群聊天记录
     *
     * @param context
     * @param pId
     * @param values
     */
    public static long insertGroupChatRecord(Context context, Integer pId, Integer uId, ContentValues values) {
        long id = getSQLite(context).insert(getGroupChatRecordTableName(pId, uId), null, values);
        Log.e("DataBase",id+"");
        return id;
    }

    public static void updateChatRecord(Context context, Integer pId, Integer uId, ContentValues values,Long id) {
        Log.e("DataBase","updateChatRecord:"+getSQLite(context).update(getChatRecordTableName(pId, uId), values,"id=?", new String[]{String.valueOf(id)})+"");
    }

    public static void updateGroupChatRecord(Context context, Integer pId, Integer uId, ContentValues values,Long id) {
        Log.e("DataBase","updateChatRecord:"+getSQLite(context).update(getGroupChatRecordTableName(pId, uId), values,"id=?", new String[]{String.valueOf(id)})+"");
    }

    /**
     * 删除单个聊天记录
     *
     * @param context
     * @param pId
     * @param position
     */
    public static void deleteChatRecord(Context context, Integer pId, Integer uId, int position) {
        getSQLite(context).delete(getChatRecordTableName(pId, uId)
                , "id=?", new String[]{String.valueOf(position)});
    }

    /**
     * 删除单个聊天记录
     *
     * @param context
     * @param pId
     * @param position
     */
    public static void deleteGroupChatRecord(Context context, Integer pId, Integer uId, int position) {
        getSQLite(context).delete(getGroupChatRecordTableName(pId, uId)
                , "id=?", new String[]{String.valueOf(position)});
    }

    public static void deleteChatRecordTable(Context context, Integer pId, Integer uId){
        String sql = "drop table "+getChatRecordTableName(pId, uId);
        getSQLite(context).execSQL(sql);
    }

    public static void deleteGroupChatRecordTable(Context context, Integer pId, Integer uId){
        String sql = "drop table "+getGroupChatRecordTableName(pId, uId);
        getSQLite(context).execSQL(sql);
    }

    /**
     * 查询聊天记录
     *
     * @param context
     * @param pId
     * @return
     */
    public static List<ChatBean> queryChatRecord(Context context, Integer pId, Integer uId,String name) {
        Cursor cursor = getSQLite(context).query(getChatRecordTableName(pId, uId)
                , new String[]{"message_type", "message_txt", "is_read", "send_time", "send_flag","path","msgid","id","head_icon","message_temp"}
                , null, null, null, null, null);
        List<ChatBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ChatBean chatBean = new ChatBean();
            chatBean.setFileype(cursor.getInt(cursor.getColumnIndex("message_type")));
            chatBean.setStxt(cursor.getString(cursor.getColumnIndex("message_txt")));
            chatBean.setIsRead(cursor.getInt(cursor.getColumnIndex("is_read")));
            chatBean.setTime(cursor.getString(cursor.getColumnIndex("send_time")));
            chatBean.setFlag(cursor.getInt(cursor.getColumnIndex("send_flag")));
            chatBean.setPath(cursor.getString(cursor.getColumnIndex("path")));
            chatBean.setMsgid(cursor.getString(cursor.getColumnIndex("msgid")));
            chatBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            chatBean.setUserIconUrl(cursor.getString(cursor.getColumnIndex("head_icon")));
            chatBean.setName(name);
            chatBean.setTemp(cursor.getString(cursor.getColumnIndex("message_temp")));
            if(list.size() == 0 || DateUtil.diffMinute(list.get(list.size()-1).getTime(),chatBean.getTime())>=3){
                chatBean.setIsTime(true);
            }
            list.add(chatBean);
        }
        return list;
    }

    /**
     * 查询聊天记录
     *
     * @param context
     * @param pId
     * @return
     */
    public static List<ChatBean> queryGroupChatRecord(Context context, Integer pId, Integer uId) {
        Cursor cursor = getSQLite(context).query(getGroupChatRecordTableName(pId, uId)
                , new String[]{"message_type", "message_txt", "is_read", "send_time", "send_flag","path","msgid","id","head_icon","s_id","nick_name","message_temp"}
                , null, null, null, null, null);
        List<ChatBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ChatBean chatBean = new ChatBean();
            chatBean.setFileype(cursor.getInt(cursor.getColumnIndex("message_type")));
            chatBean.setStxt(cursor.getString(cursor.getColumnIndex("message_txt")));
            chatBean.setIsRead(cursor.getInt(cursor.getColumnIndex("is_read")));
            chatBean.setTime(cursor.getString(cursor.getColumnIndex("send_time")));
            chatBean.setFlag(cursor.getInt(cursor.getColumnIndex("send_flag")));
            chatBean.setPath(cursor.getString(cursor.getColumnIndex("path")));
            chatBean.setMsgid(cursor.getString(cursor.getColumnIndex("msgid")));
            chatBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            chatBean.setUserIconUrl(cursor.getString(cursor.getColumnIndex("head_icon")));
            chatBean.setsId(cursor.getInt(cursor.getColumnIndex("s_id")));
            chatBean.setName(cursor.getString(cursor.getColumnIndex("nick_name")));
            chatBean.setTemp(cursor.getString(cursor.getColumnIndex("message_temp")));
            if(list.size() == 0 || DateUtil.diffMinute(list.get(list.size()-1).getTime(),chatBean.getTime())>=3){
                chatBean.setIsTime(true);
            }
            list.add(chatBean);
        }
        return list;
    }

    public static boolean tabbleIsExist(Context context, String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = getSQLite(context).rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
        }
        return result;
    }

    public static String getChatRecordTableName(Integer pId, Integer uId) {
        return CHAT_RECORD_TABLE_NAME + pId + "_" + uId;
    }

    public static String getGroupChatRecordTableName(Integer pId, Integer uId) {
        return GROUP_CHAT_RECORD_TABLE_NAME + pId + "_" + uId;
    }

    public static String getChatListTableName(Integer uId) {
        return CHAT_LIST_TABLE_NAME + uId;
    }

    public static String getOftenModelTableName(Integer uId) {
        return OFTEN_MODEL + uId;
    }

    public static void createChatListTable(Context context, Integer uId) {
        String sql = "create table " + getChatListTableName(uId) + "(" +
                "id integer primary key autoincrement," +
                "pid integer not null," +
                "p_name text not null," +
                "last_message text not null," +
                "last_time text not null," +
                "is_top integer not null," +//置顶为1,,不置顶为0
                "p_icon text not null," +
                "message_count integer not null," +
                "type integer not null," +
                "group_type integer," +
                "file_type integer )";
        getSQLite(context).execSQL(sql);
    }

    public static long insertChatList(Context context, Integer uId, ContentValues values) {
        Log.e("DataBase","添加到消息列表");
        return getSQLite(context).insert(getChatListTableName(uId), null, values);
    }

    public static void deleteChatList(Context context, Integer uId, Integer pId) {
        getSQLite(context).delete(getChatListTableName(uId), "pid=?"
                , new String[]{String.valueOf(pId)});
    }

    public static void removeMessageCount(Context context, Integer uId, Integer pId) {
        ContentValues values = new ContentValues();
        values.put("message_count", 0);
        getSQLite(context).update(getChatListTableName(uId), values
                , "pid=?", new String[]{String.valueOf(pId)});
    }

    public static long updateChatList(Context context, Integer uId, Integer pId, ContentValues values) {
        return getSQLite(context).update(getChatListTableName(uId), values
                , "pid=?", new String[]{String.valueOf(pId)});
    }

    public static int updateChatListById(Context context, Integer uId, Long id, ContentValues values) {
        return getSQLite(context).update(getChatListTableName(uId), values
                , "id=?", new String[]{String.valueOf(id)});
    }

    public static List<ChatBean> queryChatList(Context context, Integer uId) {
        List<ChatBean> list = new ArrayList<>();
        Cursor cursor = getSQLite(context).query(getChatListTableName(uId)
                , new String[]{"pid", "p_name", "last_message", "last_time", "is_top", "p_icon", "message_count","type","group_type","file_type","id"}
                , null, null, null, null, null);
        while (cursor.moveToNext()) {
            ChatBean chatBean = new ChatBean();
            chatBean.setPid(cursor.getInt(cursor.getColumnIndex("pid")));
            chatBean.setMid(cursor.getInt(cursor.getColumnIndex("pid")));
            chatBean.setName(cursor.getString(cursor.getColumnIndex("p_name")));
            chatBean.setStxt(cursor.getString(cursor.getColumnIndex("last_message")));
            chatBean.setTime(cursor.getString(cursor.getColumnIndex("last_time")));
            if (cursor.getInt(cursor.getColumnIndex("is_top")) == 0) {
                chatBean.setTop(false);
            } else {
                chatBean.setTop(true);
            }
            chatBean.setUserIconUrl(cursor.getString(cursor.getColumnIndex("p_icon")));
            chatBean.setMessageCount(cursor.getInt(cursor.getColumnIndex("message_count")));
            chatBean.setType(cursor.getInt(cursor.getColumnIndex("type")));
            if(chatBean.getType() == Constant.CHAT_GROUP_TYPE){
                chatBean.setGroupIcon(cursor.getString(cursor.getColumnIndex("p_icon")));
            }
            chatBean.setGroupType(cursor.getInt(cursor.getColumnIndex("group_type")));
            chatBean.setFileype(cursor.getInt(cursor.getColumnIndex("file_type")));
            chatBean.setChatListId(cursor.getLong(cursor.getColumnIndex("id")));
            list.add(chatBean);
        }
        return list;
    }

    public static List<MyFriendBean> queryChatList2(Context context, Integer uId) {
        List<MyFriendBean> list = new ArrayList<>();
        Cursor cursor = getSQLite(context).query(getChatListTableName(uId)
                , new String[]{"pid", "p_name", "p_icon"}
                , "type=?", new String[]{"2"}, null, null, null);
        while (cursor.moveToNext()) {
            MyFriendBean myFriendBean = new MyFriendBean();
            myFriendBean.setUser_id(cursor.getInt(cursor.getColumnIndex("pid")));
            myFriendBean.setNick_name(cursor.getString(cursor.getColumnIndex("p_name")));
            myFriendBean.setUser_portrait(cursor.getString(cursor.getColumnIndex("p_icon")));
            list.add(myFriendBean);
        }
        return list;
    }

    public static void deleteChatListTable(Context context, Integer uId) {
        String sql = "drop table " + getChatListTableName(uId);
        getSQLite(context).execSQL(sql);
    }

    /**
     * 创建常用组件model
     *
     */
    public static void createOftenModelTable(Context context, Integer uId) {
        String sql = "create table " + getOftenModelTableName(uId) + "(" +
                "model_id integer primary key," +
                "click_count integer not null)";
        getSQLite(context).execSQL(sql);
    }

    public static long insertOftenModel(Context context, Integer uId,ContentValues values){
        return getSQLite(context).insert(getOftenModelTableName(uId),null,values);
    }

    public static int updateOftenModel(Context context, Integer uId,Integer modelId,ContentValues values){
        return getSQLite(context).update(getOftenModelTableName(uId),values,"model_id=?",new String[]{modelId+""});
    }

    public static List<OftenModelBean> queryOftenModel(Context context, Integer uId){
        String sql = "select * from "+getOftenModelTableName(uId)+" order by click_count desc limit 0,8";
        Cursor cursor =  getSQLite(context).rawQuery(sql,null);
        List<OftenModelBean> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new OftenModelBean(cursor.getInt(cursor.getColumnIndex("model_id")),cursor.getInt(cursor.getColumnIndex("click_count"))));
        }
        return list;
    }

    public static OftenModelBean queryOftenModelById(Context context,Integer uId,Integer modelId){
        Cursor cursor = getSQLite(context).query(getOftenModelTableName(uId)
                , new String[]{"model_id", "click_count"}
                , "model_id=?", new String[]{modelId+""}, null, null, null);
        OftenModelBean oftenModelBean = null;
        while (cursor.moveToNext()){
            oftenModelBean = new OftenModelBean(cursor.getInt(cursor.getColumnIndex("model_id")),cursor.getInt(cursor.getColumnIndex("click_count")));
        }
        return oftenModelBean;
    }
}
