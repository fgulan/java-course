<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body bgcolor="${sessionScope.pickedBgCol}">
	<a href="setcolor?pickedBgCol=white">WHITE</a>
	<a href="setcolor?pickedBgCol=red">RED</a>
	<a href="setcolor?pickedBgCol=green">GREEN</a>
	<a href="setcolor?pickedBgCol=cyan">CYAN</a>
	<p>
		<a href="/aplikacija2"><b>HOME</b></a>
	</p>
</body>
</html>