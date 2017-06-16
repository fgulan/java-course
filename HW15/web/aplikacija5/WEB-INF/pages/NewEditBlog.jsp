<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>
<title>Blog</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="Header.jsp" />
	<c:choose>
		<c:when test="${action == \"new\" }">
			<h1>Stvaranje novog bloga</h1>
		</c:when>
		<c:otherwise>
			<h1>Izmjena bloga</h1>
		</c:otherwise>
	</c:choose>
	<form action="/aplikacija5/servleti/author/${author}/${action}"
		method="post">
		<table>
			<tr>
				<td class="firstColumn">ID</td>
				<td class="secondColumn">${form.id}<br>
				</td>
				<c:if test="${action == \"edit\"}">
					<input type="hidden" name="id"
						value='<c:out value="${form.id}"></c:out>'>
				</c:if>
			</tr>
			<tr>
				<td class="firstColumn">Naslov</td>
				<td class="secondColumn"><input type="text" name="title"
					value='<c:out value="${form.title}"></c:out>' size="67"><br>
					<c:if test="${form.hasError('title')}">
						<div class="greska">
							<c:out value="${form.getError('title')}"></c:out>
						</div>
					</c:if></td>
			</tr>
			<tr>
				<td class="firstColumn">Tekst</td>
				<td class="secondColumn"><textarea name="text" rows="10"
						cols="50">${form.text}</textarea></td>
			</tr>
			<tr>
				<td class="firstColumn"></td>
				<td class="secondColumn"><input type="submit"
					name="newBlogAction" value="Spremi"> <input type="submit"
					name="newBlogAction" value="Odustani"></td>
			</tr>
		</table>
	</form>
</body>

</html>