<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>扩展现有组件</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />    
    <link href="<%=basePath%>bootstrap/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/bootstrap.css"> 	
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/General.css">
    <script src="<%=basePath%>bootstrap/js/jquery.js"></script>
    <script src="<%=basePath%>bootstrap/js/bootstrap.js"></script>
    <script src="<%=basePath%>bootstrap/js/General.js"></script> 
    
    <script>
    	var showSecondLevel = function(data){
    		alert(data);
    	}
    </script>
    
    
    
</head>
<body>	
	<div class="p_header navbar navbar-lightblue navbar-fixed-top" role="navigation">
    	<div class="row">
        	<div class="col-md-3">
            	<div class="navbar-header">
                    <a class="navbar-brand" href="#">某某市突发预警平台</a>
                    <div class="navbar-text">asdasd</div>      
                </div>
            </div>
<%--             <div class="col-md-6">
            	<ul class="nav navbar-nav mynav">
                    <li><a href="#">
                    	<p><span class="hi1"></span></p>
                        <p>首页</p>
                    </a></li>
                    <li><a href="#">
                    	<p><span class="hi2"></span></p>
                        <p>预警发布</p>
                    </a></li>
                    <li><a href="#">
                    	<p><span class="hi3"></span></p>
                        <p>预警发布</p>
                    </a></li>
                    
                </ul>
            </div> --%>
            <div id="navMenu" class="col-md-6">
				<ul class="nav navbar-nav mynav">
					<c:forEach items="${firstMenuList }" var="firstMenu" varStatus="status">
						<li <c:if test="${firstMenu.popedomID == firstMenuID }">class="selected" </c:if>><a onclick="showSecondLevel('${firstMenu.popedomID }');" href="javascript:void(0);"><span>${firstMenu.name }</span></a></li>
					</c:forEach>
				</ul>
			</div>
            
            <div class="col-md-3">
            	<div class="con_exit pull-right">
                	欢迎您！<a href="#">退出</a>
                </div>
            </div>
        </div>   
    </div>
    <div class="clearfix"></div>
    <div class="page-container">
    	<div class="page-sidebar">
        	<ul class="nav nav-pills nav-stacked">
                <li>
                 	<a href="#"><i class="fa fa-adn"></i>Java <span class="arrow"></span></a>   
                    <ul>
                     	<li><a target="Conframe" href="gongsi.html">Swing</a></li>              
                     	<li><a target="Conframe" href="gongsi1.html">jMeter</a></li>
                     	<li><a target="Conframe" href="gongsi2.html">EJB</a></li>
                     	<li><a target="Conframe" href="gongsi3.html">分离的链接</a></li>
                    </ul>
               	</li>
            </ul>
        </div>
       	<div class="page-content">
        	<iframe name="Conframe" id="Conframe" src="<%=basePath%>jsp/mains.jsp" class="page-ifream" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%"></iframe>
        </div> 

    <div class="p_footer navbar navbar-lightblue navbar-fixed-bottom">
    	<div class="navbar-text">asdasd</div>
    </div>
    <div class="winens winopen"></div>
</body>
</html>