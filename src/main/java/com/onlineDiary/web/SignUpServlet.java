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
import java.text.ParseException;

/**
 * Created by ������ on 12.11.2016.
 */
public class SignUpServlet extends HttpServlet {

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
            try {
                if (User.isLoginCorrect(req.getParameter("login")) && User.isPasswordCorrect(req.getParameter("password"))) {
                    if (ManagementSystem.getInstance().checkLogin(req.getParameter("login").trim())) {
                        if (req.getParameter("password").trim().equals(req.getParameter("confPassword").trim())) {
                            addUser(req);resp.sendRedirect("/auth");
                            return;
                        } else {
                            req.setAttribute("errorMessage", "Passwords don't match");
                            req.getRequestDispatcher("/SignUpPage.jsp").forward(req, resp);
                        }
                    } else {
                        req.setAttribute("errorMessage", "User with this login already exists");
                        req.getRequestDispatcher("/SignUpPage.jsp").forward(req, resp);
                    }
                } else {
                    req.setAttribute("errorMessage", "Incorrect filling");
                    req.getRequestDispatcher("/SignUpPage.jsp").forward(req, resp);
                }
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            } catch (ParseException p_e) {
            throw new IOException(p_e.getMessage());
            }
        }
        if (answer == 2) {
            resp.sendRedirect("/auth");
            return;
        }
        getServletContext().getRequestDispatcher("/SignUpPage.jsp").forward(req,resp);
        }

    private void addUser(HttpServletRequest req) throws SQLException, ParseException {
        User user = prepareUser(req);
        ManagementSystem.getInstance().addUser(user);
    }

    private int checkAction(HttpServletRequest req) throws SQLException {
        if (req.getParameter("Register") != null) {
            return 1;
        }
        if (req.getParameter("Cancel") != null) {
            return 2;
        }
        return 0;
    }

    private User prepareUser(HttpServletRequest req) throws ParseException {
        User user = new User();
        user.setLogin(req.getParameter("login").trim());
        user.setPassword(req.getParameter("password").trim());
        user.setRole(1);
        return user;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }
}
