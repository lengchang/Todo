package com.example.linukey.data.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.linukey.data.source.local.DBHelper;
import com.example.linukey.data.source.local.TeamTaskContentProvider;

import java.io.Serializable;

/**
 * Created by linukey on 12/5/16.
 */

public class TeamTask implements Serializable {
    private int id;
    private String title;
    private String content;
    private String starttime;
    private String endtime;
    private String clocktime;
    private String projectId;
    private String state;
    private String isdelete;
    private String teamId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getClocktime() {
        return clocktime;
    }

    public void setClocktime(String clocktime) {
        this.clocktime = clocktime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public static boolean saveTeamTask(TeamTask teamTask, Context context){
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
