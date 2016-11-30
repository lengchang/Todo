package com.example.linukey.BLL;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.linukey.DAL.DBHelper;
import com.example.linukey.DAL.SelfTaskContentProvider;
import com.example.linukey.Model.SelfTask;

/**
 * Created by linukey on 12/1/16.
 */

public class AddSelfBLL {

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

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_selftask, newValues);

        if(myRowUri != null)
            return true;

        return false;
    }
}
