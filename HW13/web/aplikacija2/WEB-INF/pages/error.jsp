<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body bgcolor="${sessionScope.pickedBgCol}">
	<p>Invalid parameters:</p>
	<p>a € [-100, 100]</p>
	<p>b € [-100, 100]</p>
	<p>n € [1, 5]</p>
	<p>
		<a href="/aplikacija2"><b>HOME</b></a>
	</p>
</body>
</html>