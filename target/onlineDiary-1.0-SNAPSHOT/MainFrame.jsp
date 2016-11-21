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
</head>

<body>
<form action="/main" method="post">
    <table>
        <tr>
            <td>Classes list:
                <select name="stClass">
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
            </td>
            <td>
                <input type="submit" value="Ok" name="Ok"/>
            </td>
        </tr>
    </table>
</form>
<form action=
      <c:url value="/marks"/> method="post">
    <table>
        <tr>
            <td>Students:
                <select name="studentId">
                    <c:forEach var="student" items="${students}">
                        <option value="${student.studentId}">
                            <c:out value="${student.surname}"/>
                            <c:out value="${student.name}"/>
                            <c:out value="${student.patronymic}"/>
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td>Subjects:
                <select name="subjId">
                    <c:forEach var="subject" items="${subjects}">
                        <option value="${subject.subjId}">
                            <c:out value="${subject.subjName}"/>
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input type="submit" value="Ok" name="subjOk"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
