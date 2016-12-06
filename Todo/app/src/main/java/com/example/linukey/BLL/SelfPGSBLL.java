package com.example.linukey.BLL;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.linukey.DAL.DBHelper;
import com.example.linukey.DAL.GoalContentProvider;
import com.example.linukey.DAL.ProjectContentProvider;
import com.example.linukey.DAL.SelfTaskContentProvider;
import com.example.linukey.DAL.SightContentProvider;
import com.example.linukey.Model.Goal;
import com.example.linukey.Model.Project;
import com.example.linukey.Model.Sight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/3/16.
 */

public class SelfPGSBLL {
    public boolean saveProject(Project project, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProjectContentProvider.key_title, project.getTitle());
        contentValues.put(ProjectContentProvider.key_content, project.getContent());
        contentValues.put(ProjectContentProvider.key_state, project.getState());
        contentValues.put(ProjectContentProvider.key_projectId, project.getProjectId());
        contentValues.put(ProjectContentProvider.key_userId, project.getUserId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_project, contentValues);

        if(myRowUri != null){
            return true;
        }
        return false;
    }

    public boolean updateProject(Project project, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProjectContentProvider.key_title, project.getTitle());
        contentValues.put(ProjectContentProvider.key_content, project.getContent());
        contentValues.put(ProjectContentProvider.key_state, project.getState());
        contentValues.put(ProjectContentProvider.key_projectId, project.getProjectId());
        contentValues.put(ProjectContentProvider.key_userId, project.getUserId());

        String where = ProjectContentProvider.key_id + " = " + project.getId();
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int id = cr.update(DBHelper.ContentUri_project, contentValues, where, selectionArgs);

        if(id>0)
            return true;

        return false;
    }

    public boolean saveGoal(Goal goal, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GoalContentProvider.key_title, goal.getTitle());
        contentValues.put(GoalContentProvider.key_content, goal.getContent());
        contentValues.put(GoalContentProvider.key_goalId, goal.getGoalId());
        contentValues.put(GoalContentProvider.key_state, goal.getState());
        contentValues.put(GoalContentProvider.key_userId, goal.getUserId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_goal, contentValues);

        if(myRowUri != null){
            return true;
        }
        return false;
    }

    public boolean updateGoal(Goal goal, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GoalContentProvider.key_title, goal.getTitle());
        contentValues.put(GoalContentProvider.key_content, goal.getContent());
        contentValues.put(GoalContentProvider.key_goalId, goal.getGoalId());
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

    public boolean saveSight(Sight sight, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SightContentProvider.key_title, sight.getTitle());
        contentValues.put(SightContentProvider.key_content, sight.getContent());
        contentValues.put(SightContentProvider.key_sightId, sight.getSightId());
        contentValues.put(SightContentProvider.key_userId, sight.getUserId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_sight, contentValues);

        if(myRowUri != null){
            return true;
        }
        return false;
    }

    public boolean updateSight(Sight sight, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SightContentProvider.key_title, sight.getTitle());
        contentValues.put(SightContentProvider.key_content, sight.getContent());
        contentValues.put(SightContentProvider.key_sightId, sight.getSightId());
        contentValues.put(SightContentProvider.key_userId, sight.getUserId());

        String where = SightContentProvider.key_id + " = " + sight.getId();
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int id = cr.update(DBHelper.ContentUri_sight, contentValues, where, selectionArgs);
        if(id > 0)
            return true;
        return false;
    }

    public List<Project> getProjects(Context context, String userId){
        ContentResolver cr = context.getContentResolver();

        String[] result_columns = new String[]{
                ProjectContentProvider.keys[0],
                ProjectContentProvider.keys[1],
                ProjectContentProvider.keys[2],
                ProjectContentProvider.keys[3],
                ProjectContentProvider.keys[4],
                ProjectContentProvider.keys[5]
        };

        String where = ProjectContentProvider.key_userId + " = " + userId;

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

    public List<Goal> getGoals(Context context, String userId){
        ContentResolver cr = context.getContentResolver();

        String[] result_columns = new String[]{
                GoalContentProvider.keys[0],
                GoalContentProvider.keys[1],
                GoalContentProvider.keys[2],
                GoalContentProvider.keys[3],
                GoalContentProvider.keys[4],
                GoalContentProvider.keys[5]
        };

        String where = GoalContentProvider.key_userId + " = " + userId;

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

    public List<Sight> getSights(Context context, String userId){
        ContentResolver cr = context.getContentResolver();

        String[] result_columns = new String[]{
                SightContentProvider.keys[0],
                SightContentProvider.keys[1],
                SightContentProvider.keys[2],
                SightContentProvider.keys[3],
                SightContentProvider.keys[4]
        };

        String where = SightContentProvider.key_userId + " = " + userId;

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
