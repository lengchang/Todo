package com.example.linukey.data.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.linukey.data.source.local.DBHelper;
import com.example.linukey.data.source.local.GoalContentProvider;
import com.example.linukey.data.source.local.ProjectContentProvider;
import com.example.linukey.data.source.local.SelfTaskContentProvider;
import com.example.linukey.util.TodoHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/3/16.
 */

public class Goal extends TaskClassify {
    private String state;


    public Goal(int id, String title, String content, String state, String goalId, String userId) {
        setId(id);
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(goalId);
        this.state = state;
    }

    public Goal(String title, String content, String state, String goalId, String userId) {
        this.state = state;
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(goalId);
    }

    public Goal(){}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static boolean deleteOne(int id, Context context){
        ContentResolver cr = context.getContentResolver();
        String where = GoalContentProvider.key_id + " = " + id;
        String[] selectionArgs = null;
        int rid = cr.delete(DBHelper.ContentUri_goal, where, selectionArgs);
        if(rid > -1)
            return true;
        return false;
    }

    public static boolean saveGoal(Goal goal, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GoalContentProvider.key_title, goal.getTitle());
        contentValues.put(GoalContentProvider.key_content, goal.getContent());
        contentValues.put(GoalContentProvider.key_goalId, goal.getSelfId());
        contentValues.put(GoalContentProvider.key_state, goal.getState());
        contentValues.put(GoalContentProvider.key_userId, goal.getUserId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_goal, contentValues);

        if(myRowUri != null){
            return true;
        }
        return false;
    }

    public static boolean completed(int id, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GoalContentProvider.key_state, TodoHelper.PGS_State.get("complete"));

        String where = GoalContentProvider.key_id + " = " + id;
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int rid = cr.update(DBHelper.ContentUri_goal, contentValues, where, selectionArgs);
        if(rid > -1)
            return true;
        else
            return false;
    }

    public static boolean updateGoal(Goal goal, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GoalContentProvider.key_title, goal.getTitle());
        contentValues.put(GoalContentProvider.key_content, goal.getContent());
        contentValues.put(GoalContentProvider.key_goalId, goal.getSelfId());
        contentValues.put(GoalContentProvider.key_state, goal.getState());
        contentValues.put(GoalContentProvider.key_userId, goal.getUserId());

        String where = GoalContentProvider.key_id + " = " + goal.getId();
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int id = cr.update(DBHelper.ContentUri_goal, contentValues, where, selectionArgs);
        if(id > 0)
            return true;
        return false;
    }

    public static List<Goal> getGoals(Context context){
        ContentResolver cr = context.getContentResolver();

        String[] result_columns = new String[]{
                GoalContentProvider.keys[0],
                GoalContentProvider.keys[1],
                GoalContentProvider.keys[2],
                GoalContentProvider.keys[3],
                GoalContentProvider.keys[4],
                GoalContentProvider.keys[5]
        };

        String where = null;

        String whereArgs[] = null;
        String order = null;

        Cursor resultCursor = cr.query(DBHelper.ContentUri_goal,
                result_columns, where, whereArgs, order);

        List<Goal> result = null;
        if(resultCursor != null && resultCursor.getCount() > 0){
            result = new ArrayList<>();
            while(resultCursor.moveToNext()){
                Goal goal = new Goal(
                        resultCursor.getInt(0),
                        resultCursor.getString(1),
                        resultCursor.getString(2),
                        resultCursor.getString(3),
                        resultCursor.getString(4),
                        resultCursor.getString(5)
                );
                result.add(goal);
            }
        }
        return result;
    }
}
