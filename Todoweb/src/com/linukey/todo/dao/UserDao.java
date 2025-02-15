package com.linukey.todo.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.linukey.todo.entity.User;
import com.linukey.todo.util.HibernateSessionFactory;
import com.linukey.todo.util.TodoHelper;
import com.opensymphony.xwork2.ActionContext;

public class UserDao extends CommDao<User> {
	
	/**
	 * 检查用户名是否已经存在
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkinput(String username, String password){
			List<User> users = selectAll(User.class);
			for(User user : users){
				if(user.getUsername().equals(username) && 
						user.getPassword().equals(password))
					return true;
			}
		return false;
	}
	
	/**
	 * 保存用户注册信息
	 * @param username
	 * @param password
	 * @param email
	 * @param repassword
	 * @param phonenumber
	 * @return
	 */
	public boolean saveUser(String username, String password, String email, 
			String repassword, String phonenumber){
		List<User> users = selectAll(User.class);
		for(User user : users){
			if(user.getUsername().equals(username))
				return false;
		}
		String userId = UUID.randomUUID().toString();
		String usergroup = TodoHelper.UserGroup.get("normal");
		User user = new User(username, password, email, phonenumber, usergroup, userId);
		if(saveOne(user) == 1)
			return true;
		return false;
	}
	
	/**
	 * 保存用户登录信息
	 * @param username
	 * @return
	 */
	public boolean saveLoginInfo(String username){
		ActionContext.getContext().getSession().put("username", username);
		return true;
	}
	
	/**
	 * 用户登出
	 * @return
	 */
	public boolean logout(){
		ActionContext.getContext().getSession().put("username", null);
		return true;
	}
}




















