package com.linukey.todo.action;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.linukey.todo.entity.*;
import com.linukey.todo.util.HibernateSessionFactory;
import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport {
	public String loginAction(){
		return SUCCESS;
	}
}
