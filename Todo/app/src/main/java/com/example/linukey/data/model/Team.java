package com.example.linukey.data.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.linukey.data.source.local.DBHelper;

import java.io.Serializable;

/**
 * Created by linukey on 12/5/16.
 */

public class Team implements Serializable {
    private int id;
    private String teamname;
    private String content;
    private String leaderId;
    private String teamId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public static boolean saveTeam(Team team, Context context){
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
