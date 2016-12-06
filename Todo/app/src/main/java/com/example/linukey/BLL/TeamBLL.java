package com.example.linukey.BLL;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.linukey.DAL.DBHelper;
import com.example.linukey.Model.Team;

/**
 * Created by linukey on 12/5/16.
 */

public class TeamBLL {
    public boolean saveTeam(Team team, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put("teamname", team.getTeamname());
        contentValues.put("teamId", team.getTeamId());
        contentValues.put("content", team.getContent());
        contentValues.put("leaderId", team.getLeaderId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_user, contentValues);

        if(myRowUri != null)
            return true;

        return false;
    }
}
