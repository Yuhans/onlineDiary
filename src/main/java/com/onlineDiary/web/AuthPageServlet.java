package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.beans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthPageServlet extends HttpServlet {
    private ManagementSystem managementSystem = new ManagementSystem();

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int answer = checkAction(req);
        switch (answer) {
            case 1:
                if (req.getParameter("login") != null) {
                    String login = req.getParameter("login");

                    User user = managementSystem.getUserByLogin(login);


                    if (user == null) {
                        req.setAttribute("errorMessage", "Invalid user or password");
                        req.getRequestDispatcher("/AuthPage.jsp").forward(req, resp);
                    } else if (user.getPassword().equals(req.getParameter("password").trim())) {
                        HttpSession session = req.getSession();
                        session.setAttribute("user", login);
                        session.setAttribute("role",user.getRole() );
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
                break;
            case 2:
                resp.sendRedirect("/signup");
                break;
            default:
                getServletContext().getRequestDispatcher("/AuthPage.jsp").forward(req, resp);
        }
    }

    private int checkAction(HttpServletRequest req) {
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
