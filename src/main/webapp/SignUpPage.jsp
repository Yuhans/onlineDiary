<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Online diary sign up</title>
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

        p.corFilText {
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
    <input type="text" name="login" placeholder="Login" class="loginInput" value="${user.login}"/>
    <c:if test="${errorMessage == 'Incorrect filling'}">
        <p class="corFilText">Login should be at least 3 characters long.</p>  <%--a-z0-9_---%>
    </c:if>
    <input type="password" name="password" placeholder="Password" class="passwordInput" value="${user.password}"/>
    <c:if test="${errorMessage == 'Incorrect filling'}">
        <p class="corFilText">Password should be at least 6 characters long.</p>
    </c:if>
    <input type="password" name="confPassword" placeholder="Confirm password" class="passwordInput" value="${user.password}"/>
    <c:if test="${errorMessage != 'Incorrect filling'}">
        <c:if test="${not empty errorMessage}">
            <p><c:out value="${errorMessage}"/></p>
        </c:if>
    </c:if>
    <button type="submit" value="Register" name="Register" class="Button"><span>Register</span></button>
    <button type="submit" value="Cancel" name="Cancel" class="Button"><span>Cancel</span></button>
</form>
</div>
</body>
</html>