package com.onlineDiary.web;

import com.onlineDiary.logic.account.AccountService;
import com.onlineDiary.logic.beans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    private AccountService accountService = AccountService.getInstance();

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
        }
        response.sendRedirect("/auth");
    }
}
