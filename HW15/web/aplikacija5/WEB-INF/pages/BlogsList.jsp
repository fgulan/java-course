<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<%
    Boolean user = Boolean.valueOf(request.getParameter("logStatus"));
%>

<html>
<head>
<title>Lista blogova</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="Header.jsp" />

	<h1>Blogovi korisnika ${author}:</h1>
	<c:if test="${entries.isEmpty()}">
		<p>Korisnik još nije stvorio blog.</p>
	</c:if>
	<c:forEach var="entry" items="${entries}">
		<p>
			<a href="/aplikacija5/servleti/author/${author}/${entry.id}">${entry.title}</a>
		</p>
	</c:forEach>
</body>
<c:if test="${sessionScope.get('current.user.nick') == author}">
	<p>
		<a href="/aplikacija5/servleti/author/${author}/new">Stvori blog</a>
	</p>
</c:if>
</html>