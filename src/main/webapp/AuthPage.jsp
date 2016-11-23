<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Online diary</title>
    <style>

        p {
            color: red;
            font-family: Arial, Helvetica, sans-serif;
            font-weight: lighter;
            margin-top: 5px;
            margin-bottom: 0;
            padding: 0;
            border: 0;
        }

        .loginInput {
            width: 100%;
            padding: 5px 5px 5px 23px;
            margin: 5px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            background-color: white;
            background-position: 4px 5px;
            background-repeat: no-repeat;
            background-image: url("img/icon-user.png");
        }

        .passwordInput {
            width: 100%;
            padding: 5px 5px 5px 23px;
            margin: 5px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            background-color: white;
            background-position: 4px 4px;
            background-repeat: no-repeat;
            background-image: url("img/icon-password.png");
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
            position: relative;
            right: -95px;
            bottom: -10px
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
            /*transition: 0.5s;*/
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
            position: relative;
            right: -88px;
            bottom: -10px;
        }

        .signUpButton:hover {
            background-color: #3D6CB8
        }

        .Frame {
            border: 1px solid #2a2a2a;
            width: 230px;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            border-radius: 5px;
            background-color: #EBEBEA;
            padding: 10px;
        }

    </style>
</head>

<body>
<div class="Frame">
<form action="<c:url value="/auth"/>" method="POST">
    <input type="text" name="login" placeholder="Login" class="loginInput" value="${user.login}"/>
    <input type="password" name="password" placeholder="Password" class="passwordInput" value="${user.password}"/>
    <c:if test="${not empty errorMessage}">
        <p><c:out value="${errorMessage}"/></p>
    </c:if>
   <button type="submit" value="Login" name="Login" class="loginButton"><span>Login</span></button>
    <button type="submit" value="Sign up" name="Sign up" class="signUpButton"><span>Sign up</span></button>
</form>
</div>
</body>
</html>