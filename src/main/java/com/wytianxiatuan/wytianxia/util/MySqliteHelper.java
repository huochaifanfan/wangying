package com.wytianxiatuan.wytianxia.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuju on 2018/1/27.
 */

public class MySqliteHelper extends SQLiteOpenHelper {
    private volatile static MySqliteHelper helper;
    public static MySqliteHelper getInstance(Context context){
        if (helper == null){
            synchronized (MySqliteHelper.class){
                if (helper == null) helper = new MySqliteHelper(context);
            }
        }
        return helper;
    }
    private MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private MySqliteHelper(Context context){
        super(context,"history.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "creat table history(_id Integer primary key,content text,date text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
