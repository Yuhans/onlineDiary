<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tatyana
  Date: 16.11.2016
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Marks</title>
</head>
<body>
<table border="2">
    <tr>
        <td>Subject</td>
        <c:forEach items="${days}" var="day">
            <td>${day}</td>
        </c:forEach>
    </tr>
    <tr>
        <td>${subjectName}</td>
    <c:forEach items="${marks}" var="mark" varStatus="status">
            <td>${mark}</td>
    </c:forEach>
    </tr>
    <%--<td><input type="submit" value="Login" name="Login"/></td>--%>
    <%-- <td><input type="submit" name="getList" value="Обновить"/></td>--%>
</table>
</body>
</html>
