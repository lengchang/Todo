package com.example.linukey.Model;

import java.io.Serializable;

/**
 * Created by linukey on 12/3/16.
 */

public class Project extends TaskClassify {
    private String state;

    public Project(int id, String title, String content, String state, String projectId, String userId) {
        this.state = state;
        setId(id);
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(projectId);
    }

    public Project(String title, String content, String state, String projectId, String userId) {
        this.state = state;
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(projectId);
    }

    public Project(){}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
