package com.onlineDiary.web;

import com.onlineDiary.web.forms.MainFrameForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MainFrameServlet extends HttpServlet {

    private MainFrameForm form = new MainFrameForm();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (RequestHandler.isAuthorized(request)) {
            RequestHandler.setRole(request);
            setMainForm(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (RequestHandler.isAuthorized(request)) {
            RequestHandler.setRole(request);
            RequestHandler.setClasses(request);
            request.getRequestDispatcher("MainFrame.jsp").forward(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    private void setMainForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler.setStudentsAndSubjectsByClass(request, form);
        RequestHandler.setMarks(request, form);
        request.getRequestDispatcher("MainFrame.jsp").forward(request, response);
    }
}