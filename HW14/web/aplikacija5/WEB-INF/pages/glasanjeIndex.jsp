<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="true"%>

<html>
<body>
	<h1>${poll.title}</h1>
	<p>${poll.description}</p>
	<ol>
		<c:forEach var="entry" items="${options}">
			<li><a href="glasanje-glasaj?id=${entry.id}">${entry.name}</a></li>
		</c:forEach>
	</ol>
	<p>
		<a href="index.html">Home</a>
	</p>
</body>
</html>