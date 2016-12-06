package com.example.linukey.Model;

import java.io.Serializable;

/**
 * Created by linukey on 12/3/16.
 */

public class Goal extends TaskClassify {
    private String state;


    public Goal(int id, String title, String content, String state, String goalId, String userId) {
        setId(id);
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(goalId);
        this.state = state;
    }

    public Goal(String title, String content, String state, String goalId, String userId) {
        this.state = state;
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(goalId);
    }

    public Goal(){}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
