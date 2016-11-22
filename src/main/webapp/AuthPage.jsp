<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Online diary</title>
    <style>

        p {
            color: red;
            width: 100%;
        }

        input[type=text],[type=password] {
            width: 180px;
            padding: 5px;
            margin: 5px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .loginButton {
            display: inline-block;
            border-radius: 10px;
            background-color: #4580DE;
            border: none;
            color: #FFFFFF;
            text-align: center;
            font-size: 10px;
            padding: 8px;
            width: 60px;
            transition: all 0.5s;
            cursor: pointer;
            margin: 5px;
        }

        .loginButton span {
            cursor: pointer;
            display: inline-block;
            position: relative;
            transition: 0.5s;
        }

        .loginButton span:after {
            content: '»';
            position: absolute;
            opacity: 0;
            top: 0;
            right: -20px;
            transition: 0.5s;
        }

        .loginButton:hover span {
            padding-right: 10px;
        }

        .loginButton:hover span:after {
            opacity: 1;
            right: 0;
        }

        .signUpButton {
            display: inline-block;
            border-radius: 10px;
            background-color: #4580DE;
            border: none;
            color: #FFFFFF;
            text-align: center;
            font-size: 10px;
            padding: 8px;
            width: 60px;
            transition: all 0.5s;
            cursor: pointer;
            margin: 5px;
        }

        .signUpButton:hover {
            background-color: #3D6CB8
        }

        div {
            border: 1px solid #2a2a2a;
            width: 15%;
            border-radius: 5px;
            background-color: #EBEBEA;
            padding-top: 15px;
            padding-bottom: 2px;
            padding-left: 10px;
            padding-right: 8px;
        }

    </style>
</head>

<body>
<div>
<form action="<c:url value="/auth"/>" method="POST">
    <table>
        <tr>
            <td>Login:</td><td><input type="text" name="login" value="${user.login}"/></td>
        </tr>
        <tr>
            <td>Password:</td><td><input type="password" name="password" value="${user.password}"/></td>
        </tr>
        <tr width="100%"> //TODO Normal width
            <td width="100%">
                <c:if test="${not empty errorMessage}">
                  <p><c:out value="${errorMessage}"/></p>
                </c:if>
            </td>
        </tr>
        <table>
        <tr width="120">
            <td><button type="submit" value="Login" name="Login" class="loginButton"><span>Login</span></button></td>
            <td><button type="submit" value="Sign up" name="Sign up" class="signUpButton"><span>Sign up</span></button></td>
        </tr>
        </table>
    </table>
</form>
</div>
</body>
</html>