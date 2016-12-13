<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<HTML !DOCTYPE>
<head>
    <meta charset="utf-8">
    <title>Todo|多彩生活，简单记录...</title>
    <link rel="stylesheet" type="text/css" href="./css/setting_common.css"/>
    <link rel="stylesheet" type="text/css" href="./css/commom_introduce.css"/>
    <link rel="stylesheet" type="text/css" href="../css/person_info.css"/>
    <script src="./js/jquery-3.1.1.js"></script>
</head>
<body>
    <header>
		<s:include value="../Master/nav.jsp" />
    </header>
    <article id="article_container">
        <div id="container">
            <div id="left_container">
                <span class="user_info_" id="user_info_first">用户名：</span><span class="info_con">xinqilela</span><br>
                <span class="user_info_">手机号：</span><span class="info_con">13132256501</span><br>
                <span class="user_info_">邮箱账号：</span><span class="info_con">2581987520@qq.com</span><br>
                <span id="user_info_last"><a href="new_pwd.html">更改密码>></a></span>
            </div>
            <div id="right_container">
                <div id="user_img"></div>
                <button>上传头像</button>
            </div>
        </div>
    </article>
</body>
</HTML>