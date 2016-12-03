<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add mark</title>
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
    <form action="<c:url value="/addmark"/>" method="POST">
        <select id="stClass" name="stClass" onchange="this.form.submit()">
            <option value="" disabled selected>Select class</option>
            <c:forEach var="stclass" items="${classes}">
                <option value="${stclass.classId}" ${stclass.classId==form.classId ? 'selected' : ''}>
                    <c:out value="${stclass.studyYear}"/>
                    <c:out value="${stclass.letter}"/>
                </option>
            </c:forEach>
        </select>
        <br/>
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
        <br/>
        <br/>
        <br/>
        <br/>
        <input type="text" name="mark" id="mark" value="Mark">
        <br/>
        <input type="date" name="date" id="date" value="Date">
        <br/>
        <button type="submit" value="OK" name="OkB" class="okButton"><span>OK</span></button>
        <c:if test="${submitDone == 'yes'}">
            <script>alert("Mark was added!");
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
