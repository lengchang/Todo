package com.example.linukey.Model;

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
}
