package com.example.linukey.data.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.linukey.data.source.local.DBHelper;
import com.example.linukey.data.source.local.ProjectContentProvider;
import com.example.linukey.data.source.local.SelfTaskContentProvider;
import com.example.linukey.util.TodoHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/3/16.
 */

public class Project extends TaskClassify {
    private String state;

    public Project(int id, String title, String content, String state, String projectId, String userId) {
        this.state = state;
        setId(id);
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(projectId);
    }

    public Project(String title, String content, String state, String projectId, String userId) {
        this.state = state;
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(projectId);
    }

    public Project(){}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static boolean saveProject(Project project, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProjectContentProvider.key_title, project.getTitle());
        contentValues.put(ProjectContentProvider.key_content, project.getContent());
        contentValues.put(ProjectContentProvider.key_state, project.getState());
        contentValues.put(ProjectContentProvider.key_projectId, project.getSelfId());
        contentValues.put(ProjectContentProvider.key_userId, project.getUserId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_project, contentValues);

        if(myRowUri != null){
            return true;
        }
        return false;
    }

    public static boolean updateProject(Project project, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProjectContentProvider.key_title, project.getTitle());
        contentValues.put(ProjectContentProvider.key_content, project.getContent());
        contentValues.put(ProjectContentProvider.key_state, project.getState());
        contentValues.put(ProjectContentProvider.key_projectId, project.getSelfId());
        contentValues.put(ProjectContentProvider.key_userId, project.getUserId());

        String where = ProjectContentProvider.key_id + " = " + project.getId();
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int id = cr.update(DBHelper.ContentUri_project, contentValues, where, selectionArgs);

        if(id>0)
            return true;

        return false;
    }

    public static boolean deleteOne(int id, Context context){
        ContentResolver cr = context.getContentResolver();
        String where = ProjectContentProvider.key_id + " = " + id;
        String[] selectionArgs = null;
        int rid = cr.delete(DBHelper.ContentUri_project, where, selectionArgs);
        if(rid > -1)
            return true;
        return false;
    }

    public static boolean completed(int id, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProjectContentProvider.key_state, TodoHelper.PGS_State.get("complete"));

        String where = ProjectContentProvider.key_id + " = " + id;
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int rid = cr.update(DBHelper.ContentUri_project, contentValues, where, selectionArgs);
        if(rid > -1)
            return true;
        else
            return false;
    }

    public static List<Project> getProjects(Context context){
        ContentResolver cr = context.getContentResolver();

        String[] result_columns = new String[]{
                ProjectContentProvider.keys[0],
                ProjectContentProvider.keys[1],
                ProjectContentProvider.keys[2],
                ProjectContentProvider.keys[3],
                ProjectContentProvider.keys[4],
                ProjectContentProvider.keys[5]
        };

        String where = null;

        String whereArgs[] = null;
        String order = null;

        Cursor resultCursor = cr.query(DBHelper.ContentUri_project,
                result_columns, where, whereArgs, order);

        List<Project> result = null;
        if(resultCursor != null && resultCursor.getCount() > 0){
            result = new ArrayList<>();
            while(resultCursor.moveToNext()){
                Project project = new Project(
                        resultCursor.getInt(0),
                        resultCursor.getString(1),
                        resultCursor.getString(2),
                        resultCursor.getString(3),
                        resultCursor.getString(4),
                        resultCursor.getString(5)
                );
                result.add(project);
            }
        }

        return result;
    }
}
