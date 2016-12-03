package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.Roles;
import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.logic.beans.SClass;
import com.onlineDiary.web.forms.MainFrameForm;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

public class AddMarkServlet extends HttpServlet {

    private ManagementSystem dao = new ManagementSystem();
    private AccountService accountService = AccountService.getInstance();
    private MainFrameForm form = new MainFrameForm();

    private static final Logger LOGGER = Logger.getLogger(AddMarkServlet.class);
    private static final Roles TEACHER = Roles.TEACHER;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            if (isTeacher(request)) {
                setStudentsAndSubjectsByClass(request);
                addMark(request);
                request.getRequestDispatcher("AddMark.jsp").forward(request, response);
            } else {
                response.sendRedirect("/main");
            }
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            if (isTeacher(request)) {
                request.setAttribute("classes", dao.getClasses());
                request.getRequestDispatcher("AddMark.jsp").forward(request, response);
            } else {
                response.sendRedirect("/main");
            }
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

    private void addMark(HttpServletRequest request) {
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

    private int getClassId(HttpServletRequest request) {
        int classId = 1;
        if (request.getParameter("stClass") != null) {
            classId = Integer.parseInt(request.getParameter("stClass"));
        }
        return classId;
    }
}
