package com.example.linukey.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.linukey.Model.Sight;

/**
 * Created by linukey on 11/29/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final Uri ContentUri_selftask = Uri.parse("content://com.linukey.Todo.self_task");
    public static final Uri ContentUri_user = Uri.parse("content://com.linukey.Todo.user");
    public static final Uri ContentUri_project = Uri.parse("content://com.linukey.Todo.project");
    public static final Uri ContentUri_goal = Uri.parse("content://com.linukey.Todo.goal");
    public static final Uri ContentUri_sight = Uri.parse("content://com.linukey.Todo.sight");
    public static final Uri ContentUri_team = Uri.parse("content://com.linukey.Todo.team");
    public static final Uri ContentUri_teamtask = Uri.parse("content://com.linukey.Todo.teamtask");

    public static final String dbName = "Todo.db";
    public static final int dbVersion = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SelfTaskContentProvider.sql_createTable);
        db.execSQL(UserContentProvider.sql_createTable);
        db.execSQL(ProjectContentProvider.sql_createTable);
        db.execSQL(GoalContentProvider.sql_createTable);
        db.execSQL(SightContentProvider.sql_createTable);
        db.execSQL(TeamContentProvider.sql_createTable);
        db.execSQL(TeamTaskContentProvider.sql_createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if it exists" + SelfTaskContentProvider.tableName);
        db.execSQL("drop table if it exists" + UserContentProvider.tableName);
        db.execSQL("drop table if it exists" + ProjectContentProvider.tableName);
        db.execSQL("drop table if it exists" + GoalContentProvider.tableName);
        db.execSQL("drop table if it exists" + SightContentProvider.tableName);
        db.execSQL("drop table if it exists" + TeamContentProvider.tableName);
        db.execSQL("drop table if it exists" + TeamTaskContentProvider.tableName);
        onCreate(db);
    }
}
