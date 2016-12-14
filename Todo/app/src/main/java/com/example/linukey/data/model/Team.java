package com.example.linukey.data.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.linukey.data.source.local.DBHelper;
import com.example.linukey.data.source.local.SelfTaskContentProvider;
import com.example.linukey.data.source.local.TeamContentProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/5/16.
 */

public class Team implements Serializable {
    private int id;
    private String teamname;
    private String content;
    private String leaderId;
    private String teamId;

    public Team(int id, String teamname, String content, String leaderId, String teamId) {
        this.id = id;
        this.teamname = teamname;
        this.content = content;
        this.leaderId = leaderId;
        this.teamId = teamId;
    }

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
        contentValues.put(TeamContentProvider.key_name, team.getTeamname());
        contentValues.put(TeamContentProvider.key_teamId, team.getTeamId());
        contentValues.put(TeamContentProvider.key_content, team.getContent());
        contentValues.put(TeamContentProvider.key_leaderId, team.getLeaderId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_team, contentValues);

        if(myRowUri != null)
            return true;

        return false;
    }

    public static boolean deleteOne(int id, Context context){
        return true;
    }

    public static boolean updateTeam(Team team, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamContentProvider.key_name, team.getTeamname());
        contentValues.put(TeamContentProvider.key_teamId, team.getTeamId());
        contentValues.put(TeamContentProvider.key_content, team.getContent());
        contentValues.put(TeamContentProvider.key_leaderId, team.getLeaderId());

        String where = TeamContentProvider.key_id + " = " + team.getId();
        String[] selectionArgs = null;

        ContentResolver cr = context.getContentResolver();
        int rid = cr.update(DBHelper.ContentUri_team, contentValues, where, selectionArgs);
        if(rid > -1)
            return true;
        else
            return false;
    }

    public static List<Team> getTeams(Context context) {
        ContentResolver cr = context.getContentResolver();

        String[] result_columns = new String[]{
                TeamContentProvider.keys[0],
                TeamContentProvider.keys[1],
                TeamContentProvider.keys[2],
                TeamContentProvider.keys[3],
                TeamContentProvider.keys[4]
        };

        String where = null;

        String whereArgs[] = null;
        String order = null;

        Cursor resultCursor = cr.query(DBHelper.ContentUri_team,
                result_columns, where, whereArgs, order);

        List<Team> result = null;

        if(resultCursor != null && resultCursor.getCount() > 0){
            result = new ArrayList<>();
            while(resultCursor.moveToNext()){
                Team team = new Team(
                        resultCursor.getInt(0),
                        resultCursor.getString(1),
                        resultCursor.getString(2),
                        resultCursor.getString(3),
                        resultCursor.getString(4)
                );
                result.add(team);
            }
            resultCursor.close();
            return result;
        }
        return result;
    }
}
