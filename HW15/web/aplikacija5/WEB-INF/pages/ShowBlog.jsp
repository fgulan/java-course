<%@page import="hr.fer.zemris.java.tecaj_15.model.BlogEntry"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>
<title>Novi blog</title>
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="Header.jsp" />
	<p class="loginText">
		Povratak na pregled <a href="/aplikacija5/servleti/author/${author}">blogova</a>
	</p>

	<h1>Prikaz bloga</h1>

	<table>
		<tr>
			<td class="firstColumn">ID:</td>
			<td class="secondColumn">${entry.id}<br>
			</td>
		</tr>
		<tr>
			<td class="firstColumn">Naslov:</td>
			<td class="secondColumn"><c:out value="${entry.title}"></c:out><br>
			</td>
		</tr>
		<tr>
			<td class="firstColumn">Tekst:</td>
			<td class="secondColumn">${entry.text}</td>
		</tr>
		<tr>
			<td class="firstColumn">Stvoreno:</td>
			<td class="secondColumn">${entry.createdAt}</td>
		</tr>
		<tr>
			<td class="firstColumn">Zadnja promjena:</td>
			<td class="secondColumn">${entry.lastModifiedAt}</td>
		</tr>
	</table>
	<p>
		<c:if
			test="${sessionScope.get('current.user.nick') == author}">
			<p>
				<a href="/aplikacija5/servleti/author/${author}/edit">Uredi</a>
			</p>
		</c:if>
	</p>

	<h2>Komentari:</h2>
	<ul>
		<c:forEach var="comment" items="${entry.comments}">
			<li><div style="font-weight: bold">[Korisnik=${comment.usersEMail}]
					${comment.postedOn}</div>
				<div style="padding-left: 10px;">${comment.message}</div></li>
		</c:forEach>
	</ul>

	<form action="/aplikacija5/servleti/author/${author}/${entry.id}"
		method="post">
		<input type="hidden" name="blogID"
			value='<c:out value="${entry.id}"></c:out>'>
		<table>
			<tr>
				<td class="firstColumn">Komentar:</td>
				<td class="secondColumn"><textarea name="comment" rows="8"
						cols="50"></textarea></td>
			</tr>
			<tr>
				<td class="firstColumn"></td>
				<td class="secondColumn"><input type="submit"
					name="commentAction" value="Komentiraj"></td>
			</tr>
		</table>
	</form>
</body>
</html>