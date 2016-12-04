package com.example.linukey.Model;

/**
 * Created by linukey on 12/3/16.
 */

public class Goal {
    private int id;
    private String title;
    private String content;
    private String state;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Goal(int id, String title, String content, String state, String userId, String goalId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state;
        this.userId = userId;
        this.goalId = goalId;
    }

    public Goal(String title, String content, String state, String userId, String goalId) {
        this.title = title;
        this.content = content;
        this.state = state;
        this.userId = userId;
        this.goalId = goalId;
    }

    public Goal(){}

    private String userId;

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

    private String goalId;
}
