<%@page import="hr.fer.zemris.java.hw13.servlets.ServerUtilty"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    long startTime = (long) getServletContext().getAttribute("upTime");
    String time = ServerUtilty.getInterval(startTime);
%>

<html>
<body bgcolor="${sessionScope.pickedBgCol}">
	<h1>Servlet uptime:</h1>
	<p>
		Uptime:
		<%=time%></p>
    <p>
        <a href="/aplikacija2"><b>HOME</b></a>
    </p>
</body>
</html>