<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>


<html>
<head>
<title>Login</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="Header.jsp" />

	<c:if test="${empty sessionScope.get('current.user.id')}">
		<h2>Prijava:</h2>
		<form action="/aplikacija5/servleti/main" method="post">
			<table>
				<tr>
					<td class="firstColumn">Nadimak</td>
					<td class="secondColumn"><input type="text" name="nick"
						<c:choose>
                            <c:when test="${sessionScope.get('error')}">
                                value=${sessionScope.get('current.user.nick')}
                            </c:when>
                            <c:otherwise>
                                value=""
                             </c:otherwise>
                        </c:choose>
						size="15"></td>
				</tr>
				<tr>
					<td class="firstColumn">Zaporka</td>
					<td class="secondColumn"><input type="password"
						name="password" value="" size="15"></td>
				</tr>
				<tr>
					<td class="firstColumn"></td>
					<td class="greska"><c:if test="${sessionScope.get('error')}">
                    Neipsravni podaci! Molimo vas provjerite ponovno vaše podatke.
                </c:if></td>
				</tr>
				<tr>
					<td><input type="submit" name="action" value="Prijava"></td>
				</tr>
			</table>
		</form>
	</c:if>

	<p>
		<a href="/aplikacija5/servleti/register">Novi korisnik</a>
	</p>
	<p>Registrirani korisnici:</p>
	<table>
		<tr>
			<td>Nadimak</td>
			<td>Ime</td>
			<td>Prezime</td>
		</tr>
		<c:forEach var="user" items="${users}">
			<tr>
				<td><a href="/aplikacija5/servleti/author/${user.nick}">${user.nick}</a></td>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>