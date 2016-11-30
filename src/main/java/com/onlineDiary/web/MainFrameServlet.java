package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.beans.SClass;
import com.onlineDiary.web.forms.MainFrameForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class MainFrameServlet extends HttpServlet {
    private final ManagementSystem dao = new ManagementSystem();
    private final MainFrameForm form = new MainFrameForm();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            setMainForm(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            setClasses(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    private boolean isAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("user") != null;
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
            request.setAttribute("marks", form.getMarks());
        }
    }

    private void setStudentsAndSubjectsByClass(HttpServletRequest request) {
        request.setAttribute("classes", dao.getClasses());
        SClass schoolClass = new SClass();
        int classId = 0;
        if (request.getParameter("stClass") != null) {
            classId = Integer.parseInt(request.getParameter("stClass"));
            request.setAttribute("students", dao.getStudentsByClass(classId));
            schoolClass = dao.getClasses().get(classId - 1);
            request.setAttribute("subjects", dao.getSubjects(schoolClass.getStudyYear()));
        }
        schoolClass.setClassId(classId);
        form.setClassId(schoolClass.getClassId());
        request.setAttribute("form", form);
    }

    private void setClasses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("classes", dao.getClasses());
        request.getRequestDispatcher("MainFrame.jsp").forward(request, response);
    }
}