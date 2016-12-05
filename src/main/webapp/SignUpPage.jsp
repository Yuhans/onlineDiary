<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<html lang="${language}">
<head>
    <title>Online diary sign up</title>
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

        .Button {
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
            margin: 1px;
            position: relative;
            right: -100px;
            bottom: -10px;
        }

        .Button:hover {
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
    <form action="<c:url value="/signup"/>" method="POST">
        <fmt:message key="login.label.username" var="loginTextValue"/>
        <input type="text" name="login" placeholder="${loginTextValue}" class="loginInput"/>
        <c:if test="${errorMessage == 'Incorrect filling'}">
            <fmt:message key="login.message.inclogin" var="incLoginMess"/>
            <p class="errText">${incLoginMess}</p>
        </c:if>
        <fmt:message key="login.label.password" var="passwordTextValue"/>
        <input type="password" name="password" placeholder="${passwordTextValue}" class="passwordInput"/>
        <c:if test="${errorMessage == 'Incorrect filling'}">
            <fmt:message key="login.message.incpass" var="incPassMess"/>
            <p class="errText">${incPassMess}</p>
        </c:if>
        <fmt:message key="login.label.confpassword" var="confPasswordTextValue"/>
        <input type="password" name="confPassword" placeholder="${confPasswordTextValue}" class="passwordInput"/>
        <c:if test="${errorMessage == 'Passwords dont match'}">
            <fmt:message key="login.message.passnotmatch" var="passMatchMess"/>
            <p class="errText"><c:out value="${passMatchMess}"/></p>
        </c:if>
        <c:if test="${errorMessage == 'User with this login already exists'}">
            <fmt:message key="login.message.userexists" var="userExMess"/>
            <p class="errText"><c:out value="${userExMess}"/></p>
        </c:if>
        <fmt:message key="login.button.signup" var="signupButton"/>
        <button type="submit" value="Register" name="Register" class="Button"><span>${signupButton}</span></button>
        <fmt:message key="login.button.cancel" var="cancelButton"/>
        <button type="submit" value="Cancel" name="Cancel" class="Button"><span>${cancelButton}</span></button>
    </form>
</div>
</body>
</html>