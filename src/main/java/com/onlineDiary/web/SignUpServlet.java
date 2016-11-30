package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.beans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private ManagementSystem dao = new ManagementSystem();

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int answer = checkAction(req);
        switch (answer) {
            case 1:
                if (isLoginAndPasswordCorrect(req)) {
                    if (isUserAlreadyExist(req)) {
                        if (req.getParameter("password").trim().equals(req.getParameter("confPassword").trim())) {
                            addUser(req);
                            resp.sendRedirect("/auth");
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
                break;
            case 2:
                resp.sendRedirect("/auth");
                break;
            default:
                getServletContext().getRequestDispatcher("/SignUpPage.jsp").forward(req, resp);
                break;
        }


    }

    private boolean isLoginAndPasswordCorrect(HttpServletRequest request) {
        return User.isLoginCorrect(request.getParameter("login")) && User.isPasswordCorrect(request.getParameter("password"));
    }

    private boolean isUserAlreadyExist(HttpServletRequest request) {
        return dao.checkLogin(request.getParameter("login").trim());
    }

    private void addUser(HttpServletRequest req) {
        User user = prepareUser(req);
        dao.addUser(user);
    }

    private int checkAction(HttpServletRequest req) {
        if (req.getParameter("Register") != null) {
            return 1;
        }
        if (req.getParameter("Cancel") != null) {
            return 2;
        }
        return 0;
    }

    private User prepareUser(HttpServletRequest req) {
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
