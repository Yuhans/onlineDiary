<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Золотце
  Date: 01.12.2016
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
        <li><a href="/main">Home</a></li>
        <li><a href="/addmark">Add mark</a></li>
        <li><a href="/addstudent">Add Student</a></li>
        <li style="float:right"><a href="/logout">Log out</a></li>
        </li>
    </ul>
    <form action="<c:url value="/addstudent"/>" method="POST">
        <select id="stClass" name="stClass" >
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
        <br/>
        <br/>
        <br/>
        <br/>
        <label for="surname">Surname</label>
        <input type="text" name="surname" id="surname" value="Петров">
        <br/>
        <label for="name">Name</label>
        <input type="text" name="name" id="name" value="Петр">
        <br/>
        <label for="patronymic">Patronymic</label>
        <input type="text" name="patronymic" id="patronymic" value="Петрович">
        <br/>
        <button type="submit" value="OK" name="OkB" class="okButton"><span>OK</span></button>
        <c:if test="${submitDone == 'yes'}">
            <script>alert("Student was added!");
            </script>
        </c:if>
        <c:if test="${submitDone == 'no'}">
            <script>alert("Fill all fields!");
            </script>
        </c:if>
    </form>
</div>
</body>
</html>
