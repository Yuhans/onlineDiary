package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;


public class AuthPageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int answer;
        try {
            answer = checkAction(req);
        } catch (SQLException sql_e) {
            throw new IOException(sql_e.getMessage());
        }
        if (answer == 1) {
                 if (req.getParameter("login") != null) {
                     String login = req.getParameter("login");
                     User user = ManagementSystem.getInstance().getUserByLogin(login);
                     if (user == null) {
                         req.setAttribute("errorMessage", "Invalid user or password");
                         req.getRequestDispatcher("/AuthPage.jsp").forward(req, resp);
                     } else if (user.getPassword().equals(req.getParameter("password").trim())) {
                         HttpSession session = req.getSession();
                         session.setAttribute("user", login);
                         resp.sendRedirect("/main");
                         return;
                     } else {
                         req.setAttribute("errorMessage", "Invalid user or password");
                         req.getRequestDispatcher("/AuthPage.jsp").forward(req, resp);
                     }
                 } else {
                     req.setAttribute("errorMessage", "Invalid user or password");
                     req.getRequestDispatcher("/AuthPage.jsp").forward(req, resp);
                 }
                 return;
             }
        if (answer == 2) {
            resp.sendRedirect("/signup");
            return;
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
