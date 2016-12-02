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

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String answer = checkAction(req);
        switch (answer) {
            case "Register":
                signUp(req, resp);
                break;
            case "Cancel":
                resp.sendRedirect("/auth");
                break;
            default:
                getServletContext().getRequestDispatcher("/SignUpPage.jsp").forward(req, resp);
                break;
        }
    }

    private String checkAction(HttpServletRequest req) {
        if (req.getParameter("Register") != null) {
            return "Register";
        }
        if (req.getParameter("Cancel") != null) {
            return "Cancel";
        }
        return "";
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isLoginAndPasswordCorrect(request)) {
            signUpUser(request, response);
        } else {
            request.setAttribute("errorMessage", "Incorrect filling");
            request.getRequestDispatcher("/SignUpPage.jsp").forward(request, response);
        }
    }

    private boolean isLoginAndPasswordCorrect(HttpServletRequest request) {
        return User.isLoginCorrect(request.getParameter("login")) && User.isPasswordCorrect(request.getParameter("password"));
    }

    private void signUpUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserAlreadyExist(request)) {
            request.setAttribute("errorMessage", "User with this login already exists");
            request.getRequestDispatcher("/SignUpPage.jsp").forward(request, response);
        } else {
            addUser(request, response);
        }
    }

    private boolean isUserAlreadyExist(HttpServletRequest request) {
        return dao.checkLogin(request.getParameter("login").trim());
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("password").trim().equals(request.getParameter("confPassword").trim())) {
            addUser(request);
            response.sendRedirect("/auth");
        } else {
            request.setAttribute("errorMessage", "Passwords don't match");
            request.getRequestDispatcher("/SignUpPage.jsp").forward(request, response);
        }
    }

    private void addUser(HttpServletRequest req) {
        User user = prepareUser(req);
        dao.addUser(user);
    }

    private User prepareUser(HttpServletRequest req) {
        User user = new User();
        user.setLogin(req.getParameter("login").trim());
        user.setPassword(req.getParameter("password").trim());
        user.setRole(1);
        return user;
    }



}
