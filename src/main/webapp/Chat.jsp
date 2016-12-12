<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<fmt:message key="receivername" var="receivername"/>
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
    <style>
        .Messages {
            top: 10%;
            left: 29%;
            right: 19%;
            height: 85%;
            position: absolute;
            border-radius: 8px;
            overflow: hidden;
        }

        .TextArea {
            background-color: #F8F8F7;
            width: 100%;
            height: 70%;
            position: absolute;
            border-radius: 8px;
            overflow: auto;
            overflow-x:hidden;

        }

        .MessageArea {
            position: absolute;
            left: 0%;
            bottom: 0%;
            height: 25%;
            width: 80%;
        }

        .textBefore {
            top: 50%;
            margin: 5px;
        }

        .SendBtn {
            position: absolute;
            right: 0%;
            top: 75%;
            width: 15%;
            height: 40px;

            border-radius: 8px;
            background-color: #4580DE;
            border: none;
            color: #FFFFFF;
            text-align: center;
            font-size: 10px;
            padding: 5px;
            transition: all 0.5s;
            cursor: pointer;
            margin: 5px;

        }

        .SendBtn:hover {
            background-color: #3D6CB8
        }

        select {
            position: relative;
            bottom: -5px;
            width: 27%;
        }

        input[type="text"] {
            width: 100%;
            height: 50%;
            margin: 0px;
        }

        .blueText {
            color: blue;
        }

        .blackText {
            color: black;
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


    <div>
        <form action="<c:url value="/chat"/>" method="POST">
            <br>
            <br>
            <br>
            <div class="textBefore">
                <c:out value="${receivername}"/>
            </div>
            <select id="receiver" name="receiver" onchange="submit()">
                <option selected="selected">${selectedUser}</option>
                <c:forEach var="user" items="${users}">
                    <c:if test="${selectedUser != user.login}">
                        <option value="${user.login}" ${user.login==selectedUser ? 'selected' : ''}>
                            <c:out value="${user.login}"/>
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </form>
    </div>


    <div class="Messages">

        <div class="TextArea">
            <c:forEach items="${messages}" var="mess">
                <br><b>${mess.date}</b>
                <br>${mess.sender} to  ${mess.receiver}
                <c:choose>
                    <c:when test="${sender eq mess.sender}">
                        <span class="blackText">${mess.text}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="blueText">${mess.text}</span>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <form action="<c:url value="/chat"/>" method="POST">
            <div class="MessageArea">
                <textarea name="newMessage" cols="40" rows="5"></textarea>
            </div>

            <div>
                <button type="submit" value="OK" name="messOk" class="SendBtn">
                    <span>OK</span>
                </button>
            </div>
        </form>

    </div>
</div>
</body>
</html>
