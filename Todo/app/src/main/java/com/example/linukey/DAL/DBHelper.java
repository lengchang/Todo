package com.example.linukey.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by linukey on 11/29/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final Uri ContentUri_selftask = Uri.parse("content://com.linukey.Todo.self_task");
    public static final Uri ContentUri_user = Uri.parse("content://com.linukey.Todo.user");

    public static final String dbName = "Todo.db";
    public static final int dbVersion = 1;
    private String tableCreate;
    private String tableName;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void initDb(String tableCreate, String tableName){
        this.tableCreate = tableCreate;
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if it exists" + tableName);
        onCreate(db);
    }
}
