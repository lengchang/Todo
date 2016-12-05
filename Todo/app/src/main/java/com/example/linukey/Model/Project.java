package com.example.linukey.Model;

import java.io.Serializable;

/**
 * Created by linukey on 12/3/16.
 */

public class Project implements Serializable {
    private int id;
    private String title;
    private String content;
    private String state;
    private String projectId;

    public Project(int id, String title, String content, String state, String projectId, String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state;
        this.projectId = projectId;
        this.userId = userId;
    }

    public Project(String title, String content, String state, String projectId, String userId) {
        this.title = title;
        this.content = content;
        this.state = state;
        this.projectId = projectId;
        this.userId = userId;
    }

    public Project(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
