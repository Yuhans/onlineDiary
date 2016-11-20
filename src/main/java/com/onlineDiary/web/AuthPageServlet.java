package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Рамиль on 12.11.2016.
 */
public class AuthPageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int answer = 0;
        try {
            answer = checkAction(req);
        } catch (SQLException sql_e) {
            throw new IOException(sql_e.getMessage());
        }
        if (answer == 1) {
             try {
                 if (req.getParameter("login") != null) {
                     User user = ManagementSystem.getInstance().getUserByLogin(req.getParameter("login"));
                     if (user.getPassword().equals(req.getParameter("password").trim())) {
                         /*RequestDispatcher requestDispatcher = req.getRequestDispatcher("/main");
                         requestDispatcher.forward(req, resp);*/
                         resp.sendRedirect("/main");
                         //getServletContext().getRequestDispatcher("/MainFrame.jsp").forward(req,resp);
                     }
                 }
                 else {
                    // resp.sendRedirect("/signup");
                     getServletContext().getRequestDispatcher("/signup").forward(req,resp);
                 }
                 return;
             } catch (SQLException sql_e) {
                 throw  new IOException(sql_e.getMessage());
             }
        }
        if (answer == 2) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/signup");
            requestDispatcher.forward(req, resp);
            //resp.sendRedirect("/signup");
        }
        getServletContext().getRequestDispatcher("/AuthPage.jsp").forward(req,resp);
    }

    private int checkAction(HttpServletRequest req) throws SQLException {
        if (req.getParameter("Login") != null) {
            return 1;
        }
        if (req.getParameter("Sign up") != null) {
            return 2;
        }
        return 0;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
