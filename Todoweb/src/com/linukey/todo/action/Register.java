package com.linukey.todo.action;

import com.linukey.todo.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

public class Register extends ActionSupport {
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	String username;
	String password;
	String repassword;
	String email;
	String phonenumber;
	
	public String registAction(){
		if(!new UserDao().saveUser(username, password, email, repassword, phonenumber)){
			addFieldError("user", "用户名已存在!");
			return INPUT;
		}
		return SUCCESS;
	}
	
	@Override
	public void validate(){
		if(username.trim().isEmpty())
			addFieldError("username", "用户名不能为空!");
		else if(password.trim().isEmpty())
			addFieldError("password", "密码不能为空!");
		else if(repassword.trim().isEmpty())
			addFieldError("repassword", "请再次输入密码!");
		else if(!repassword.equals(password))
			addFieldError("password", "两次输入密码不一致!");
		else if(email.trim().isEmpty())
			addFieldError("email", "请输入邮箱!");
		else if(phonenumber.trim().isEmpty())
			addFieldError("phonenumber", "请输入手机号码!");
	}
}
