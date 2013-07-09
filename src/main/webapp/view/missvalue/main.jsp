<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>刷新遗漏</title>
</head>
<frameset rows="20%,*">
	<frame src="<%=path%>/view/missvalue/top.jsp" noresize="noresize"
		name="topFrame" scrolling="no" marginwidth="0"
		marginheight="0"/>
<frameset cols="20%,80%">
	<frame src="<%=path%>/view/missvalue/left.jsp" name="leftFrame" noresize="noresize"
		marginwidth="0" marginheight="0" scrolling="no"
		target="main" />
	<frame src="<%=path%>/view/missvalue/right.jsp" name="main" marginwidth="0"
		marginheight="0" scrolling="auto" target="_self" />
</frameset>
</frameset>
</html>