<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style>
.aaaa{ width:200px; height:700px; overflow:hidden; margin:0 auto; padding:0 auto;}
.aaaa li{ width:200px; height:50px; background: #9F3; margin-bottom:5px; text-decoration:none; text-align:center;}
.aaaa li:hover{ width:200px; height:50px; margin-right:10px;background:#33C; }
</style>
</head>
<body>
<ul class="aaaa">
<li><a href="#">aaaaa</a></li>
<li><a href="#">aaaaa</a></li>
<li><a href="#">aaaaa</a></li>
<li><a href="#">aaaaa</a></li>
</ul>
</body>
</html>


