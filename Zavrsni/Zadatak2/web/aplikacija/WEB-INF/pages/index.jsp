<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h1>Krugovi</h1>
	
	
	<A HREF="klik">
<IMG SRC="slika" WIDTH=500 HEIGHT=500 ALT="Slika"
   BORDER=0 ISMAP></A>
	<table border="1">
		<thead>
			<tr>
				<th>Indeks</th>
				<th>Definicija kruga</th>
				<th>Naredbe</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${krugovi}" varStatus="loop">
				<tr>
					<td>${loop.index}</td>
					<td>${data.opis }</td>
					<td><a href="/aplikacija/obrisi?index=${loop.index}"><b>Obrisi
								ovaj krug</b></a> | <a href="/aplikacija/sel?index=${loop.index}"><b>Selektiraj
								ovaj krug</b></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form action="naredba" method="GET">
		Naredva : <input type="text" name="naredba"> <br /> <input
			type="submit" value="Izvrši" />
			<c:if test="${not empty sessionScope.greska}">
						<font size="3" color="red"><c:out  value="${sessionScope.greska}"></c:out></font>
							
						
					</c:if></td>
	</form>
</body>
</html>