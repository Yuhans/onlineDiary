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

    <style>

        .Frame {
            /*border: 1px solid #2a2a2a;*/
            width: 950px;
            height: 600px;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            border-radius: 8px;
            background-color: #EBEBEA;
            padding: 0px;
        }

        select {
            width: 30%;
            position: relative;
            bottom: -50px;
            padding: 16px 20px;
            margin: 5px;
            border: none;
            border-radius: 4px;
            background-color: #BFD7ED;
        }

        ul {
            position: fixed;
            top: 4%;
            left: 50%;
            transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            width: 950px;
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #4580DE;
            border-radius: 8px;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        li a:hover:not(.active) {
            background-color: #3D6CB8;
        }

        .active {
            background-color: #3D6CB8;
        }

        .okButton {
            display: inline-block;
            border-radius: 10px;
            background-color: #4580DE;
            border: none;
            color: #FFFFFF;
            text-align: center;
            font-size: 10px;
            padding: 5px;
            width: 60px;
            height: 30px;
            transition: all 0.5s;
            cursor: pointer;
            margin: 0px;
            position: relative;
            bottom: -50px;
        }

        .okButton:hover {
            background-color: #3D6CB8
        }

        .logOutButton {
            display: inline-block;
            background-color: Transparent;
            border: none;
            color: #FFFFFF;
            text-align: center;
            width: 50px;
            height: 18px;
            transition: all 0.5s;
            cursor: pointer;
            margin: 0;
            padding: 0;
            border: 0;
        }

        .img {
            position: relative;
            bottom: -280px;
            right: -860px;
        }

    </style>

</head>
<body>
<div class="Frame">
    <form action="<c:url value="/main"/>" method="POST">
        <ul>
            <li><a href="/main">Home</a></li>
            <li style="float:right"> <a><input type="submit" class="logOutButton" value="Log out" name="Log out"/></a></li>
        </ul>
        <select id="stClass" name="stClass">
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
        <button type="submit" value="OK" name="Ok" class="okButton"><span>OK</span></button>
    </form>
    <form action="<c:url value="/marks"/>" method="POST">
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
        <button type="submit" value="OK" name="subjOk" class="okButton"><span>OK</span></button>
    </form>
    <img class="img" src="img/icon-diary.png" alt="icon-diary">
</div>
</body>
</html>
