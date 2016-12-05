package com.example.linukey.Model;

import java.io.Serializable;

/**
 * Created by linukey on 12/3/16.
 */

public class Goal implements Serializable {
    private int id;
    private String title;
    private String content;
    private String state;
    private String userId;
    private String goalId;

    public Goal(int id, String title, String content, String state, String goalId, String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state;
        this.userId = userId;
        this.goalId = goalId;
    }

    public Goal(String title, String content, String state, String goalId, String userId) {
        this.title = title;
        this.content = content;
        this.state = state;
        this.userId = userId;
        this.goalId = goalId;
    }

    public Goal(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }
}
