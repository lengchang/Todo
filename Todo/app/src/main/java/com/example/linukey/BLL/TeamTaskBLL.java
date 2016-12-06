package com.example.linukey.BLL;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.linukey.DAL.DBHelper;
import com.example.linukey.DAL.TeamTaskContentProvider;
import com.example.linukey.Model.TeamTask;

/**
 * Created by linukey on 12/5/16.
 */

public class TeamTaskBLL {
    public boolean saveTeamTask(TeamTask teamTask, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamTaskContentProvider.key_title, teamTask.getTitle());
        contentValues.put(TeamTaskContentProvider.key_content, teamTask.getContent());
        contentValues.put(TeamTaskContentProvider.key_starttime, teamTask.getStarttime());
        contentValues.put(TeamTaskContentProvider.key_endtime, teamTask.getEndtime());
        contentValues.put(TeamTaskContentProvider.key_clocktime, teamTask.getClocktime());
        contentValues.put(TeamTaskContentProvider.key_state, teamTask.getState());
        contentValues.put(TeamTaskContentProvider.key_isdelete, teamTask.getIsdelete());
        contentValues.put(TeamTaskContentProvider.key_projectId, teamTask.getProjectId());
        contentValues.put(TeamTaskContentProvider.key_teamId, teamTask.getTeamId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_teamtask, contentValues);

        if(myRowUri != null)
            return true;

        return false;
    }
}