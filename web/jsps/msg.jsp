<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'msg.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css">
      * {
          font-family: "Courier New";
          text-align: center;
      }
    </style>
  </head>
  
  <body>
    <h1>${requestScope.msg }</h1>
    <ul >
      <li><a href="<c:url value='/index.jsp'/>">主页</a></li>
      <li><a href="<c:url value='/jsps/user/login.jsp'/>">登陆</a></li>
      <li><a href="<c:url value='/jsps/user/regist.jsp'/>">注册</a></li>
    </ul>
  </body>
</html>
