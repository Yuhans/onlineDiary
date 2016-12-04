<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text" />

<html lang="${language}">
<head>
    <title>Online diary</title>
    <link rel="icon" type="image/png" href="img/favicon.png">
    <style>

        p.errText {
            color: red;
            font-weight: lighter;
            font-size: 13px;
            font-family: Arial, Helvetica, sans-serif;
            margin: 0;
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
            background: white url("img/icon-user.png") no-repeat 4px 5px;
        }

        .passwordInput {
            width: 100%;
            padding: 5px 5px 5px 23px;
            margin: 5px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            background: white url("img/icon-password.png") no-repeat 4px 4px;
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

        .selectLang {
            width: 26%;
            position: relative;
            right: -167px;
            margin: 2px;
            border: none;
            border-radius: 4px;
            background-color: #4580DE;
            color: #FFFFFF;
            text-align: center;
            font-size: 10px;
        }

        .selectLang:hover {
            background-color: #3D6CB8
        }

    </style>
</head>

<body>
<div class="Frame">
<form action="<c:url value="/auth"/>" method="POST">
    <select id="language" name="language" class="selectLang" onchange="submit()">
        <c:choose>
            <c:when test="${language=='en'}">
                <option value="en" selected>English</option>
                <option value="ru">Russian</option>
            </c:when>
            <c:otherwise>
                <option value="en">English</option>
                <option value="ru" selected>Russian</option>
            </c:otherwise>
        </c:choose>
    </select>
    <fmt:message key="login.label.username" var="loginTextValue"/>
    <input type="text" name="login" placeholder="${loginTextValue}" class="loginInput"/>
    <fmt:message key="login.label.password" var="passwordTextValue"/>
    <input type="password" name="password" placeholder="${passwordTextValue}" class="passwordInput"/>
    <c:if test="${not empty errorMessage}">
        <p class="errText"><c:out value="${errorMessage}"/></p>
    </c:if>
    <fmt:message key="login.button.login" var="loginButtonValue"/>
   <button type="submit" value="Login" name="Login" class="loginButton"><span>${loginButtonValue}</span></button>
    <fmt:message key="login.button.signup" var="signUpButtonValue"/>
    <button type="submit" value="Sign up" name="Sign up" class="signUpButton"><span>${signUpButtonValue}</span></button>
</form>
</div>
</body>
</html>