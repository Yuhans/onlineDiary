<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Main Frame</title>

    <link rel="icon" type="image/png" href="img/favicon.png">
    <link href="/css/defaultStyle.css" rel="stylesheet" type="text/css">
    <style>
        .Frame {
            width: 950px;
            height: 600px;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            border-radius: 8px;
            background-color: #EBEBEA;
            padding: 0;
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
            width: 30%;
            height: 30px;
            transition: all 0.5s;
            cursor: pointer;
            margin: 5px;
            position: relative;
            bottom: -50px;
        }

        .okButton:hover {
            background-color: #3D6CB8
        }
    </style>

</head>
<body>
<div class="Frame">
    <img class="img" src="img/icon-diary.png" alt="icon-diary">
    <c:choose>
        <c:when test="${role == 0}">
            <ul>
                <li><a href="<c:url value="/main"/>">Home</a></li>
                <li><a href="<c:url value="/addmark"/>">Add mark</a></li>
                <li><a href="<c:url value="/addstudent"/>">Add Student</a></li>
                <li style="float:right"><a href="<c:url value="/logout"/>">Log out</a></li>
            </ul>
        </c:when>
        <c:otherwise>
            <ul>
                <li><a href="<c:url value="/main"/>">Home</a></li>
                <li style="float:right"><a href="<c:url value="/logout"/>">Log out</a></li>
            </ul>
        </c:otherwise>
    </c:choose>
    <form action="<c:url value="/main"/>" method="POST">
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
                <option value="${student.studentId}" ${student.studentId == form.selStudentId ? 'selected' : ''}>
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
                <option value="${subject.subjId}" ${subject.subjId == form.selSubjId ? 'selected' : ''}>
                    <c:out value="${subject.subjName}"/>
                </option>

            </c:forEach>
        </select>
        <br/>
        <button type="submit" value="OK" name="subjOk" class="okButton"><span>OK</span></button>
        <br/>
        <br/>
        <br/>
        <br/>
        <table>
            <tr>
                <th>Subject</th>
                <th>Date</th>
                <th>Mark</th>
            </tr>
            <c:forEach items="${marks}" var="mark">
                <tr>
                    <td>${mark.subject}</td>
                    <td>${mark.date}</td>
                    <td>${mark.mark}</td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>
</body>
</html>
