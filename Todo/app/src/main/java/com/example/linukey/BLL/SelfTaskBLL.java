package com.example.linukey.BLL;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.linukey.DAL.DBHelper;
import com.example.linukey.DAL.SelfTaskContentProvider;
import com.example.linukey.Model.SelfTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/1/16.
 */

public class SelfTaskBLL {

    public boolean saveTaskInfo(SelfTask selfTask, Context context){
        ContentValues newValues = new ContentValues();
        newValues.put(SelfTaskContentProvider.key_title, selfTask.getTitle());
        newValues.put(SelfTaskContentProvider.key_content, selfTask.getContent());
        newValues.put(SelfTaskContentProvider.key_starttime, selfTask.getStarttime());
        newValues.put(SelfTaskContentProvider.key_endtime, selfTask.getEndtime());
        newValues.put(SelfTaskContentProvider.key_clocktime, selfTask.getClocktime());
        newValues.put(SelfTaskContentProvider.key_projectId, selfTask.getProjectId());
        newValues.put(SelfTaskContentProvider.key_goalId, selfTask.getGoalId());
        newValues.put(SelfTaskContentProvider.key_sightId, selfTask.getSightId());
        newValues.put(SelfTaskContentProvider.key_userId, selfTask.getUserId());
        newValues.put(SelfTaskContentProvider.key_isdelete, selfTask.isdelete());
        newValues.put(SelfTaskContentProvider.key_state, selfTask.getState());
        newValues.put(SelfTaskContentProvider.key_istmp, selfTask.getIsTmp());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_selftask, newValues);

        if(myRowUri != null){
            return true;
        }

        return false;
    }

    public boolean deleteOne(int id, Context context){
        ContentResolver cr = context.getContentResolver();
        String where = SelfTaskContentProvider.key_id + " = " + id;
        String[] selectionArgs = null;
        int rid = cr.delete(DBHelper.ContentUri_selftask, where, selectionArgs);
        if(rid > -1)
            return true;
        return false;
    }

    public boolean completed(int id, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SelfTaskContentProvider.key_state, TodoHelper.TaskState.get("complete"));

        String where = SelfTaskContentProvider.key_id + " = " + id;
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int rid = cr.update(DBHelper.ContentUri_selftask, contentValues, where, selectionArgs);
        if(rid > -1)
            return true;
        else
            return false;
    }

    public boolean updateTaskInfo(SelfTask selfTask, Context context){
        ContentValues newValues = new ContentValues();
        newValues.put(SelfTaskContentProvider.key_title, selfTask.getTitle());
        newValues.put(SelfTaskContentProvider.key_content, selfTask.getContent());
        newValues.put(SelfTaskContentProvider.key_starttime, selfTask.getStarttime());
        newValues.put(SelfTaskContentProvider.key_endtime, selfTask.getEndtime());
        newValues.put(SelfTaskContentProvider.key_clocktime, selfTask.getClocktime());
        newValues.put(SelfTaskContentProvider.key_projectId, selfTask.getProjectId());
        newValues.put(SelfTaskContentProvider.key_goalId, selfTask.getGoalId());
        newValues.put(SelfTaskContentProvider.key_sightId, selfTask.getSightId());
        newValues.put(SelfTaskContentProvider.key_istmp, selfTask.getIsTmp());

        String where = SelfTaskContentProvider.key_id + " = " + selfTask.getId();
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int number = cr.update(DBHelper.ContentUri_selftask, newValues, where, selectionArgs);
        if(number > 0){
            return true;
        }

        return false;
    }

    public List<SelfTask> getTasks(Context context, String userId) {
        ContentResolver cr = context.getContentResolver();

        String[] result_columns = new String[]{
                SelfTaskContentProvider.keys[0],
                SelfTaskContentProvider.keys[1],
                SelfTaskContentProvider.keys[2],
                SelfTaskContentProvider.keys[3],
                SelfTaskContentProvider.keys[4],
                SelfTaskContentProvider.keys[5],
                SelfTaskContentProvider.keys[6],
                SelfTaskContentProvider.keys[7],
                SelfTaskContentProvider.keys[8],
                SelfTaskContentProvider.keys[9],
                SelfTaskContentProvider.keys[10],
                SelfTaskContentProvider.keys[11],
                SelfTaskContentProvider.keys[12]
        };

        String where = SelfTaskContentProvider.key_userId + " = " + userId;

        String whereArgs[] = null;
        String order = null;

        Cursor resultCursor = cr.query(DBHelper.ContentUri_selftask,
                result_columns, where, whereArgs, order);

        List<SelfTask> result = null;

        if(resultCursor != null && resultCursor.getCount() > 0){
            result = new ArrayList<>();
            while(resultCursor.moveToNext()){
                SelfTask selfTask = new SelfTask(
                        resultCursor.getInt(0),
                        resultCursor.getString(1),
                        resultCursor.getString(2),
                        resultCursor.getString(3),
                        resultCursor.getString(4),
                        resultCursor.getString(5),
                        resultCursor.getString(6),
                        resultCursor.getString(7),
                        resultCursor.getString(8),
                        resultCursor.getString(9),
                        resultCursor.getString(10),
                        resultCursor.getString(11),
                        resultCursor.getString(12)
                );
                result.add(selfTask);
            }
            resultCursor.close();
            return result;
        }
        return result;
    }
}
