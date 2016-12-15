package com.onlineDiary.web;

import com.onlineDiary.logic.DBService;
import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.logic.beans.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthPageServlet extends HttpServlet {

    private DBService dbService = new DBService();
    private AccountService accountService = AccountService.getInstance();
    private static final Logger LOGGER = Logger.getLogger(AuthPageServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String answer = checkAction(req);
        switch (answer) {
            case "Sign in":
                signIn(req, resp);
                break;
            case "Sign up":
                resp.sendRedirect("/signup");
                break;
            default:
                getServletContext().getRequestDispatcher("/AuthPage.jsp").forward(req, resp);
                break;
        }
    }

    private String checkAction(HttpServletRequest req) {
        if (req.getParameter("Login") != null) {
            return "Sign in";
        }
        if (req.getParameter("Sign up") != null) {
            return "Sign up";
        }
        return "";
    }

    private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password").trim();
        if (isRequestCorrect(login, password)) {
            User user = dbService.getUserByLogin(login);
            if (user == null || !user.getPassword().equals(password)) {
                doWithBadRequest(request, response);
            } else {
                LOGGER.info("User " + login + " has signed in.");
                accountService.addSession(request.getSession().getId(), user);
                response.sendRedirect("/main");
            }
        } else {
            doWithBadRequest(request, response);
        }
    }

    private boolean isRequestCorrect(String login, String pass) {
        return !(login == null || pass == null);
    }

    private void doWithBadRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.error("Username or password is incorrect");
        request.setAttribute("errorMessage", "Invalid user or password.");
        request.getRequestDispatcher("/AuthPage.jsp").forward(request, response);
    }
}
