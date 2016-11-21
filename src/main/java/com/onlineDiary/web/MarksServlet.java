package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by Tatyana on 16.11.2016.
 */

public class MarksServlet extends HttpServlet {
    ManagementSystem dao = ManagementSystem.getInstance();

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //MarksForm marksForm = new MarksForm();
        try {
            int studId=1;
            int subjId=1;
            List<Integer> marks = dao.getMarks(studId,subjId);
            List<Date> days = dao.getDates();
            String subjectName=ManagementSystem.getInstance().getSubjectName(subjId);
            req.setAttribute("marks", marks);
            req.setAttribute("days", days);
            req.setAttribute("subjectName", subjectName);
            getServletContext().getRequestDispatcher("/MarksPage.jsp").forward(req, resp);
        } catch (SQLException sql_e) {
            throw new IOException(sql_e.getMessage());
        }
    }
}