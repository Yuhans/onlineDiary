<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Online diary sign up</title>
</head>

<body>
<form action="<c:url value="/signup"/>" method="POST">
    <table>
        <tr>
            <td>Login:</td><td><input type="text" name="login" value="${user.login}"/></td>
        </tr>
        <tr>
            <td>Password:</td><td><input type="password" name="password" value="${user.password}"/></td>
        </tr>
        <tr>
            <td>Confirm password:</td><td><input type="password" name="password" value="${user.password}"/></td>
        </tr>
    </table>
    <table>
        <tr>
            <td><input type="submit" value="Register" name="Register"/></td>
            <td><input type="submit" value="Cancel" name="Cancel"/></td>
        </tr>
    </table>
</form>
</body>
</html>