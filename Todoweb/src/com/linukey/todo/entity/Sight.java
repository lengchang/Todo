package com.linukey.todo.entity;

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

	public Sight() {}
	
}
