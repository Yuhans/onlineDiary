package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.logic.beans.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddStudentServlet extends HttpServlet {

    private ManagementSystem dao = new ManagementSystem();
    private AccountService accountService = AccountService.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isAuthorized(request)) {
            addStudent(request);
            request.getRequestDispatcher("AddStudent.jsp").forward(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthorized(request)) {
            request.setAttribute("classes", dao.getClasses());
            request.getRequestDispatcher("AddStudent.jsp").forward(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    private boolean isAuthorized(HttpServletRequest request) {
        return accountService.getUserBySessionId(request.getSession().getId()) != null;
    }

    private void addStudent(HttpServletRequest request) throws ServletException {
        request.setAttribute("classes", dao.getClasses());
        if (request.getParameter("OkB") != null) {
            Student newStudent = prepareStudent(request);
            if (newStudent == null) {
                request.setAttribute("submitDone", "no");
            } else {
                dao.addStudent(newStudent);
                request.setAttribute("submitDone", "yes");
            }
        }
    }

    private Student prepareStudent(HttpServletRequest request) {
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

    private boolean areParametersCorrect(String name, String surname, String patronymic, int classId) {
        return !((name == null || name.isEmpty())
                || (surname == null || surname.isEmpty())
                || (patronymic == null || patronymic.isEmpty())
                || classId == 0);
    }

    private int getClassId(HttpServletRequest request) {
        int classId = 0;
        if (request.getParameter("stClass") != null) {
            classId = Integer.parseInt(request.getParameter("stClass"));
        }
        return classId;
    }
}
