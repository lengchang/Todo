package com.example.linukey.data.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.linukey.data.source.local.DBHelper;
import com.example.linukey.data.source.local.ProjectContentProvider;
import com.example.linukey.data.source.local.SelfTaskContentProvider;
import com.example.linukey.data.source.local.SightContentProvider;
import com.example.linukey.util.TodoHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/3/16.
 */

public class Sight extends TaskClassify {
    public Sight(int id, String title, String content, String sightId, String userId) {
        setId(id);
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(sightId);
    }

    public Sight(String title, String content, String sightId, String userId) {
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(sightId);
    }

    public Sight(){}

    public void setId(int id) {
        this.id = id;
    }

    public static boolean saveSight(Sight sight, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SightContentProvider.key_title, sight.getTitle());
        contentValues.put(SightContentProvider.key_content, sight.getContent());
        contentValues.put(SightContentProvider.key_sightId, sight.getSelfId());
        contentValues.put(SightContentProvider.key_userId, sight.getUserId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_sight, contentValues);

        if(myRowUri != null){
            return true;
        }
        return false;
    }

    public static boolean updateSight(Sight sight, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SightContentProvider.key_title, sight.getTitle());
        contentValues.put(SightContentProvider.key_content, sight.getContent());
        contentValues.put(SightContentProvider.key_sightId, sight.getSelfId());
        contentValues.put(SightContentProvider.key_userId, sight.getUserId());

        String where = SightContentProvider.key_id + " = " + sight.getId();
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int id = cr.update(DBHelper.ContentUri_sight, contentValues, where, selectionArgs);
        if(id > 0)
            return true;
        return false;
    }

    public static boolean deleteOne(int id, Context context){
        ContentResolver cr = context.getContentResolver();
        String where = SightContentProvider.key_id + " = " + id;
        String[] selectionArgs = null;
        int rid = cr.delete(DBHelper.ContentUri_sight, where, selectionArgs);
        if(rid > -1)
            return true;
        return false;
    }

    public static List<Sight> getSights(Context context){
        ContentResolver cr = context.getContentResolver();

        String[] result_columns = new String[]{
                SightContentProvider.keys[0],
                SightContentProvider.keys[1],
                SightContentProvider.keys[2],
                SightContentProvider.keys[3],
                SightContentProvider.keys[4]
        };

        String where = null;

        String whereArgs[] = null;
        String order = null;

        Cursor resultCursor = cr.query(DBHelper.ContentUri_sight,
                result_columns, where, whereArgs, order);

        List<Sight> result = null;
        if(resultCursor != null && resultCursor.getCount() > 0){
            result = new ArrayList<>();
            while(resultCursor.moveToNext()){
                Sight sight = new Sight(
                        resultCursor.getInt(0),
                        resultCursor.getString(1),
                        resultCursor.getString(2),
                        resultCursor.getString(3),
                        resultCursor.getString(4)
                );
                result.add(sight);
            }
        }
        return result;
    }
}
