<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body bgcolor="${sessionScope.pickedBgCol}">
	<h1>Results</h1>
	<table border="1">
		<thead>
			<tr>
				<th>Angle</th>
				<th>Sin value</th>
				<th>Cos value</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${results}">
				<tr>
					<td>${data.value }</td>
					<td>${data.sinValue }</td>
					<td>${data.cosValue }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <p>
        <a href="/aplikacija2"><b>HOME</b></a>
    </p>
</body>
</html>