package com.onlineDiary.web;

import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.logic.beans.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    private AccountService accountService = AccountService.getInstance();
    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logout(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logout(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = request.getSession().getId();
        User user = accountService.getUserBySessionId(sessionId);
        if (user != null) {
            HttpSession session = request.getSession();
            accountService.deleteSession(sessionId);
            session.invalidate();
            LOGGER.info("User " + user.getLogin() + " has logged out");
        }
        response.sendRedirect("/auth");
    }
}
