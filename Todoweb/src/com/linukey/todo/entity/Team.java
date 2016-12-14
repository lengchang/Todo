package com.linukey.todo.entity;

public class Team {
	private int id;
	private String teamname;
	private String content;
	private String leaderId;
	private String teamId;

	public Team(int id, String teamname, String content, String leaderId, String teamId) {
		this.id = id;
		this.teamname = teamname;
		this.content = content;
		this.leaderId = leaderId;
		this.teamId = teamId;
	}
	
	public Team(String teamname, String content, String leaderId, String teamId) {
		this.teamname = teamname;
		this.content = content;
		this.leaderId = leaderId;
		this.teamId = teamId;
	}
	
	public Team(){}

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
