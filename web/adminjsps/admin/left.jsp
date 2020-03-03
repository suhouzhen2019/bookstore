<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">

	<script language="javascript">
		//设置大标题，第一个参数必须和变量名相同
		var bar1 = new Q6MenuBar("bar1", "图书商城管理");
		function load() {
			//设置配色方案，共 0 1 2 3 四种
			bar1.colorStyle = 3;
			//指定图片目录
			bar1.config.imgDir = "<c:url value='/menu/img/'/>";
			//菜单之间是否相互排斥，打开一个新的，其他的就会关闭
			bar1.config.radioButton = false;
			//分类管理 菜单项名称，子菜单名称，点击后请求的位置，内容显示的位置
			bar1.add("分类管理", "查看分类", "<c:url value='/Admin/AdminCategoryServlet?method=findAll'/>", "body");
			bar1.add("分类管理", "添加分类", "<c:url value='/adminjsps/admin/category/add.jsp'/>", "body");

			bar1.add("图书管理", "查看图书", "<c:url value='/Admin/AdminBookServlet?method=findAll'/>", "body");
			bar1.add("图书管理", "添加图书", "<c:url value='/Admin/AdminBookServlet?method=addPre'/>", "body");

			bar1.add("订单管理", "所有订单", "<c:url value='/adminjsps/admin/order/list.jsp'/>", "body");
			bar1.add("订单管理", "未付款订单", "<c:url value='/adminjsps/admin/order/list.jsp'/>", "body");
			bar1.add("订单管理", "已付款订单", "<c:url value='/adminjsps/admin/order/list.jsp'/>", "body");
			bar1.add("订单管理", "未收货订单", "<c:url value='/adminjsps/admin/order/list.jsp'/>", "body");
			bar1.add("订单管理", "已完成订单", "<c:url value='/adminjsps/admin/order/list.jsp'/>", "body");

			//获取 div 元素
			var d = document.getElementById("menu");
			//把生成内容转换为 HTML，赋值给 menu
			d.innerHTML = bar1.toString();
		}
	</script>

</head>

<body onload="load()" style="margin: 0px; background: rgb(254,238,189);">
<div id="menu"></div>

</body>
</html>
