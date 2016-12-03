package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.Roles;
import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.logic.beans.SClass;
import com.onlineDiary.web.forms.MainFrameForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MainFrameServlet extends HttpServlet {

    private ManagementSystem dao = new ManagementSystem();
    private MainFrameForm form = new MainFrameForm();
    private AccountService accountService = AccountService.getInstance();

    private static final Roles TEACHER = Roles.TEACHER;
    private static final Roles STUDENT = Roles.STUDENT;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            if (isTeacher(request)) {
                request.setAttribute("role", 0);
            } else {
                request.setAttribute("role", 1);
            }
            setMainForm(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            if (isTeacher(request)) {
                request.setAttribute("role", 0);
            }
            else {
                request.setAttribute("role", 1);
            }
            setClasses(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    private boolean isAuthorized(HttpServletRequest request) {
        return accountService.getUserBySessionId(request.getSession().getId()) != null;
    }

    private boolean isTeacher(HttpServletRequest request) {
        return accountService.getUserBySessionId(request.getSession().getId()).getRole() == TEACHER;
    }

    private void setMainForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setStudentsAndSubjectsByClass(request);
        setMarks(request);
        request.getRequestDispatcher("MainFrame.jsp").forward(request, response);
    }

    private void setMarks(HttpServletRequest request) {
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

    private void setStudentsAndSubjectsByClass(HttpServletRequest request) {
        request.setAttribute("classes", dao.getClasses());
        int classId = getClassId(request);
        request.setAttribute("students", dao.getStudentsByClass(classId));
        SClass schoolClass = dao.getClasses().get(classId - 1);
        request.setAttribute("subjects", dao.getSubjects(schoolClass.getStudyYear()));
        schoolClass.setClassId(classId);
        form.setClassId(schoolClass.getClassId());
        request.setAttribute("form", form);
    }

    private void setClasses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("classes", dao.getClasses());
        request.getRequestDispatcher("MainFrame.jsp").forward(request, response);
    }

    private int getClassId(HttpServletRequest request) {
        int classId = 1;
        if (request.getParameter("stClass") != null) {
            classId = Integer.parseInt(request.getParameter("stClass"));
        }
        return classId;
    }
}