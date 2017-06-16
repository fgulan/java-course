<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
		<c:choose>
			<c:when test="${not empty sessionScope.get('current.user.id')}">
				<p class="loginText">
					${sessionScope.get('current.user.fn')}
					${sessionScope.get('current.user.ln')}, <a
						href="/aplikacija5/servleti/logout">Odjava</a>
				</p>
				<p class="loginText">
					<a href="/aplikacija5/servleti/main">Povratak na početnu</a>
				</p>
			</c:when>
			<c:otherwise>
				<p class="loginText">
					Niste prijavljeni, <a href="/aplikacija5/servleti/main">prijavite</a>
					se ovdje!
				</p>
			</c:otherwise>
		</c:choose>
</body>
</html>