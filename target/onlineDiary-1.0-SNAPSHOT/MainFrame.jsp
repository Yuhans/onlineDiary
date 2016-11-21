<%--
  Created by IntelliJ IDEA.
  User: Рамиль
  Date: 04.11.2016
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Main Frame</title>
</head>

<body>
<form action="<c:url value="/main"/>" method="POST">
    <table>
        <tr>
            <td>Classes list:
                <select name="letter">
                    <c:forEach var="sclass" items="${form.sClasses}">
                        <option value="${sclass.classId}"><c:out value="${sclass.letter}"/></option>
                    </c:forEach>
                </select>
            </td>
<%-- <td><input type="submit" name="getList" value="Обновить"/></td>--%>
</tr>
</table>
</form>
</body>
</html>
