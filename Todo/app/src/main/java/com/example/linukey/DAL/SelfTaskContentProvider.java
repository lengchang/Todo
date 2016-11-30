package com.example.linukey.DAL;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by linukey on 11/29/16.
 */

public class SelfTaskContentProvider extends ContentProvider {

    private final String key_id = "id";
    private final String key_title = "title";
    private final String key_content = "content";
    private final String key_starttime = "starttime";
    private final String key_endtime = "endtime";
    private final String key_clocktime = "clocktime";
    private final String key_projectId = "projectId";
    private final String key_goalId = "goalId";
    private final String key_sightId = "sightId";
    private final String key_userId = "userId";
    private final String key_state = "state";
    private final String key_isdelete = "isdelete";

    private final String tableName = "self_task";

    private final String sql_createTable = "create table " + tableName + "(" +
            key_id + " integer primary key autoincrement," +
            key_title + " varchar not null," +
            key_content + " varchar not null," +
            key_starttime + " varchar not null," +
            key_endtime + " varchar not null," +
            key_clocktime + " varchar not null," +
            key_projectId + " varchar," +
            key_goalId + " varchar," +
            key_sightId + " varchar," +
            key_userId + " varchar not null," +
            key_state + " varchar not null," +
            key_isdelete + " int not null);";

    private DBHelper dbHelper = null;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), DBHelper.dbName, null, DBHelper.dbVersion);
        dbHelper.initDb(sql_createTable, tableName);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tableName);

        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, groupBy, having, sortOrder);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String nullColumnHack = null;

        long id = db.insert(tableName, nullColumnHack, values);

        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(DBHelper.ContentUri_selftask, id);
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        } else
            return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (selection == null)
            selection = "1";

        int deleteCount = db.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int updateCount = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }
}
