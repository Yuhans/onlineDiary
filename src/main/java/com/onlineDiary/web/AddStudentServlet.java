package com.onlineDiary.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (RequestHandler.isAuthorized(request)) {
            if (RequestHandler.isTeacher(request)) {
                RequestHandler.addStudent(request);
                request.getRequestDispatcher("AddStudent.jsp").forward(request, response);
            } else {
                response.sendRedirect("/main");
            }
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (RequestHandler.isAuthorized(request)) {
            if (RequestHandler.isTeacher(request)) {
                RequestHandler.setClasses(request);
                request.getRequestDispatcher("AddStudent.jsp").forward(request, response);
            } else {
                response.sendRedirect("/main");
            }
        } else {
            response.sendRedirect("/auth");
        }
    }
}
