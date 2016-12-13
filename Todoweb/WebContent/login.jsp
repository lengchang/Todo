<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<HTML !DOCTYPE>
<head>
<meta charset="utf-8">
<title>Todo|登录</title>
<link rel="stylesheet" type="text/css" href="./css/login.css" />
</head>
<body>
	<header>
		<s:include value="./Master/nav.jsp" />
	</header>
	<article>
		<div id="login_container">
			<form action="login" method="post">
				<s:fielderror />
				<input type="text" id="user_name" name="username" placeholder="用户名/邮箱" required><br />
				<input type="password" id="user_pass" name="password" placeholder="密码" required ><br />
				<div id="about_pass">
					<span id="remember_pass_span"><input type="checkbox">记住我</span>
					<a href="findpwd.html"><span id="forget_pass_span">忘记密码？</span></a><br/>
					<input type="radio" name="usergroup" value="普通用户" checked />普通用户
					<input type="radio" name="usergroup" value="管理员" />管理员
				</div>
				<input type="submit" value="登录" id="login_button"><br />
			</form>
			<a href="./register.jsp" id="gotoregist">尚无账号？免费注册</a>
		</div>
	</article>
	<footer>
		<s:include value="./Master/footer.jsp" />
	</footer>
</body>
</HTML>