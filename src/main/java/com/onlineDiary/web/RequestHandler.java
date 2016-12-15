package com.onlineDiary.web;

import com.onlineDiary.logic.DBService;
import com.onlineDiary.logic.Roles;
import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.logic.beans.SClass;
import com.onlineDiary.logic.beans.Student;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;

public class RequestHandler {
    private static DBService dbService = new DBService();
    private static final Logger LOGGER = Logger.getLogger(RequestHandler.class);
    private static final AccountService accountService = AccountService.getInstance();


    public static void setStudentsAndSubjectsByClass(HttpServletRequest request) {
        request.setAttribute("classes", dbService.getClasses());
        int classId = getClassId(request);
        request.setAttribute("students", dbService.getStudentsByClass(classId));
        SClass schoolClass = dbService.getClasses().get(classId - 1);
        request.setAttribute("subjects", dbService.getSubjects(schoolClass.getStudyYear()));
        request.setAttribute("selectedClass", classId);
    }

    public static void addMark(HttpServletRequest request) {
        if (request.getParameter("OkB") != null) {
            String studentId = request.getParameter("studentId");
            String subjectId = request.getParameter("subjId");
            if (studentId == null || subjectId == null) {
                request.setAttribute("submitDone", "no");
                LOGGER.error("Student ID or Subject ID not found");
            } else {
                int studId = Integer.parseInt(studentId);
                int subjId = Integer.parseInt(subjectId);
                Date date = Date.valueOf(request.getParameter("date"));
                int mark = Integer.parseInt(request.getParameter("mark"));
                dbService.addMark(studId, subjId, date, mark);
                LOGGER.info("Mark has been successfully added to student " + studId + ".");
                request.setAttribute("submitDone", "yes");
            }
        }
    }

    public static void addStudent(HttpServletRequest request) {
        if (request.getParameter("OkB") != null) {
            Student newStudent = prepareStudent(request);
            if (newStudent == null) {
                LOGGER.error("Student hasn't been added.");
                request.setAttribute("submitDone", "no");
            } else {
                dbService.addStudent(newStudent);
                LOGGER.info("Student " + newStudent.getName() + " has been successfully added.");
                request.setAttribute("submitDone", "yes");
            }
        }
    }

    private static Student prepareStudent(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        int classId = getClassId(request);
        if (areParametersCorrect(name, surname, patronymic, classId)) {
            return new Student(name, surname, patronymic, classId, 0);
        } else {
            return null;
        }
    }

    private static boolean areParametersCorrect(String name, String surname, String patronymic, int classId) {
        return !((name == null || name.isEmpty())
                || (surname == null || surname.isEmpty())
                || (patronymic == null || patronymic.isEmpty())
                || classId == 0);
    }

    public static int getClassId(HttpServletRequest request) {
        int classId = 1;
        if (request.getParameter("stClass") != null) {
            classId = Integer.parseInt(request.getParameter("stClass"));
        }
        return classId;
    }

    public static void setClasses(HttpServletRequest request) throws ServletException, IOException {
        request.setAttribute("classes", dbService.getClasses());
    }

    public static void setMarks(HttpServletRequest request) {
        if ((request.getParameter("studentId") != null) & (request.getParameter("subjId") != null)) {
            int studId = Integer.parseInt(request.getParameter("studentId"));
            int subjId = Integer.parseInt(request.getParameter("subjId"));
            request.setAttribute("marks", dbService.getMarks(studId, subjId));
            request.setAttribute("studId", studId);
            request.setAttribute("subjId", subjId);
        }
    }

    public static void setRole(HttpServletRequest request) {
        if (isTeacher(request)) {
            request.setAttribute("role", Roles.TEACHER);
        }
        else {
            request.setAttribute("role", Roles.STUDENT);
        }
    }

    public static String getLogin(HttpServletRequest request) {
       return accountService.getUserBySessionId(request.getSession().getId()).getLogin();
    }

    public static boolean isTeacher(HttpServletRequest request) {
        return accountService.getUserBySessionId(request.getSession().getId()).getRole() == Roles.TEACHER;
    }

    public static boolean isAuthorized(HttpServletRequest request) {
        return accountService.getUserBySessionId(request.getSession().getId()) != null;
    }
}
