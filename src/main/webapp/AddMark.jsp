<%--
  Created by IntelliJ IDEA.
  User: Tatyana
  Date: 27.11.2016
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add mark</title>
    <link rel="icon" type="image/png" href="img/favicon.png">
    <link href="/css/defaultStyle.css" rel="stylesheet" type="text/css">
    <style>
        .okButton {
            position: fixed;
            top: 75%;
            left: 5px;
        }
    </style>
</head>
<body>
<div class="Frame">
    <form action="<c:url value="/addmark"/>" method="POST">

        <ul>
            <li><a href="/main">Home</a></li>
            <li><a href="/addmark">Add mark</a></li>
            <li style="float:right"><a><input type="submit" class="logOutButton" value="Log out" name="Log out"/></a>
            </li>
        </ul>

        <select id="stClass" name="stClass" onchange="this.form.submit()">
            <option value="" disabled selected>Select class</option>
            <c:forEach var="stclass" items="${classes}">
                <c:choose>
                    <c:when test="${stclass.classId==form.classId}">
                        <option value="${stclass.classId}" selected>
                            <c:out value="${stclass.studyYear}"/>
                            <c:out value="${stclass.letter}"/>
                        </option>
                    </c:when>
                    <c:otherwise>
                        <option value="${stclass.classId}">
                            <c:out value="${stclass.studyYear}"/>
                            <c:out value="${stclass.letter}"/>
                        </option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </form>

    <form action="<c:url value="/addmark"/>" method="POST">
        <select id="studentId" name="studentId">
            <option value="" disabled selected>Select student</option>
            <c:forEach var="student" items="${students}">
                <option value="${student.studentId}">
                    <c:out value="${student.surname}"/>
                    <c:out value="${student.name}"/>
                    <c:out value="${student.patronymic}"/>
                </option>
            </c:forEach>
        </select>
        <br/>
        <select id="subjId" name="subjId">
            <option value="" disabled selected>Select subject</option>
            <c:forEach var="subject" items="${subjects}">
                <option value="${subject.subjId}">
                    <c:out value="${subject.subjName}"/>
                </option>
            </c:forEach>
        </select>

    </form>
    <form action="<c:url value="/addmark"/>" method="POST">
        <br/>
        <br/>
        <label for="mark">Mark</label>
        <input type="text" name="mark" id="mark" value="5">
        <br/>
        <label for="date">Date</label>
        <input type="date" name="date" id="date" value="2016-11-13">
        <br/>
        <button type="submit" value="OK" name="OkB" class="okButton"><span>OK</span></button>
    </form>
    <img class="img" src="img/icon-diary.png" alt="icon-diary">
</div>
</body>
</html>
