package com.example.linukey.Model;

import java.io.Serializable;

/**
 * Created by linukey on 12/3/16.
 */

public class Sight extends TaskClassify {
    public Sight(int id, String title, String content, String sightId, String userId) {
        setId(id);
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(sightId);
    }

    public Sight(String title, String content, String sightId, String userId) {
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(sightId);
    }

    public Sight(){}

    public void setId(int id) {
        this.id = id;
    }
}
