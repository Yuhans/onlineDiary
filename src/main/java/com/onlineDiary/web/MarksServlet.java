package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.Mark;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created by Tatyana on 16.11.2016.
 */

public class MarksServlet extends HttpServlet {
    ManagementSystem dao = ManagementSystem.getInstance();

    protected void setMarks(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int studId = Integer.parseInt(req.getParameter("studentId"));
        int subjId = Integer.parseInt(req.getParameter("subjId"));
        List<Mark> marks = dao.getMarks(studId, subjId);
        req.setAttribute("marks", marks);
        getServletContext().getRequestDispatcher("/MarksPage.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setMarks(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
