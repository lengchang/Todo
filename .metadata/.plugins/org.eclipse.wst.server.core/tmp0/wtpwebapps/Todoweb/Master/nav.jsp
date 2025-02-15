<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link rel="stylesheet" type="text/css" href="./css/nav.css" />

<div id="top_logo">
	<a href="/Todoweb/index.jsp"> <img src="./img/logo.png">
	</a>
</div>
<nav id="top_nav">
	<ul>
		<a href="/Todoweb/index.jsp" id="index_firstpage"><li id="ul_index">首页</li></a>
		<a href="self_mode_use.html" id="self_mode"><li>个人模式</li></a>
		<a href="team_mode_use.html" id="team_mode"><li>团队模式</li></a>
		<a href="help.html" id="help"><li>帮助</li></a>
		<a href="download.html" id="download"><li>下载</li></a>
		<a href="/Todoweb/WEB-INF/root/admin.jsp"><li>管理后台</li></a>
	</ul>
</nav>
<div id="top_button">
	<s:if test="#session['username']!=null">
		<a href="/Todoweb/acessUserinfo.action">${session.username}</a>
		<a href="/Todoweb/logout.action">logout</a>
	</s:if>
	<s:if test="#session['username']==null">
		<a href="/Todoweb/login.jsp"><button id="button_login">登录到网页版</button></a>
		<a href="/Todoweb/register.jsp"><button id="button_regist">注册</button></a>
	</s:if>
</div>