package com.onlineDiary.web;

import com.onlineDiary.logic.ManagementSystem;
import com.onlineDiary.logic.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;


public class SignUpServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int answer = checkAction(req);

        switch (answer) {
            case 1:
                if (User.isLoginCorrect(req.getParameter("login")) && User.isPasswordCorrect(req.getParameter("password"))) {
                    if (ManagementSystem.getInstance().checkLogin(req.getParameter("login").trim())) {
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
        }


    }

    private void addUser(HttpServletRequest req) {
        try {
            User user = prepareUser(req);
            ManagementSystem.getInstance().addUser(user);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
