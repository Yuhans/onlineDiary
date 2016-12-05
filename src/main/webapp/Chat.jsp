<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="mainpage" var="mainpage"/>
<fmt:message key="addmark" var="addmark"/>
<fmt:message key="addstudent" var="addstudent"/>
<fmt:message key="logout" var="logout"/>
<fmt:message key="chat" var="chat"/>
<fmt:message key="selectclass" var="selectclass"/>
<fmt:message key="selectuser" var="selectuser"/>
<fmt:message key="selectsubject" var="selectsubj"/>
<fmt:message key="selectstudent" var="selectstud"/>
<fmt:message key="mark" var="mark"/>
<fmt:message key="date" var="date"/>
<fmt:message key="subject" var="showSubject"/>


<html lang="${language}">
<head>
    <title>Chat</title>
    <link rel="icon" type="image/png" href="img/favicon.png">
    <link href="/css/defaultStyle.css" rel="stylesheet" type="text/css">
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
    <div class="TextArea" id='fake_textarea' contenteditable>
        <form action="<c:url value="/chat"/>" method="POST">
            <c:forEach items="${messages}" var="mess">
                <br><b>${mess.date}</b>
                <br>${mess.text}
            </c:forEach>

        </form>
    </div>

    <div class="Receiver">
        <form action="<c:url value="/chat"/>" method="POST">
            <select id="receiver" name="receiver" onchange="this.form.submit()">
                <option value="" disabled selected>${selectuser}</option>
                <c:forEach var="user" items="${users}">
                    <option value="${user.login}" ${user.login==form.selUser ? 'selected' : ''}>
                        <c:out value="${user.getLogin}"/>
                    </option>
                </c:forEach>
            </select>
        </form>
    </div>
</div>
</body>
</html>
