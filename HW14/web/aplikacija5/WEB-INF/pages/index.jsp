<%@page import="hr.fer.zemris.java.hw14.beans.Poll"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<html>
<body>
	<h1>Ankete</h1>
	<c:choose>
		<c:when test="${polls.isEmpty()}">
			<p>U bazi ne postoji niti jedna anketa.</p>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="entry" items="${polls}">
					<li>
						<p>
							<a href="glasanje?pollID=<c:out value="${entry.id}"></c:out>">${entry.title}</a>
						</p>
					</li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>
</body>
</html>