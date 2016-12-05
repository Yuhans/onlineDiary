package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.Roles;
import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.logic.beans.SClass;
import com.onlineDiary.logic.beans.Student;
import com.onlineDiary.web.forms.MainFrameForm;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;

public class RequestHandler {
    private static ManagementSystem dao = new ManagementSystem();
    private static final Logger LOGGER = Logger.getLogger(RequestHandler.class);
    private static final AccountService accountService = AccountService.getInstance();


    public static void setStudentsAndSubjectsByClass(HttpServletRequest request, MainFrameForm form) {
        request.setAttribute("classes", dao.getClasses());
        int classId = getClassId(request);
        request.setAttribute("students", dao.getStudentsByClass(classId));
        SClass schoolClass = dao.getClasses().get(classId - 1);
        request.setAttribute("subjects", dao.getSubjects(schoolClass.getStudyYear()));
        schoolClass.setClassId(classId);
        form.setClassId(schoolClass.getClassId());
        request.setAttribute("form", form);
    }

    public static void addMark(HttpServletRequest request) {
        request.setAttribute("submitDone", null);
        if (request.getParameter("OkB") != null) {
            String tmp = request.getParameter("studentId");
            String tmp2 = request.getParameter("subjId");
            if (tmp == null && tmp2 == null) {
                request.setAttribute("submitDone", "no");
                LOGGER.error("Student ID or Subject ID not found");
            } else {
                int studId = Integer.parseInt(request.getParameter("studentId"));
                int subjId = Integer.parseInt(request.getParameter("subjId"));
                Date date = Date.valueOf(request.getParameter("date"));
                int mark = Integer.parseInt(request.getParameter("mark"));
                dao.addMark(studId, subjId, date, mark);
                LOGGER.info("Mark has been successfully added to student " + studId + ".");
                request.setAttribute("submitDone", "yes");
            }
        }
    }

    public static void addStudent(HttpServletRequest request) {
        request.setAttribute("classes", dao.getClasses());
        if (request.getParameter("OkB") != null) {
            Student newStudent = prepareStudent(request);
            if (newStudent == null) {
                LOGGER.error("Student hasn't been added.");
                request.setAttribute("submitDone", "no");
            } else {
                dao.addStudent(newStudent);
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
        request.setAttribute("classes", dao.getClasses());
    }

    public static void setMarks(HttpServletRequest request, MainFrameForm form) {
        if ((request.getParameter("studentId") != null) & (request.getParameter("subjId") != null)) {
            int studId = Integer.parseInt(request.getParameter("studentId"));
            int subjId = Integer.parseInt(request.getParameter("subjId"));
            form.setMarks(dao.getMarks(studId, subjId));
            form.setSelStudentId(studId);
            form.setSelSubjId(subjId);
            request.setAttribute("marks", form.getMarks());
            request.setAttribute("form", form);
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

    public static boolean isTeacher(HttpServletRequest request) {
        return accountService.getUserBySessionId(request.getSession().getId()).getRole() == Roles.TEACHER;
    }

    public static boolean isAuthorized(HttpServletRequest request) {
        return accountService.getUserBySessionId(request.getSession().getId()) != null;
    }
}
