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

    public static final String key_id = "id";
    public static final String key_title = "title";
    public static final String key_content = "content";
    public static final String key_starttime = "starttime";
    public static final String key_endtime = "endtime";
    public static final String key_clocktime = "clocktime";
    public static final String key_projectId = "projectId";
    public static final String key_goalId = "goalId";
    public static final String key_sightId = "sightId";
    public static final String key_userId = "userId";
    public static final String key_state = "state";
    public static final String key_isdelete = "isdelete";

    public static final String tableName = "selftask";

    public static final String sql_createTable = "create table " + tableName + "(" +
            key_id + " integer primary key autoincrement," +
            key_title + " varchar not null," +
            key_content + " varchar not null," +
            key_starttime + " varchar not null," +
            key_endtime + " varchar not null," +
            key_clocktime + " varchar not null," +
            key_projectId + " varchar not null," +
            key_goalId + " varchar not null," +
            key_sightId + " varchar not null," +
            key_userId + " varchar not null," +
            key_state + " varchar not null," +
            key_isdelete + " varchar not null);";

    private DBHelper dbHelper = null;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), DBHelper.dbName, null, DBHelper.dbVersion);
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
            Uri insertedId = ContentUris.withAppendedId(uri, id);
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
