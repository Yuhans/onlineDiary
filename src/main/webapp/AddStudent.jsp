<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text" />
<fmt:message key="mainpage" var="mainpage"/>
<fmt:message key="addmark" var="addmark"/>
<fmt:message key="addstudent" var="addstudent"/>
<fmt:message key="logout" var="logout"/>
<fmt:message key="selectclass" var="selectclass"/>
<fmt:message key="selectsubject" var="selectsubj"/>
<fmt:message key="selectstudent" var="selectstud"/>
<fmt:message key="submitsuccess" var="success"/>
<fmt:message key="submitfail" var="fail"/>
<fmt:message key="surname_i18n" var="surname_i18n"/>
<fmt:message key="name_i18n" var="name_i18n"/>
<fmt:message key="patronymic_i18n" var="patronymic_i18n"/>

<html lang="${language}">
<head>
    <title>Add Student</title>
    <link rel="icon" type="image/png" href="img/favicon.png">
    <link href="/css/defaultStyle.css" rel="stylesheet" type="text/css">
    <style>
        .okButton {
            display: inline-block;
            border-radius: 10px;
            background-color: #4580DE;
            border: none;
            color: #FFFFFF;
            text-align: center;
            font-size: 10px;
            padding: 5px;
            width: 30%;
            height: 30px;
            transition: all 0.5s;
            cursor: pointer;
            margin: 5px;
            position: relative;
            bottom: -12px;
        }

        .okButton:hover {
            background-color: #3D6CB8
        }
    </style>
</head>
<body>
<div class="Frame">
    <img class="img" src="img/icon-diary.png" alt="icon-diary">
    <ul>
        <li><a href="/main">${mainpage}</a></li>
        <li><a href="/addmark">${addmark}</a></li>
        <li><a href="/addstudent">${addstudent}</a></li>
        <li style="float:right"><a href="/logout">${logout}</a></li>
        </li>
    </ul>
    <form action="<c:url value="/addstudent"/>" method="POST">
        <select id="stClass" name="stClass">
            <option value="" disabled selected>${selectclass}</option>
            <c:forEach var="stclass" items="${classes}">
                <option value="${stclass.classId}" ${stclass.classId==form.classId ? 'selected' : ''}>
                    <c:out value="${stclass.studyYear}"/>
                    <c:out value="${stclass.letter}"/>
                </option>
            </c:forEach>
        </select>
        <br/>
        <br/>
        <br/>
        <br/>
        <input type="text" name="surname" id="surname" value="${surname_i18n}">
        <br/>
        <input type="text" name="name" id="name" value="${name_i18n}">
        <br/>
        <input type="text" name="patronymic" id="patronymic" value="${patronymic_i18n}">
        <br/>
        <button type="submit" value="OK" name="OkB" class="okButton"><span>OK</span></button>
        <c:if test="${submitDone == 'yes'}">
            <script>alert("${success}");
            </script>
        </c:if>
        <c:if test="${submitDone == 'no'}">
            <script>alert("${fail}");
            </script>
        </c:if>
    </form>
</div>
</body>
</html>
