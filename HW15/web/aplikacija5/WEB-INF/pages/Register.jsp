<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<title>Register</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
	<jsp:include page="Header.jsp" />

	<h1>Register</h1>
	<form action="/aplikacija5/servleti/register" method="post">
		<table>
			<tr>
				<td class="firstColumn">Ime</td>
				<td class="secondColumn"><input type="text" name="firstName"
					value='<c:out value="${form.firstName}"></c:out>' size="50"><br>
					<c:if test="${form.hasError('firstName')}">
						<div class="greska">
							<c:out value="${form.getError('firstName')}"></c:out>
						</div>
					</c:if></td>
			</tr>
			<tr>
				<td class="firstColumn">Prezime</td>
				<td class="secondColumn"><input type="text" name="lastName"
					value='<c:out value="${form.lastName}"></c:out>' size="50"><br>
					<c:if test="${form.hasError('lastName')}">
						<div class="greska">
							<c:out value="${form.getError('lastName')}"></c:out>
						</div>
					</c:if></td>
			</tr>
			<tr>
				<td class="firstColumn">E-Mail</td>
				<td class="secondColumn"><input type="text" name="email"
					value='<c:out value="${form.email}"></c:out>' size="50"><br>
					<c:if test="${form.hasError('email')}">
						<div class="greska">
							<c:out value="${form.getError('email')}"></c:out>
						</div>
					</c:if></td>
			</tr>
			<tr>
				<td class="firstColumn">Nadimak</td>
				<td class="secondColumn"><input type="text" name="nick"
					value='<c:out value="${form.nick}"></c:out>' size="50"><br>
					<c:if test="${form.hasError('nick')}">
						<div class="greska">
							<c:out value="${form.getError('nick')}"></c:out>
						</div>
					</c:if></td>
			</tr>
			<tr>
				<td class="firstColumn">Lozinka</td>
				<td class="secondColumn"><input type="password" name="password"
					value='<c:out value="${form.password}"></c:out>' size="50"><br>
					<c:if test="${form.hasError('password')}">
						<div class="greska">
							<c:out value="${form.getError('password')}"></c:out>
						</div>
					</c:if></td>
			</tr>
			<tr>
				<td class="firstColumn"></td>
				<td class="secondColumn"><input type="submit" name="action"
					value="Pohrani"> <input type="submit" name="action"
					value="Odustani"></td>
			</tr>
		</table>
	</form>

</body>
</html>