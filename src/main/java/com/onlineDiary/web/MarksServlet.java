package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.Mark;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * Created by Tatyana on 16.11.2016.
 */

public class MarksServlet extends HttpServlet {
    ManagementSystem dao = ManagementSystem.getInstance();

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int studId= Integer.parseInt(req.getParameter("studentId"));
        int subjId= Integer.parseInt(req.getParameter("subjId"));
        try {
            List<Mark> marks= dao.getMarks(studId, subjId);
            req.setAttribute("marks", marks);
            getServletContext().getRequestDispatcher("/MarksPage.jsp").forward(req, resp);
        } catch (SQLException sql_e) {
            throw new IOException(sql_e.getMessage());
        }


    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
