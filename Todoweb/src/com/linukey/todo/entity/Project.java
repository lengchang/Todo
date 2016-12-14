package com.linukey.todo.entity;

public class Project extends TaskClassify {
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String state;
    private String type;

    public Project(int id, String title, String content, String state, String projectId,
                   String userId, String type) {
        this.state = state;
        setId(id);
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(projectId);
        this.type = type;
    }

    public Project(String title, String content, String state, String projectId,
                   String userId, String type) {
        this.state = state;
        this.type = type;
        setTitle(title);
        setContent(content);
        setUserId(userId);
        setSelfId(projectId);
    }

    public Project(){ }
}
