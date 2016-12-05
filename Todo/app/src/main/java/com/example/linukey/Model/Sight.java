package com.example.linukey.Model;

import java.io.Serializable;

/**
 * Created by linukey on 12/3/16.
 */

public class Sight implements Serializable {
    private int id;
    private String title;
    private String content;
    private String sightId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Sight(int id, String title, String content, String sightId, String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.sightId = sightId;
        this.userId = userId;
    }

    public Sight(String title, String content, String sightId, String userId) {
        this.title = title;
        this.content = content;
        this.sightId = sightId;
        this.userId = userId;
    }

    public Sight(){}

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

    public String getSightId() {
        return sightId;
    }

    public void setSightId(String sightId) {
        this.sightId = sightId;
    }
}
