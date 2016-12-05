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
<fmt:message key="chat" var="chat"/>
<fmt:message key="selectclass" var="selectclass"/>
<fmt:message key="selectsubject" var="selectsubj"/>
<fmt:message key="selectstudent" var="selectstud"/>
<fmt:message key="mark" var="mark"/>
<fmt:message key="date" var="date"/>
<fmt:message key="subject" var="showSubject"/>


<html lang="${language}">
<head>
    <title>Main Frame</title>

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
        <c:when test="${role=='TEACHER'}">
            <ul>
                <li><a href="<c:url value="/main"/>">${mainpage}</a></li>
                <li><a href="<c:url value="/addmark"/>">${addmark}</a></li>
                <li><a href="<c:url value="/addstudent"/>">${addstudent}</a></li>
                <li><a href="<c:url value="/chat"/>">${chat}</a></li>
                <li style="float:right"><a href="<c:url value="/logout"/>">${logout}</a></li>
            </ul>
        </c:when>
        <c:otherwise>
            <ul>
                <li><a href="<c:url value="/main"/>">${mainpage}</a></li>
                <li><a href="<c:url value="/chat"/>">${chat}</a></li>
                <li style="float:right"><a href="<c:url value="/logout"/>">${logout}</a></li>
            </ul>
        </c:otherwise>
    </c:choose>
    <form action="<c:url value="/main"/>" method="POST">
        <select id="stClass" name="stClass" onchange="this.form.submit()">
            <option value="" disabled selected>${selectclass}</option>
            <c:forEach var="stclass" items="${classes}">
                <option value="${stclass.classId}" ${stclass.classId==form.classId ? 'selected' : ''}>
                    <c:out value="${stclass.studyYear}"/>
                    <c:out value="${stclass.letter}"/>
                </option>
            </c:forEach>
        </select>
        <br/>
        <select id="studentId" name="studentId">
            <option value="" disabled selected>${selectstud}</option>
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
            <option value="" disabled selected>${selectsubj}</option>
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
                <th>${showSubject}</th>
                <th>${date}</th>
                <th>${mark}</th>
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
